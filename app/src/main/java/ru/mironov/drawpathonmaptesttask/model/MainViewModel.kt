package ru.mironov.drawpathonmaptesttask.model

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Geo
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mironov.drawpathonmaptesttask.Repository
import ru.mironov.drawpathonmaptesttask.Status
import ru.mironov.drawpathonmaptesttask.StatusRepo
import ru.mironov.drawpathonmaptesttask.model.geojson.GeoJsonParser
import ru.mironov.drawpathonmaptesttask.model.geojson.GeoJsonParser.getParserType
import ru.mironov.drawpathonmaptesttask.model.geojson.GeoJsonProvider

class MainViewModel : ViewModel() {

    private var repository: Repository = Repository()

    var arrayPolylines = arrayListOf<Polyline>()

    val viewModelStatus: MutableLiveData<Status> = MutableLiveData<Status>()

    private lateinit var parser: String

    init {
        setupObserver()
    }

    private fun setupObserver() {
        repository.dataStatus.observeForever { status ->
            when (status) {
                is StatusRepo.RESPONSE -> {
                    viewModelScope.launch (Dispatchers.IO) {
                        //Parse and crate list of polylines
                        if (status.geoJsonString.isNotEmpty()) {
                            val geoJson =
                                if (status.geoJsonString.isNotEmpty()) GeoJsonParser.parse(
                                    jsonString = status.geoJsonString,
                                    parserType = parser.getParserType()
                                )
                                else null

                            arrayPolylines = PolylinesParser.parsePolylines(geoJson)
                            if (arrayPolylines.size > 0) {
                                viewModelStatus.postValue(Status.RESPONSE(arrayPolylines))
                            } else {
                                //если почему-то координат нет
                                viewModelStatus.postValue(Status.ERROR("coords are empty"))
                            }
                        } else {
                            //если пустой ответ
                            viewModelStatus.postValue(Status.ERROR("body is empty"))
                        }
                    }
                }
                is StatusRepo.ERROR -> {
                    viewModelStatus.postValue(Status.ERROR(""))
                }
            }
        }
    }

    fun getGeoJson(selectedParser: String) {
        this.parser = selectedParser
        viewModelScope.launch(Dispatchers.IO) {
            viewModelStatus.postValue(Status.LOADING)
            repository.getGeoJsonWeb()
        }
    }

    fun getGeoJsonRes(context: Context, selectedParser: String) {
        this.parser = selectedParser
        viewModelScope.launch (Dispatchers.Default) {
            viewModelStatus.postValue(Status.LOADING)
            repository.getGeoJsonRes(GeoJsonProvider.readJson(context))
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

}
