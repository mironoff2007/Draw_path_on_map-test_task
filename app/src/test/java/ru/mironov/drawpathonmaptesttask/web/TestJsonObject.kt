package ru.mironov.drawpathonmaptesttask.web



import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TestJsonObject {
    @SerialName("type")
    var type: String? = null

    @SerialName("features")
    var features:ArrayList<Feature?>?=null

    @Serializable
    class Feature{

        @SerialName("type")
        var type: String? = null

        @SerialName("geometry")
        var geometry: Geometry?=null
    }
    @Serializable
    class Geometry{
        @SerialName("type")
        var type: String? = null

        @SerialName("coordinates")
        var coordinates:ArrayList<ArrayList<ArrayList<ArrayList<Double>?>?>?>?=null
    }
}