package ru.mironov.drawpathonmaptesttask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Geo
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline

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
                    if (repository.geoJson != null) {
                        arrayPolylines = parser.parsePolylines(repository.geoJson!!)
                        if (arrayPolylines.size > 0) {
                            viewModelStatus.postValue(Status.RESPONSE)
                        } else {
                            viewModelStatus.postValue(Status.ERROR)
                        }
                    } else {
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
        repository.getGeoJson()
        viewModelStatus.postValue(Status.LOADING)
    }

    fun calculateLengths(): Int {
        var length = 0.0
        var pointLast: Point? = null

        arrayPolylines.forEach {
            //Polyline next
            pointLast = null
            it.points.forEach {
                //Point next
                if (pointLast != null) {
                    //Calc on second point
                    length = length + Geo.distance(pointLast!!, it)
                }
                pointLast = it
            }
        }
        return (length / 1000).toInt()
    }
}
