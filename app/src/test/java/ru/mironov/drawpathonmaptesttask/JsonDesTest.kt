package ru.mironov.drawpathonmaptesttask

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import ru.mironov.drawpathonmaptesttask.model.GeoJackson
import ru.mironov.drawpathonmaptesttask.model.GeoJsonGson
import ru.mironov.drawpathonmaptesttask.model.GeoJsonGsonWoAn
import ru.mironov.drawpathonmaptesttask.model.GeoJsonKotlinSerialization

class JsonDesTest {

    var time = 0L

    private val jsonString = GeoJson.getJsonString()

    @get:Rule
    var testName: TestName = TestName()

    @Before
    fun before(){
        println()
    }

    @Test
    fun gsonTest() {
        val gson = Gson()

        time = System.currentTimeMillis()

        val geoJson: GeoJsonGson = gson.fromJson(jsonString, object : TypeToken<GeoJsonGson>() {}.type)

        println(testName.methodName + "-" + (time - System.currentTimeMillis()))
        assert(geoJson.features!!.first()!!.geometry!!.coordinates!!.size == 213)
    }

    @Test
    fun gsonTestWithoutAnnotations() {
        val gson = Gson()

        time = System.currentTimeMillis()

        val geoJson: GeoJsonGsonWoAn = gson.fromJson(jsonString, object : TypeToken<GeoJsonGsonWoAn>() {}.type)

        println(testName.methodName + "-" + (time - System.currentTimeMillis()))
        assert(geoJson.features!!.first()!!.geometry!!.coordinates!!.size == 213)
    }

    @Test
    fun kotlinSerializationTest() {
        val geoJson: GeoJsonKotlinSerialization
        val format = Json { ignoreUnknownKeys = true }
        val serializer = GeoJsonKotlinSerialization.serializer()

        time = System.currentTimeMillis()
        geoJson = format.decodeFromString(serializer, jsonString)

        println(testName.methodName + "-" + (time - System.currentTimeMillis()))
        assert(geoJson.features!!.first()!!.geometry!!.coordinates!!.size == 213)
    }

    @Test
    fun jacksonTest() {
        val mapper = ObjectMapper()

        time = System.currentTimeMillis()
        val geoJson: GeoJackson = mapper.readValue(jsonString, GeoJackson::class.java)

        println(testName.methodName + "-" + (time - System.currentTimeMillis()))
        assert(geoJson.features!!.first()!!.geometry!!.coordinates!!.size == 213)
    }



}
