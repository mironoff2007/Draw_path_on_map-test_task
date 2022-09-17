package ru.mironov.drawpathonmaptesttask

import ru.mironov.drawpathonmaptesttask.model.geojson.GeoJson

sealed class StatusRepo {
 object LOADING: StatusRepo()
 data class ERROR(val msg: String): StatusRepo()
 data class RESPONSE(val geoJson: GeoJson): StatusRepo()
}
