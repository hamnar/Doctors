package com.docter.docter.responseModel

import java.io.Serializable

data class Data(
    var id: Int = 0,

    var credential: List<String>? = arrayListOf(),

    var isPhoto: String? = "",

    var gender: String? = "",

    var name: String? = "",

    var speciality: List<String>? = arrayListOf(),

    var address: Address? = null
) : Serializable {
    override fun toString(): String {
        return ""
    }
}
