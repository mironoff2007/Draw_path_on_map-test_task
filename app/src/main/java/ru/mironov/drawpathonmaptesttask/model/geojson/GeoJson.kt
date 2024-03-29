package ru.mironov.drawpathonmaptesttask.model.geojson

data class GeoJson(
    var type: String? = null,

    var features: List<Feature?>? = null
) {

    class Feature(
        var type: String? = null,

        var geometry: Geometry? = null
    )

    class Geometry(
        var type: String? = null,

        var coordinates: List<List<List<List<Double>?>?>?>? = null
    )
}