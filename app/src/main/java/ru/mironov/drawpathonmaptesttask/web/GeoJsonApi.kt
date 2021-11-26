package ru.mironov.drawpathonmaptesttask.web


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import ru.mironov.drawpathonmaptesttask.MyJsonObject

interface GeoJsonApi {
    @GET("russia.geo.json")
    fun getGeoJson(): Call<MyJsonObject>?
}
