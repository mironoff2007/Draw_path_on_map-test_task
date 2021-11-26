package ru.mironov.drawpathonmaptesttask.web


import com.google.gson.JsonObject
import com.yandex.mapkit.GeoObject
import retrofit2.Call
import retrofit2.http.GET

interface GeoJsonApiTest {
    @GET("russia.geo.json")
    fun getGeoJson(): Call<MyJsonObject>?
}
