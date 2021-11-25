package ru.mironov.draw_path_on_map_test_task

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import org.junit.Assert.*
import ru.mironov.draw_path_on_map_test_task.NetworkService

class ApiTest {

    var jo:JsonObject?=null
    var f:JsonObject?=null
    var g:JsonObject?=null
   var polygons=arrayListOf<JsonArray?>()
    var polygon=arrayListOf<JsonArray?>()


    @Test
    fun getRequestTest() {

        val  call:Call<JsonObject>? = NetworkService.getJSONApi().getCurrencyMap()
        val response: Response<JsonObject> =call!!.execute()
        jo=response.body()

        val s= jo?.get("type")?.asString
        f= jo?.get("features")?.asJsonArray?.get(0) as JsonObject?
        g= f?.get("geometry") as JsonObject?
        val c=g?.get("coordinates")?.asJsonArray

        var i=0
        var sizeCoords=0
        if(c?.size()!=null){sizeCoords=c?.size()}
        while(i<sizeCoords){
            var p=c?.get(i) as JsonArray?
            p=p?.get(0) as JsonArray?
            polygons.add(p)
            i++

            var j=0
            var sizePoligons=0
            if(p?.size()!=null){sizePoligons=p?.size()}
            while(j<sizePoligons){
                val poly=p?.get(j) as JsonArray?
                j++
                System.out.println(poly?.get(0).toString()+"/"+poly?.get(1).toString())
            }

        }

    }

}