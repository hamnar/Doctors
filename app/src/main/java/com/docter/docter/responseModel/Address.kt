package com.docter.docter.responseModel

import java.io.Serializable

data class Address(
    var lineAddress: String? = "",

    var city: String? = "",

    var state: String? = "",

    var zip: String? = "",

    var phone: String? = "",

    var geoLocation: GeoLocation? = null,

    var distance: Double = 0.toDouble()

) : Serializable {
    override fun toString(): String {
        return ""
    }
}
