package ru.mironov.drawpathonmaptesttask


import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(var dataRemoteStatus: MutableLiveData<RemoteStatus>) {

   var geoJson :JsonObject?=null

    fun getCurrencyFromWeb(curToCur: String, key: String) {
        NetworkService
            .getJSONApi()
            .getGeoJson()
            ?.enqueue(object : Callback<JsonObject?> {

                override fun onFailure(call: Call<JsonObject?>, t: Throwable) {
                    dataRemoteStatus.postValue(RemoteStatus.ERROR)
                }

                override fun onResponse(call: Call<JsonObject?>, response: Response<JsonObject?>) {
                    if (response.body() == null) {
                        dataRemoteStatus.postValue(RemoteStatus.ERROR)
                    } else {
                       geoJson=response.body()
                        dataRemoteStatus.postValue(RemoteStatus.RESPONSE)
                    }
                }
            })
    }
}