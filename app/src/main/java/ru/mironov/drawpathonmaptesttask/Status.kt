package ru.mironov.drawpathonmaptesttask

import com.yandex.mapkit.geometry.Polyline

sealed class Status {
 object LOADING: Status()
 data class ERROR(val msg: String): Status()
 data class RESPONSE(val lines: List<Polyline>): Status()
}
