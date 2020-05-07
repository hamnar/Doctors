package com.docter.docter.lisener

import com.docter.docter.responseModel.SearchByDoctorNameResponseModel


interface SeacrhDoctorByNameListener {
    fun onSeacrhDoctorByNameSuccess(response: SearchByDoctorNameResponseModel?)
    fun onSeacrhDoctorByNameFailure()
}