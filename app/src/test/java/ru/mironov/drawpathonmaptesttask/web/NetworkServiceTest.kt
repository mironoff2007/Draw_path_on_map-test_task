package ru.mironov.drawpathonmaptesttask.web

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object NetworkServiceTest {

    private const val BASE_URL = "https://waadsu.com/api/"
    private var mRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    fun getJSONApi(): GeoJsonApiTest {
        return mRetrofit.create(GeoJsonApiTest::class.java)
    }
}


