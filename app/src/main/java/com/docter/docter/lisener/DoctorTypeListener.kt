package com.docter.docter.lisener

import com.docter.docter.responseModel.DoctorTypeResponseModel


interface DoctorTypeListener {
    fun onDoctorTypeSuccess(response: DoctorTypeResponseModel?)
    fun onDoctorTypeFailure()
}