package ru.mironov.drawpathonmaptesttask.model

import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import ru.mironov.drawpathonmaptesttask.model.geojson.GeoJson

object PolylinesParser {

        fun parsePolylines(jsonObject: GeoJson?): ArrayList<Polyline> {
            val arrayPolylines = arrayListOf<Polyline>()

            val feature = jsonObject?.features?.get(0)
            //List of Poligons/Polylines
            feature?.geometry?.coordinates?.forEach() { coordinates ->
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