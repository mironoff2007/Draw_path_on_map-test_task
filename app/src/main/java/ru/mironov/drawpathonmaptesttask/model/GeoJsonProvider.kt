package ru.mironov.drawpathonmaptesttask.model

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.squareup.moshi.Moshi
import ru.mironov.drawpathonmaptesttask.R


object GeoJsonParserProvider {

    fun readJson(context: Context): String {
        return  try {
            val res: Resources = context.resources
            val inputStream = res.openRawResource(R.raw.geojson)
            val b = ByteArray(inputStream.available())
            inputStream.read(b)
            b.decodeToString()
        } catch (e: Exception) {
            Log.d("JSON", e.stackTrace.joinToString("."))
            ""
        }
    }
}