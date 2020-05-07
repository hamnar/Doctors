package com.docter.docter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.docter.docter.lisener.StateWiseListener
import com.docter.docter.network.DoctorsNetwork
import com.docter.docter.responseModel.StateWiseBase

class StateWiseViewmodel : ViewModel(), StateWiseListener {

    var statewiseSuccess = MutableLiveData<Boolean>()
    var statewiseFailed = MutableLiveData<Boolean>()
    var enableProgressBar = MutableLiveData<Boolean>()
    var stateWiseBaseResponseModel: MutableLiveData<StateWiseBase> = MutableLiveData()

    override fun onStateWiseSuccess(response: StateWiseBase?) {
        this.stateWiseBaseResponseModel.value = response
        this.statewiseSuccess.value = true
        this.statewiseFailed.value = false
        enableProgressBar.value = false
    }

    override fun onStateWiseFailure() {
        this.statewiseSuccess.value = false
        this.statewiseFailed.value = true
        enableProgressBar.value = false
    }
    fun getAllStatewise() {
        enableProgressBar.value = true
        DoctorsNetwork.getStatewiseDaily(this)
    }

}