package ru.mironov.drawpathonmaptesttask

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline

class GeoJsonParser {

    private var arrayPoints = arrayListOf<Point>()

    fun parsePolylines(jsonObject: JsonObject): ArrayList<Polyline> {
        val arrayPolylines = arrayListOf<Polyline>()

        val features = jsonObject?.get("features")?.asJsonArray?.get(0) as JsonObject?
        val geometry = features?.get("geometry") as JsonObject?
        val coordinates = geometry?.get("coordinates")?.asJsonArray

        var sizeCoords = 0
        if (coordinates?.size() != null) {
            sizeCoords = coordinates?.size()
        }
        //Parse polylines
        for (i in 0 until sizeCoords) {
            var polylines = coordinates?.get(i) as JsonArray?
            polylines = polylines?.get(0) as JsonArray?

            //Parse Points
            var sizePoligons = 0
            if (polylines?.size() != null) {
                sizePoligons = polylines?.size()
            }
            for (j in 0 until sizePoligons) {
                val polyline = polylines?.get(j) as JsonArray?

                val coord1 = polyline?.get(0)?.asDouble
                val coord2 = polyline?.get(1)?.asDouble
                if (coord1 != null && coord2 != null) {
                    arrayPoints.add(Point(coord2, coord1))
                }
            }
            var polyline=Polyline(arrayPoints.toList())
            arrayPoints.clear()
            arrayPolylines.add(polyline)
        }
        return arrayPolylines
    }

}