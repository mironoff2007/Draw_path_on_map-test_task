package ru.mironov.drawpathonmaptesttask.model


import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GeoJackson(
    @field:JsonProperty("type")
    @param:JsonProperty("type")
    var type: String? = null,

    @field:JsonProperty("features")
    @param:JsonProperty("features")
    var features: ArrayList<Feature?>? = null
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Feature @JsonCreator constructor(
        @field:JsonProperty("type")
        @param:JsonProperty("type")
        var type: String? = null,

        @field:JsonProperty("geometry")
        @param:JsonProperty("geometry")
        var geometry: Geometry? = null
    )

    class Geometry @JsonCreator constructor(
        @field:JsonProperty("type")
        @param:JsonProperty("type")
        var type: String? = null,

        @field:JsonProperty("coordinates")
        @param:JsonProperty("coordinates")
        var coordinates: ArrayList<ArrayList<ArrayList<ArrayList<Double>?>?>?>? = null
    )
}