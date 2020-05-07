package com.docter.docter.responseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchedItemList(
    @SerializedName("type")
    @Expose
    var type: String = "",
    @SerializedName("speciality")
    @Expose
    var speciality: String = "",
    @SerializedName("subSpeciality")
    @Expose
    var subSpeciality: String = "",
    @SerializedName("name")
    @Expose
    var name: String = "",
    @SerializedName("location")
    @Expose
    var location: String = "",
    @SerializedName("city")
    @Expose
    var city: String = "",
    @SerializedName("state")
    @Expose
    var state: String = "",
    @SerializedName("lon")
    @Expose
    var lon: Double = 0.0,
    @SerializedName("lng")
    @Expose
    var lng: Double = 0.0,
    @SerializedName("lat")
    @Expose
    var lat: Double = 0.0,
    @SerializedName("distance")
    @Expose
    var distance: Int = 0,
    @SerializedName("zip")
    @Expose
    var zip: Int = 0,
    @SerializedName("zipsort")
    @Expose
    var zipsort: String = ""
) : Serializable {
    override fun toString(): String {
        return ""
    }
}

