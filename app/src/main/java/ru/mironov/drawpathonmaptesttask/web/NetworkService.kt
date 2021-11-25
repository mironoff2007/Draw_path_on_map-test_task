package ru.mironov.drawpathonmaptesttask.web

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {


    private const val BASE_URL = "https://waadsu.com/api/"
    private var mRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun getJSONApi(): GeoJsonApi {
        return mRetrofit.create(GeoJsonApi::class.java)
    }

}


