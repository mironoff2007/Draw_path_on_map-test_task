package ru.mironov.drawpathonmaptesttask


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface GeoJsonApi {
    @GET("russia.geo.json")
    fun getGeoJson(): Call<JsonObject>?
}
