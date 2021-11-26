package ru.mironov.drawpathonmaptesttask


import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mironov.drawpathonmaptesttask.model.MyJsonObject
import ru.mironov.drawpathonmaptesttask.web.NetworkService

class Repository(var dataStatus: MutableLiveData<Status>) {

    var geoJson: MyJsonObject? = null

    fun getGeoJson() {
        NetworkService
            .getJSONApi()
            .getGeoJson()
            ?.enqueue(object : Callback<MyJsonObject?> {

                override fun onFailure(call: Call<MyJsonObject?>, t: Throwable) {
                    dataStatus.postValue(Status.ERROR)
                }

                override fun onResponse(call: Call<MyJsonObject?>, response: Response<MyJsonObject?>) {
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