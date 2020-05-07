package com.docter.docter.responseModel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StateWiseBase(
    @Json(name = "States_daily")
    val States_daily: List<statesDaily>
)