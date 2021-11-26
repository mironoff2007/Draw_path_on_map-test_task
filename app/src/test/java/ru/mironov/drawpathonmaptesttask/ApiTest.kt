package ru.mironov.drawpathonmaptesttask

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import ru.mironov.drawpathonmaptesttask.web.NetworkService

class ApiTest {

    var jo: JsonObject? = null
    var features: JsonObject? = null
    var geometry: JsonObject? = null
    var polygons = arrayListOf<JsonArray?>()


    @Test
    fun getRequestTest() {

        val call: Call<JsonObject>? = NetworkService.getJSONApi().getGeoJson()
        val response: Response<JsonObject> = call!!.execute()
        jo = response.body()

        features = jo?.get("features")?.asJsonArray?.get(0) as JsonObject?
        geometry = features?.get("geometry") as JsonObject?
        val coordinates = geometry?.get("coordinates")?.asJsonArray

        var i = 0
        var sizeCoords = 0
        if (coordinates?.size() != null) {
            sizeCoords = coordinates?.size()
        }
        while (i < sizeCoords) {
            var p = coordinates?.get(i) as JsonArray?
            p = p?.get(0) as JsonArray?
            polygons.add(p)
            i++

            var j = 0
            var sizePoligons = 0
            if (p?.size() != null) {
                sizePoligons = p?.size()
            }
            while (j < sizePoligons) {
                val poly = p?.get(j) as JsonArray?
                j++
                System.out.println(poly?.get(0).toString() + "/" + poly?.get(1).toString())
            }
        }
        assert(polygons.size==213)
    }

}