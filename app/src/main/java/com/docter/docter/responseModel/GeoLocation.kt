package com.docter.docter.responseModel

import java.io.Serializable

data class GeoLocation(
    var lon: Double = 0.toDouble(),
    var lat: Double = 0.toDouble()
) : Serializable {
    override fun toString(): String {
        return ""
    }
}
