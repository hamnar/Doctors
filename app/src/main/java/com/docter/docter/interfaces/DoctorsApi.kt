package com.docter.docter.interfaces

import com.docter.docter.responseModel.*
import retrofit2.Response
import retrofit2.http.*

interface DoctorsApi {

    /*LOCATION SEARCH*/
    @Headers("Accept: application/json")
    @GET("search/city/{name}")
    suspend fun getSearchedLocation(@Path("name") searchedtxt: String): (Response<LocationSearchResponseModel>?)

    /*GET ALL DOCTOR TYPES*/
    @Headers("Accept: application/json")
    @GET("search/types")
    suspend fun getAllDoctorType(): (Response<DoctorTypeResponseModel>?)

    /*GET ALL DOCTOR SPECIALITIES*/
    @Headers("Accept: application/json")
    @GET("search/speciality/{types}")
    suspend fun getAllDoctorSpeciality(@Path("types") doctortype: String?): (Response<DoctorTypeResponseModel>?)


    /*   GET ALL DOCTOR SUB SPECIALITIES*/
    @Headers("Accept: application/json")
    @GET("search/subspeciality/{types}/{speciality}")
    suspend fun getAllDoctorSubSpeciality(@Path("types") doctortype: String?, @Path("speciality") speciality: String?): (Response<DoctorTypeResponseModel>?)

    /*    GET RESULTS
        SEARCH BY DOCTOR TYPE*/
    @Headers("Accept: application/json")
    @POST("search/")
    suspend fun getSearchByDoctorType(@Body searchedItemList: SearchedItemList): (Response<SearchByDoctorNameResponseModel>?)

    @Headers("Accept: application/json")
    @POST("searchbyname/")
    suspend fun getSearchByDoctorName(@Body searchedItemList: SearchedItemList): (Response<SearchByDoctorNameResponseModel>?)

}