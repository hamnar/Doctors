package com.docter.docter.responseModel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class statesDaily(
    @Json(name = "an") val an: Int,
    @Json(name = "ap") val ap: Int,
    @Json(name = "ar") val ar: Int,
    @Json(name = "as") val ass: Int,
    @Json(name = "br") val br: Int,
    @Json(name = "ch") val ch: Int,
    @Json(name = "ct") val ct: Int,
    @Json(name = "date") val date: String,
    @Json(name = "dd") val dd: Int,
    @Json(name = "d1") val d1: Int,
    @Json(name = "dn") val dn: Int,
    @Json(name = "ga") val ga: Int,
    @Json(name = "gj") val gj: Int,
    @Json(name = "hp") val hp: Int,
    @Json(name = "hr") val hr: Int,
    @Json(name = "jh") val jh: Int,
    @Json(name = "jk") val jk: Int,
    @Json(name = "ka") val ka: Int,
    @Json(name = "kl") val kl: Int,
    @Json(name = "la") val la: Int,
    @Json(name = "ld") val ld: Int,
    @Json(name = "mh") val mh: Int,
    @Json(name = "ml") val ml: Int,
    @Json(name = "mn") val mn: Int,
    @Json(name = "mp") val mp: Int,
    @Json(name = "mz") val mz: Int,
    @Json(name = "nl") val nl: Int,
    @Json(name = "or") val or: Int,
    @Json(name = "pb") val pb: Int,
    @Json(name = "py") val py: Int,
    @Json(name = "rj") val rj: Int,
    @Json(name = "sk") val sk: Int,
    @Json(name = "status") val status: String,
    @Json(name = "tg") val tg: Int,
    @Json(name = "tn") val tn: Int,
    @Json(name = "tr") val tr: Int,
    @Json(name = "tt") val tt: Int,
    @Json(name = "up") val up: Int,
    @Json(name = "ut") val ut: Int,
    @Json(name = "wb") val wb: Int
)

