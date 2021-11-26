package ru.mironov.drawpathonmaptesttask

import com.yandex.mapkit.geometry.Point
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
}