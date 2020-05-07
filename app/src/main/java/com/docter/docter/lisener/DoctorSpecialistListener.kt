package com.docter.docter.lisener

import com.docter.docter.responseModel.DoctorTypeResponseModel


interface DoctorSpecialistListener {
    fun onDoctorSpecialistSuccess(response: DoctorTypeResponseModel?)
    fun onDoctorSpecialistFailure()
}