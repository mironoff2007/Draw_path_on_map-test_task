package ru.mironov.drawpathonmaptesttask


import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mironov.drawpathonmaptesttask.web.NetworkService

class Repository(var dataStatus: MutableLiveData<Status>) {

    var geoJson: JsonObject? = null

    fun getGeoJson() {
        NetworkService
            .getJSONApi()
            .getGeoJson()
            ?.enqueue(object : Callback<JsonObject?> {

                override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                    dataStatus.postValue(Status.ERROR)
                }

                override fun onResponse(call: Call<JsonObject?>, response: Response<JsonObject?>) {
                    if (response.body() == null) {
                        dataStatus.postValue(Status.ERROR)
                    } else {
                        geoJson = response.body()
                        dataStatus.postValue(Status.RESPONSE)
                    }
                }
            })
    }
}