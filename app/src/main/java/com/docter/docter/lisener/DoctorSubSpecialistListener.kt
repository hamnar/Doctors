package com.docter.docter.lisener

import com.docter.docter.responseModel.DoctorTypeResponseModel


interface DoctorSubSpecialistListener {
    fun onDoctorSubSpecialistSuccess(response: DoctorTypeResponseModel?)
    fun onDoctorSubSpecialistFailure()
}