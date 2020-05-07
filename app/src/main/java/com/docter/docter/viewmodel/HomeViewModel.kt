/*
 * Created by M1028379 on 18/12/2019
 * Copyright (c) CARTUS B2B 2019 .
 * All rights reserved.
 * Last modified 12/17/19 8:02 PM
 *
 * CHANGE LOG
 * —------------------------------------------------------
 * ID     Bug ID   Author Name     Date         Description
 * —------------------------------------------------------
 */

package com.docter.docter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.docter.docter.lisener.LocationSearchListener
import com.docter.docter.network.DoctorsNetwork
import com.docter.docter.responseModel.LocationSearchResponseModel

class HomeViewModel : ViewModel(), LocationSearchListener {

    override fun onLocationSearchSuccess(response: LocationSearchResponseModel?) {
        this.locationSearchResponseModel.value = response
        this.locationSearchSuccess.value = true
        this.locationSearchFailed.value = false

    }

    override fun onLocationSearchFailure() {
        this.locationSearchSuccess.value = false
        this.locationSearchFailed.value = true
    }

    var locationSearchSuccess = MutableLiveData<Boolean>()
    var locationSearchFailed = MutableLiveData<Boolean>()
    var locationSearchResponseModel: MutableLiveData<LocationSearchResponseModel> =
        MutableLiveData()

    init {
        this.locationSearchSuccess.value = false
        this.locationSearchFailed.value = false
    }

    fun getSearchedLocation1(typedTxt: String): LocationSearchResponseModel? {
        return DoctorsNetwork.getSearchedLocation1(typedTxt)
    }

    fun getSearchedLocation(typedTxt: String) {
        DoctorsNetwork.getSearchedLocation(typedTxt, this)
    }

}