package com.docter.docter.network

import android.util.Log
import com.docter.docter.HomeView
import com.docter.docter.configuration.AppConfig
import com.docter.docter.helpers.CovidInjectorUtils
import com.docter.docter.helpers.DoctersInjectorUtils
import com.docter.docter.interfaces.DoctorsApi
import com.docter.docter.lisener.*
import com.docter.docter.responseModel.LocationSearchResponseModel
import com.docter.docter.responseModel.SearchedItemList
import com.docter.docter.ui.splash.SearchDoctor
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

object DoctorsNetwork {
    private lateinit var searchedLocation: LocationSearchResponseModel
    //Function to call Incoming Sale Api
    fun getSearchedLocation1(typedtxt: String): LocationSearchResponseModel? {
        val api =
            DoctersInjectorUtils.retrofitInstance?.create(DoctorsApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = api?.getSearchedLocation(typedtxt)
            withContext(Dispatchers.Main) {
                try {
                    when (response?.code()) {
                        AppConfig.NETWORK_SUCCESS -> {
                            Log.d(
                                HomeView::class.java.name,
                                "getSearchedLocation Success" + response.body()
                            )
                            searchedLocation = response.body()!!
                        }
                        else -> {
                            searchedLocation = response?.body()!!
                        }
                    }

                } catch (e: HttpException) {
                    Log.d(
                        HomeView::class.java.name,
                        "Location Search HttpException"
                    )
                }
            }
        }
        return searchedLocation
    }

    //Function to call Incoming Sale Api
    fun getSearchedLocation(typedtxt: String, locationSearchListener: LocationSearchListener) {
        val api =
            DoctersInjectorUtils.retrofitInstance?.create(DoctorsApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = api?.getSearchedLocation(typedtxt)
            withContext(Dispatchers.Main) {
                try {
                    when (response?.code()) {
                        AppConfig.NETWORK_SUCCESS -> {
                            Log.d(
                                HomeView::class.java.name,
                                "getSearchedLocation Success" + response.body()
                            )
                            locationSearchListener.onLocationSearchSuccess(response.body())

                        }
                        else -> {
                            locationSearchListener.onLocationSearchFailure()
                        }
                    }

                } catch (e: HttpException) {
                    Log.d(
                        HomeView::class.java.name,
                        "Location Search HttpException"
                    )
                }
            }
        }
    }


    //Function to call getAllDoctorType Api
    fun getAllDoctorType(doctorTypeListener: DoctorTypeListener) {
        val api =
            DoctersInjectorUtils.retrofitInstance?.create(DoctorsApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = api?.getAllDoctorType()
            withContext(Dispatchers.Main) {
                try {
                    when (response?.code()) {
                        AppConfig.NETWORK_SUCCESS -> {
                            Log.d(
                                HomeView::class.java.name,
                                "getAllDoctorType Success" + response.body()
                            )

                            doctorTypeListener.onDoctorTypeSuccess(response.body())

                        }
                        else -> {
                            doctorTypeListener.onDoctorTypeFailure()
                        }
                    }

                } catch (e: HttpException) {
                    Log.d(
                        SearchDoctor::class.java.name,
                        "Doctortype  HttpException"
                    )
                }
            }
        }
    }

    //Function to call getAllDoctorSpeciality Api
    fun getAllDoctorSpeciality(type: String?, doctorSpecialistListener: DoctorSpecialistListener) {
        val api =
            DoctersInjectorUtils.retrofitInstance?.create(DoctorsApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = api?.getAllDoctorSpeciality(type)
            withContext(Dispatchers.Main) {
                try {
                    when (response?.code()) {
                        AppConfig.NETWORK_SUCCESS -> {

                            doctorSpecialistListener.onDoctorSpecialistSuccess(response.body())

                        }
                        else -> {
                            doctorSpecialistListener.onDoctorSpecialistFailure()
                        }
                    }

                } catch (e: HttpException) {
                    Log.d(
                        SearchDoctor::class.java.name,
                        "getAllDoctorSpeciality HttpException"
                    )
                }
            }
        }
    }

    //Function to call getAllDoctorSubSpeciality Api
    fun getAllDoctorSubSpeciality(
        type: String,
        speciality: String,
        doctorSubSpecialistListener: DoctorSubSpecialistListener
    ) {
        val api =
            DoctersInjectorUtils.retrofitInstance?.create(DoctorsApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = api?.getAllDoctorSubSpeciality(type, speciality)
            withContext(Dispatchers.Main) {
                try {
                    when (response?.code()) {
                        AppConfig.NETWORK_SUCCESS -> {

                            doctorSubSpecialistListener.onDoctorSubSpecialistSuccess(response.body())

                        }
                        else -> {
                            doctorSubSpecialistListener.onDoctorSubSpecialistFailure()
                        }
                    }

                } catch (e: HttpException) {
                    Log.d(
                        SearchDoctor::class.java.name,
                        "getAllDoctorSubSpeciality HttpException"
                    )
                }
            }
        }
    }

    //Function to call getSearchedDoctorbyName Api
    fun getSearchedDoctorbyName(
        searchedItemList: SearchedItemList,
        seacrhDoctorByNameListener: SeacrhDoctorByNameListener
    ) {
        val gson = Gson()
        val jsonString = gson.toJson(searchedItemList)
        Log.e("updaterequestdoctorname", jsonString)
        val api =
            DoctersInjectorUtils.retrofitInstance?.create(DoctorsApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = api?.getSearchByDoctorName(searchedItemList)
            withContext(Dispatchers.Main) {
                try {
                    when (response?.code()) {
                        AppConfig.NETWORK_SUCCESS -> {

                            seacrhDoctorByNameListener.onSeacrhDoctorByNameSuccess(response.body())

                        }
                        else -> {
                            seacrhDoctorByNameListener.onSeacrhDoctorByNameFailure()
                        }
                    }

                } catch (e: HttpException) {
                    Log.d(
                        SearchDoctor::class.java.name,
                        "getSearchedDoctorbyName HttpException"
                    )
                }
            }
        }
    }

    //Function to call getSearchedDoctorbyName Api
    fun getSearchedDoctorbyType(
        searchedItemList: SearchedItemList,
        seacrhDoctorByNameListener: SeacrhDoctorByNameListener
    ) {
        val gson = Gson()
        val jsonString = gson.toJson(searchedItemList)
        Log.e("reqSearchedDoctorbyType", jsonString)
        val api =
            DoctersInjectorUtils.retrofitInstance?.create(DoctorsApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = api?.getSearchByDoctorType(searchedItemList)
            withContext(Dispatchers.Main) {
                try {
                    when (response?.code()) {
                        AppConfig.NETWORK_SUCCESS -> {

                            seacrhDoctorByNameListener.onSeacrhDoctorByNameSuccess(response.body())

                        }
                        else -> {
                            seacrhDoctorByNameListener.onSeacrhDoctorByNameFailure()
                        }
                    }

                } catch (e: HttpException) {
                    Log.d(
                        SearchDoctor::class.java.name,
                        "getSearchedDoctorbyType HttpException"
                    )
                }
            }
        }
    }



}