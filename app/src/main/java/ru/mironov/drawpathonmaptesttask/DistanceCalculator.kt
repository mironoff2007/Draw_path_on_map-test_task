package ru.mironov.drawpathonmaptesttask

import com.yandex.mapkit.geometry.Point

class DistanceCalculator{
    /*
       * Calculate distance between two points in latitude and longitude taking
       * into account height difference. If you are not interested in height
       * difference pass 0.0. Uses Haversine method as its base.
       * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
       * el2 End altitude in meters
       * @returns Distance in Meters
       */
    fun calcDist(p1: Point, p2:Point):Double{
        return distance(p1.latitude,p2.latitude,p1.longitude,p2.longitude,0.0,0.0)
    }

    fun distance(
        lat1: Double, lat2: Double, lon1: Double,
        lon2: Double, el1: Double, el2: Double
    ): Double {
        val R = 6371 // Radius of the earth
        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + (Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2)))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        var distance = R * c * 1000 // convert to meters
        val height = el1 - el2
        distance = Math.pow(distance, 2.0) + Math.pow(height, 2.0)
        return Math.sqrt(distance)
    }
}