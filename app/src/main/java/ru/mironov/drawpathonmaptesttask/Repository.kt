package ru.mironov.drawpathonmaptesttask


import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mironov.drawpathonmaptesttask.model.GeoJsonKotlinSerialization
import ru.mironov.drawpathonmaptesttask.web.NetworkService

class Repository(var dataStatus: MutableLiveData<Status>) {

    var geoJson: GeoJsonKotlinSerialization? = null

    fun getGeoJson() {
        NetworkService
            .getJSONApi()
            .getGeoJson()
            ?.enqueue(object : Callback<String?> {

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    dataStatus.postValue(Status.ERROR)
                }

                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    if (response.body() == null) {
                        dataStatus.postValue(Status.ERROR)
                    } else {
                        val format = Json { ignoreUnknownKeys = true }
                        geoJson = format.decodeFromString(
                            GeoJsonKotlinSerialization.serializer(),
                            response.body()!!
                        )
                        dataStatus.postValue(Status.RESPONSE)
                    }
                }
            })
    }
}