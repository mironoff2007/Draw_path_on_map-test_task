package ru.mironov.drawpathonmaptesttask


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.mironov.drawpathonmaptesttask.model.*
import ru.mironov.drawpathonmaptesttask.web.NetworkService

class Repository(var dataStatus: MutableLiveData<Status>) {

    var geoJson: GeoJsonKotlinSerialization? = null

    enum class Parser {
        GSON, GSON_WO_AN, JACKSON, KOTLINX, MOSHI
    }

    var parseType = Parser.KOTLINX

    fun getGeoJson() {
        NetworkService
            .getJSONApi()
            .getGeoJson()
            ?.enqueue(object : Callback<String?> {

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    dataStatus.postValue(Status.ERROR)
                }

                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    if (response.body() == null) {
                        dataStatus.postValue(Status.ERROR)
                    } else {
                        val jsonString = response.body()
                        var time = 0L
                        when (parseType){
                            Parser.GSON ->{
                                val gson = Gson()
                                gson.serializeNulls()

                                time = System.currentTimeMillis()
                                val geoJson: GeoJsonGson = gson.fromJson(jsonString, object : TypeToken<GeoJsonGsonWoAn>() {}.type)
                            }
                            Parser.GSON_WO_AN->{
                                val gson = Gson()
                                gson.serializeNulls()

                                time = System.currentTimeMillis()
                                val geoJson: GeoJsonGsonWoAn = gson.fromJson(jsonString, object : TypeToken<GeoJsonGsonWoAn>() {}.type)
                            }
                            Parser.JACKSON -> {
                                val mapper = ObjectMapper()
                                time = System.currentTimeMillis()
                                val geoJson: GeoJackson = mapper.readValue(jsonString, GeoJackson::class.java)
                            }
                            Parser.KOTLINX ->{
                                time = System.currentTimeMillis()
                                val format = Json { ignoreUnknownKeys = true }
                                geoJson = format.decodeFromString(GeoJsonKotlinSerialization.serializer(), response.body()!!)
                            }
                            Parser.MOSHI ->{
                                val moshi = Moshi.Builder().build()
                                time = System.currentTimeMillis()
                                val jsonAdapter: JsonAdapter<GeoJsonMoshi> = moshi.adapter(
                                    GeoJsonMoshi::class.java
                                )
                                val geoJson: GeoJsonMoshi = jsonAdapter.fromJson(jsonString)!!
                            }

                        }
                        Log.d("PARSE_JSON", "parse time;" + (time - System.currentTimeMillis()))

                        dataStatus.postValue(Status.RESPONSE)
                    }
                }
            })
    }
}