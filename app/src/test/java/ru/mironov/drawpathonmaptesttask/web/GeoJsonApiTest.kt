package ru.mironov.drawpathonmaptesttask.web

import retrofit2.Call
import retrofit2.http.GET
import ru.mironov.drawpathonmaptesttask.model.MyJsonObject

interface GeoJsonApiTest {
    @GET("russia.geo.json")
    fun getGeoJson(): Call<String>?
}
