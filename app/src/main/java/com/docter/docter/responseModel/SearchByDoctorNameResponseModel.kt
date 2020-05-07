package com.docter.docter.responseModel

import java.io.Serializable

data class SearchByDoctorNameResponseModel(
    var data: List<Data>? = null,
    var count: Int = 0
) : Serializable {
    override fun toString(): String {
        return ""
    }
}