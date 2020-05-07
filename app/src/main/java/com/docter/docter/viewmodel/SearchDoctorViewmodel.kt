package com.docter.docter.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.docter.docter.lisener.DoctorSpecialistListener
import com.docter.docter.lisener.DoctorSubSpecialistListener
import com.docter.docter.lisener.DoctorTypeListener
import com.docter.docter.lisener.SeacrhDoctorByNameListener
import com.docter.docter.network.DoctorsNetwork
import com.docter.docter.responseModel.DoctorTypeResponseModel
import com.docter.docter.responseModel.SearchByDoctorNameResponseModel
import com.docter.docter.responseModel.SearchedItemList

class SearchDoctorViewmodel : ViewModel(), DoctorTypeListener, DoctorSpecialistListener, DoctorSubSpecialistListener,
    SeacrhDoctorByNameListener {

    var doctorSubspecialistSuccess = MutableLiveData<Boolean>()
    var doctorSubspecialistFailed = MutableLiveData<Boolean>()
    var doctorSubspecialistResponseModel: MutableLiveData<DoctorTypeResponseModel> = MutableLiveData()
    var doctorSpecialistSuccess = MutableLiveData<Boolean>()
    var doctorSpecialistFailed = MutableLiveData<Boolean>()
    var doctorTypeSuccess = MutableLiveData<Boolean>()
    var doctorTypeFailed = MutableLiveData<Boolean>()
    var doctorTypeResponseModel: MutableLiveData<DoctorTypeResponseModel> = MutableLiveData()
    var doctorSpecialistResponseModel: MutableLiveData<DoctorTypeResponseModel> = MutableLiveData()
    var searchByDoctorTypeSuccess = MutableLiveData<Boolean>()
    var searchByDoctorTypeFailed = MutableLiveData<Boolean>()
    var searchByDoctorTypeResponseModel: MutableLiveData<SearchByDoctorNameResponseModel> = MutableLiveData()
    var enableProgressBar = MutableLiveData<Boolean>()

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

    override fun onDoctorSubSpecialistSuccess(response: DoctorTypeResponseModel?) {
        this.doctorSubspecialistResponseModel.value = response
        this.doctorSubspecialistSuccess.value = true
        this.doctorSubspecialistFailed.value = false
        enableProgressBar.value = false
    }

    override fun onDoctorSubSpecialistFailure() {
        this.doctorSubspecialistSuccess.value = false
        this.doctorSubspecialistFailed.value = true
        enableProgressBar.value = false
    }

    override fun onDoctorSpecialistSuccess(response: DoctorTypeResponseModel?) {
        this.doctorSpecialistResponseModel.value = response
        this.doctorSpecialistSuccess.value = true
        this.doctorSpecialistFailed.value = false
        enableProgressBar.value = false
    }

    override fun onDoctorSpecialistFailure() {
        this.doctorSpecialistSuccess.value = false
        this.doctorSpecialistFailed.value = true
        enableProgressBar.value = false
    }

    override fun onDoctorTypeSuccess(response: DoctorTypeResponseModel?) {
        this.doctorTypeResponseModel.value = response
        this.doctorTypeSuccess.value = true
        this.doctorTypeFailed.value = false
        enableProgressBar.value = false
    }

    override fun onDoctorTypeFailure() {
        this.doctorTypeFailed.value = true
        this.doctorTypeSuccess.value = false
        enableProgressBar.value = false
    }

    init {
        this.doctorTypeSuccess.value = false
        this.doctorTypeFailed.value = false
        this.doctorSpecialistSuccess.value = false
        this.doctorSpecialistFailed.value = false
        this.doctorSubspecialistSuccess.value = false
        this.doctorSubspecialistFailed.value = false

    }

    fun getAllDoctorType() {
        enableProgressBar.value = true
        DoctorsNetwork.getAllDoctorType(this)
    }

    fun getAllDoctorSpeciality(specialist: String?) {
        enableProgressBar.value = true
        DoctorsNetwork.getAllDoctorSpeciality(specialist, this)
    }

    fun getAllDoctorSubSpeciality(type: String, specialist: String) {
        enableProgressBar.value = true
        DoctorsNetwork.getAllDoctorSubSpeciality(type, specialist, this)
    }

    fun getSearchedDoctorbyType(searchedItemList: SearchedItemList) {
        enableProgressBar.value = true
        DoctorsNetwork.getSearchedDoctorbyType(searchedItemList, this)
    }

    fun kilometreToMile() {
        val kilometres = 1.7
        println("KM($kilometres) => Miles(%.2f)".format(kilometres * 1000 / 1609.344))
    }
}