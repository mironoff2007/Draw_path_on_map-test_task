package ru.mironov.drawpathonmaptesttask.model


data class GeoJsonGsonWoAn(

    var type: String? = null,

    var features: ArrayList<Feature?>? = null
) {

    class Feature(

        var type: String? = null,

        var geometry: Geometry? = null
    )

    class Geometry(
        var type: String? = null,

        var coordinates: ArrayList<ArrayList<ArrayList<ArrayList<Double>?>?>?>? = null
    )
}