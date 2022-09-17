package ru.mironov.drawpathonmaptesttask

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mironov.drawpathonmaptesttask.model.geojson.*
import ru.mironov.drawpathonmaptesttask.web.NetworkService

class Repository() {

    val dataStatus: MutableLiveData<StatusRepo?> = MutableLiveData(null)

    var parseType = GeoJsonParser.Parser.KOTLINX

    fun getGeoJsonWeb() {
        NetworkService
            .getJSONApi()
            .getGeoJson()
            ?.enqueue(object : Callback<String?> {

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    dataStatus.postValue(StatusRepo.ERROR(""))
                }

                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    if (response.body() == null) {
                        dataStatus.postValue(StatusRepo.ERROR(""))
                    } else {
                        val jsonString = response.body() ?: ""
                        val geoJson = GeoJsonParser.parse(jsonString, parseType)
                        dataStatus.postValue(StatusRepo.RESPONSE(geoJson))
                    }
                }
            })
    }

    fun getGeoJsonRes(jsonString: String) {
        val geoJson = GeoJsonParser.parse(jsonString, parseType)
        dataStatus.postValue(StatusRepo.RESPONSE(geoJson))
    }

}