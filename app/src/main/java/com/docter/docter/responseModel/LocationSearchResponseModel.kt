package com.docter.docter.responseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class LocationSearchResponseModel(
    @Expose
    @SerializedName("data") val result: List<SearchedItemList>


)