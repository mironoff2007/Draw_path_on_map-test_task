package ru.mironov.drawpathonmaptesttask

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import ru.mironov.drawpathonmaptesttask.web.NetworkServiceTest

class ApiTest {

    var jo: MyJsonObject? = null

    @Test
    fun getRequestTest() {

        val call: Call<MyJsonObject>? = NetworkServiceTest.getJSONApi().getGeoJson()
        val response: Response<MyJsonObject> = call!!.execute()
        jo = response.body()
        assert(jo?.features?.get(0)?.geometry?.coordinates?.size==213)
    }

}