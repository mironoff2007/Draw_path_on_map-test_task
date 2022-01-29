package ru.mironov.drawpathonmaptesttask

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import ru.mironov.drawpathonmaptesttask.web.NetworkServiceTest
import ru.mironov.drawpathonmaptesttask.web.TestJsonObject

class ApiTest {



    @Test
    fun getRequestTest() {

        val call: Call<String>? = NetworkServiceTest.getJSONApi().getGeoJson()
        val response: Response<String> = call!!.execute()
        val raw = response.body()

        val format = Json { ignoreUnknownKeys = true }
        val obj = raw?.let { format.decodeFromString(TestJsonObject.serializer(),raw) }
        assert(true)
    }

}