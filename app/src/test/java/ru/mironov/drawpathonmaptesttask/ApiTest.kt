package ru.mironov.drawpathonmaptesttask

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.yandex.mapkit.GeoObject
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import ru.mironov.drawpathonmaptesttask.web.MyJsonObject
import ru.mironov.drawpathonmaptesttask.web.NetworkServiceTest

class ApiTest {

    var jo: MyJsonObject? = null
    var features: JsonObject? = null
    var geometry: JsonObject? = null
    var polygons = arrayListOf<JsonArray?>()


    @Test
    fun getRequestTest() {

        val call: Call<MyJsonObject>? = NetworkServiceTest.getJSONApi().getGeoJson()
        val response: Response<MyJsonObject> = call!!.execute()
        jo = response.body()
        System.out.println()

    }

}