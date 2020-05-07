package com.docter.docter.responseModel

import com.google.gson.annotations.SerializedName


data class DoctorTypeResponseModel(
    @SerializedName("data") val result: List<DoctorTypeList>
)