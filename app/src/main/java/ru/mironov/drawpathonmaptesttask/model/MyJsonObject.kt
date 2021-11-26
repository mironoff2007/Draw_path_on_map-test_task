package ru.mironov.drawpathonmaptesttask.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
public class MyJsonObject {
    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("features")
    @Expose
    var features:ArrayList<Feature?>?=null

    class Feature{
        @SerializedName("type")
        @Expose
        var type: String? = null

        @SerializedName("geometry")
        @Expose
        var geometry: Geometry?=null
    }
    class Geometry{
        @SerializedName("type")
        @Expose
        var type: String? = null


        @SerializedName("coordinates")
        @Expose
        var coordinates:ArrayList<ArrayList<ArrayList<ArrayList<Double>?>?>?>?=null

    }
}