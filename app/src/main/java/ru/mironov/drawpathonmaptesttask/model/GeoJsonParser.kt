package ru.mironov.drawpathonmaptesttask.model

import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline

class GeoJsonParser {

    private var arrayPoints = arrayListOf<Point>()

    fun parsePolylines(coords:  List<List<List<List<Double>?>?>?>? ): ArrayList<Polyline> {
        val arrayPolylines = arrayListOf<Polyline>()

        //List of Poligons/Polylines
        coords?.forEach() { coordinates ->
            //List of points
            //List of polylines has list with 1 value
            val arrayOfPoints = arrayListOf<Point>()
            coordinates?.get(0)?.forEach() { coordinatePair ->
                //Add point
                if (coordinatePair?.get(1) !== null) {
                    arrayOfPoints.add(Point(coordinatePair[1], coordinatePair[0]))
                }
            }
            //add polyline
            arrayPolylines.add(Polyline(arrayOfPoints))
        }
        return arrayPolylines
    }
}