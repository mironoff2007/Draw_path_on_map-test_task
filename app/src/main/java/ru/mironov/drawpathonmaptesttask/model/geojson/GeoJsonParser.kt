package ru.mironov.drawpathonmaptesttask.model.geojson

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.serialization.json.Json

object GeoJsonParser {

    enum class Parser {
        GSON, GSON_WO_AN, JACKSON, KOTLINX, MOSHI
    }

    fun String.getParserType(): Parser {
        return Parser.valueOf(this)
    }

    fun parse(jsonString: String, parserType: Parser): GeoJson{
        var time = 0L
        val geoJson = when (parserType){
            Parser.GSON ->{
                val gson = Gson()
                gson.serializeNulls()

                time = System.currentTimeMillis()
                val geoJson: GeoJsonGson = gson.fromJson(jsonString, object : TypeToken<GeoJsonGson>() {}.type)
                geoJson.toGeoJson()
            }
            Parser.GSON_WO_AN->{
                val gson = Gson()
                gson.serializeNulls()

                time = System.currentTimeMillis()
                val geoJson: GeoJsonGsonWoAn = gson.fromJson(jsonString, object : TypeToken<GeoJsonGsonWoAn>() {}.type)
                geoJson.toGeoJson()
            }
            Parser.JACKSON -> {
                val mapper = ObjectMapper()
                time = System.currentTimeMillis()
                val geoJson: GeoJackson = mapper.readValue(jsonString, GeoJackson::class.java)
                geoJson.toGeoJson()
            }
            Parser.KOTLINX ->{
                time = System.currentTimeMillis()
                val format = Json { ignoreUnknownKeys = true }
                val geoJson = format.decodeFromString(GeoJsonKotlinSerialization.serializer(), jsonString)
                geoJson.toGeoJson()
            }
            Parser.MOSHI ->{
                val moshi = Moshi.Builder().build()
                time = System.currentTimeMillis()
                val jsonAdapter: JsonAdapter<GeoJsonMoshi> = moshi.adapter(
                    GeoJsonMoshi::class.java
                )
                val geoJson: GeoJsonMoshi = jsonAdapter.fromJson(jsonString)!!
                geoJson.toGeoJson()
            }
        }
        Log.d("Test_tag", "$parserType;" + (time - System.currentTimeMillis()))
        return geoJson
    }
}