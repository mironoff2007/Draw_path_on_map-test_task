package ru.mironov.drawpathonmaptesttask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yandex.mapkit.geometry.Polyline

class MainViewModel: ViewModel() {

    private lateinit var repository: Repository

    var arrayPolylines= arrayListOf<Polyline>()

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
                    val parser=GeoJsonParser()
                    if (repository.geoJson!=null){
                        arrayPolylines=parser.parsePolylines(repository.geoJson!!)
                    }
                    viewModelStatus.postValue(Status.RESPONSE)
                }
                Status.ERROR -> {
                        viewModelStatus.postValue(Status.ERROR)
                }
            }
        }
    }

    fun getGeoJson(){
        repository.getGeoJson()
        viewModelStatus.postValue(Status.LOADING)
    }


}
