package ru.mironov.drawpathonmaptesttask

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline

class GeoJsonParser {

    private var arrayPoints = arrayListOf<Point>()


    fun parsePolylines(jsonObject: MyJsonObject?): ArrayList<Polyline> {
        val arrayPolylines = arrayListOf<Polyline>()

        val feature= jsonObject?.features?.get(0)
        //List of Poligons/Polylines
        feature?.geometry?.coordinates?.forEach (){
            //List of points
            //List of polylines has list with 1 value
            val arrayOfPoints = arrayListOf<Point>()
            it?.get(0)?.forEach() {
                //Add point
                if(it?.get(1)!==null&&it?.get(0)!==null){
                arrayOfPoints.add(Point(it?.get(1),it?.get(0)))}
            }
            //add polyline
            arrayPolylines.add(Polyline(arrayOfPoints))
        }
        return arrayPolylines
    }
}