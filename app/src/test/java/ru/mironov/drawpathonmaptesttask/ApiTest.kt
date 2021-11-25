package ru.mironov.drawpathonmaptesttask

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class ApiTest {

    var jo:JsonObject?=null
    var f:JsonObject?=null
    var g:JsonObject?=null
   var polygons=arrayListOf<JsonArray?>()
    var polygon=arrayListOf<JsonArray?>()


    @Test
    fun getRequestTest() {

        val  call:Call<JsonObject>? = NetworkService.getJSONApi().getGeoJson()
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