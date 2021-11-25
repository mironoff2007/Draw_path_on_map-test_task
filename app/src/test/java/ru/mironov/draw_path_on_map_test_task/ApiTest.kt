package ru.mironov.draw_path_on_map_test_task

import com.google.gson.JsonObject
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import org.junit.Assert.*
import ru.mironov.draw_path_on_map_test_task.NetworkService

class ApiTest {

    @Test
    fun getRequestTest() {

        val  call:Call<JsonObject>? = NetworkService.getJSONApi().getCurrencyMap()
        val response: Response<JsonObject> =call!!.execute()
        var b=response.body()
        assertEquals(true, true)
    }

}