package ru.mironov.draw_path_on_map_test_task

import com.google.gson.JsonObject
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import org.junit.Assert.*
import ru.mironov.draw_path_on_map_test_task.NetworkService

class ApiTest {

    var jo:JsonObject?=null
    @Test
    fun getRequestTest() {

        val  call:Call<JsonObject>? = NetworkService.getJSONApi().getCurrencyMap()
        val response: Response<JsonObject> =call!!.execute()
        jo=response.body()

        val s= jo?.get("type")?.asString
        assertEquals(true, s == "FeatureCollection")
    }

}