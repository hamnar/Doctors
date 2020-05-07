package com.docter.docter.lisener

import com.docter.docter.responseModel.LocationSearchResponseModel


interface LocationSearchListener {
    fun onLocationSearchSuccess(response: LocationSearchResponseModel?)
    fun onLocationSearchFailure()
}