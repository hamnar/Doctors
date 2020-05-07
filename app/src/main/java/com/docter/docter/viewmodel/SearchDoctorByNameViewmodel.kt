package com.docter.docter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.docter.docter.lisener.SeacrhDoctorByNameListener
import com.docter.docter.network.DoctorsNetwork
import com.docter.docter.responseModel.SearchByDoctorNameResponseModel
import com.docter.docter.responseModel.SearchedItemList

class SearchDoctorByNameViewmodel : ViewModel(), SeacrhDoctorByNameListener {

    var searchByDoctorTypeSuccess = MutableLiveData<Boolean>()
    var searchByDoctorTypeFailed = MutableLiveData<Boolean>()
    var searchByDoctorTypeResponseModel: MutableLiveData<SearchByDoctorNameResponseModel> = MutableLiveData()
    var enableProgressBar = MutableLiveData<Boolean>()

    init {
        this.searchByDoctorTypeSuccess.value = false
        this.searchByDoctorTypeFailed.value = false
    }

    override fun onSeacrhDoctorByNameSuccess(response: SearchByDoctorNameResponseModel?) {
        this.searchByDoctorTypeResponseModel.value = response
        this.searchByDoctorTypeSuccess.value = true
        this.searchByDoctorTypeFailed.value = false
        enableProgressBar.value = false
    }

    override fun onSeacrhDoctorByNameFailure() {
        this.searchByDoctorTypeSuccess.value = false
        this.searchByDoctorTypeFailed.value = true
        enableProgressBar.value = false
    }

    fun getSearchedDoctorbyName(searchedItemList: SearchedItemList) {
        enableProgressBar.value = true
        DoctorsNetwork.getSearchedDoctorbyName(searchedItemList, this)
    }

}