package ru.mironov.drawpathonmaptesttask

import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import org.junit.Test

class DistanceCalcTest{
    @Test
    fun calcDistanceTest() {
        val calc=DistanceCalculator()
        val d=calc.calcDist(Point(60.06,28.47),
            Point(66.79,183.51))
        System.out.println((d/1000).toInt())
        assert((d/1000).toInt()==874)
    }

    @Test
    fun calcDistanceTest2() {
        val polyline= arrayListOf(Point(57.51,29.53),
            Point(66.37,184.57),
            Point(18.64,182.11)
        )
        var length=0.0
        var pointLast:Point?=null

        val calc=DistanceCalculator()
            polyline.forEach {
                if(pointLast!=null){
                    length=length+calc.calcDist(pointLast!!,it)}
                pointLast=it
            }


        System.out.println((length/1000).toInt())
        assert((length/1000).toInt()==11406)
    }
}