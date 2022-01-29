package ru.mironov.drawpathonmaptesttask.web

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkService {

    private const val BASE_URL = "https://waadsu.com/api/"
    private var mRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    fun getJSONApi(): GeoJsonApi {
        return mRetrofit.create(GeoJsonApi::class.java)
    }
}


