package com.docter.docter.responseModel

import com.google.gson.annotations.SerializedName

data class DoctorTypeList (
    @SerializedName("value")
    var type: String = ""
)