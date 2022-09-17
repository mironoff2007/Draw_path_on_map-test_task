package ru.mironov.drawpathonmaptesttask

sealed class StatusRepo {
 object LOADING: StatusRepo()
 data class ERROR(val msg: String): StatusRepo()
 data class RESPONSE(val geoJsonString: String): StatusRepo()
}
