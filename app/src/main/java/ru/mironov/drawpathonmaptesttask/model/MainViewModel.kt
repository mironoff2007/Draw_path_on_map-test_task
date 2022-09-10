package ru.mironov.drawpathonmaptesttask.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Geo
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.mironov.drawpathonmaptesttask.Repository
import ru.mironov.drawpathonmaptesttask.Status

class MainViewModel : ViewModel() {

    private var repository: Repository

    var arrayPolylines = arrayListOf<Polyline>()

    private val dataStatus: MutableLiveData<Status> = MutableLiveData<Status>()
    val viewModelStatus: MutableLiveData<Status> = MutableLiveData<Status>()

    init {
        repository = Repository(dataStatus)
        setupObserver()
    }

    private fun setupObserver() {
        repository.dataStatus.observeForever { status ->
            when (status) {
                Status.RESPONSE -> {
                    //Parse and crate list of polylines
                    val parser = GeoJsonParser()
                    if (repository.coords != null) {
                        arrayPolylines = parser.parsePolylines(repository.coords)
                        if (arrayPolylines.size > 0) {
                            viewModelStatus.postValue(Status.RESPONSE)
                        } else {
                            //если почему-то координат нет
                            viewModelStatus.postValue(Status.ERROR)
                        }
                    } else {
                        //если пустой ответ
                        viewModelStatus.postValue(Status.ERROR)
                    }
                }
                Status.ERROR -> {
                    viewModelStatus.postValue(Status.ERROR)
                }
            }
        }
    }

    fun getGeoJson() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getGeoJsonWeb()
            viewModelStatus.postValue(Status.LOADING)
        }
    }

    fun calculateLengths(): Int {
        //массив чтобы длину всех полилиний просмотреть
        //val arr= arrayListOf<Int>()

        var length = 0.0
        var lengthPolyline = 0.0
        var pointLast: Point? = null

        arrayPolylines.forEach {
            //Polyline next
            pointLast = null
            it.points.forEach {
                //Point next
                if (pointLast != null) {
                    //Calc on second point
                    lengthPolyline += Geo.distance(pointLast!!, it)
                }
                pointLast = it
            }
            //arr.add((lengthPolyline / 1000).toInt())
            length += lengthPolyline
            lengthPolyline=0.0

        }
        //Перевести в километры
        return (length / 1000).toInt()
    }

    fun getGeoJsonRes(context: Context) {
        viewModelScope.launch (Dispatchers.Default){
            repository.getGeoJsonRes(GeoJsonParserProvider.readJson(context))
        }
    }
}
