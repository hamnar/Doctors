package com.docter.docter.responseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GeoLocationResponseModel(
    @SerializedName("lon")
    @Expose
    var lon: Double = 0.0,
    @SerializedName("lat")
    @Expose
    var lat: Double = 0.0
) : Serializable {
    override fun toString(): String {
        return ""
    }
}