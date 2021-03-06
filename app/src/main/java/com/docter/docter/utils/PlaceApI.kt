package com.docter.docter.utils

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.annotation.Nullable
import com.docter.docter.R
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URLEncoder

class PlaceAPI private constructor(
    var apiKey: String?, var sessionToken: String?, var appContext: Context
) {
    /**
     * Used to get details for the places api to be showed in the auto complete list
     */

    internal fun autocomplete(input: String): ArrayList<Place>? {
        checkInitialization()
        val resultList: ArrayList<Place>? = null
        var conn: HttpURLConnection? = null
        val jsonResults = StringBuilder()
        try {
            val sb = buildApiUrl(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON)
            sb.append("&input=" + URLEncoder.encode(input, "utf8"))
            val url = java.net.URL(sb.toString())
            conn = url.openConnection() as HttpURLConnection
            val inputStreamReader = InputStreamReader(conn.inputStream)
            constructData(inputStreamReader, jsonResults)
        } catch (e: Exception) {
            when (e) {
                is MalformedURLException -> logError(e, R.string.error_processing_places_api)
                is IOException -> logError(e, R.string.error_connecting_to_places_api)
            }
            return resultList
        } finally {
            conn?.disconnect()
        }
        return parseAutoCompleteData(jsonResults)
    }

    private fun buildApiUrl(apiUrl: String): StringBuilder {
        val sb = StringBuilder(apiUrl)
        sb.append("&components=country:us")
        if (!TextUtils.isEmpty(sessionToken)) {
            sb.append("&sessiontoken=$sessionToken")
        }
        return sb
    }

    /**
     * Fetches the details of the place
     */
    @Nullable
    fun fetchPlaceDetails(placeId: String, listener: OnPlacesDetailsListener) {
        checkInitialization()
        Thread(Runnable {
            var conn: HttpURLConnection? = null
            val jsonResults = StringBuilder()
            try {
                val sb = buildApiUrl(PLACES_API_BASE + OUT_JSON)
                conn = java.net.URL(sb.toString()).openConnection() as? HttpURLConnection
                val inputStreamReader = InputStreamReader(conn?.inputStream)
                constructData(inputStreamReader, jsonResults)
                parseDetailsData(jsonResults, listener)
            } catch (e: Exception) {
                when (e) {
                    is JSONException -> parseDetailsError(jsonResults, listener, e)
                    is MalformedURLException -> showDetailsError(
                        R.string.error_processing_places_api, listener, e
                    )
                    is IOException -> showDetailsError(
                        R.string.error_connecting_to_places_api,
                        listener,
                        e
                    )
                }
            } finally {
                conn?.disconnect()
            }
        }).start()
    }


    private fun checkInitialization() {
        if (TextUtils.isEmpty(apiKey)) {
            throw InitializationException(appContext.getString(R.string.error_lib_not_initialized))
        }
    }


    private fun logError(e: Exception, resource: Int) {
        Log.e(TAG, appContext.getString(resource), e)
    }

    private fun parseAutoCompleteData(jsonResults: StringBuilder): ArrayList<Place>? {
        var resultList: ArrayList<Place>? = ArrayList()
        try {
            val jsonObj = JSONObject(jsonResults.toString())
            val predsJsonArray = jsonObj.getJSONArray("predictions")
            resultList = ArrayList(predsJsonArray.length())
            for (i in 0 until predsJsonArray.length()) {
                resultList.add(
                    Place(
                        predsJsonArray.getJSONObject(i).getString("place_id"),
                        predsJsonArray.getJSONObject(i).getString("description")
                    )
                )
            }
            val limitedList = resultList.take(10)
            return limitedList as ArrayList<Place>
        } catch (e: JSONException) {
            val errorJson = JSONObject(jsonResults.toString())
            when {
                errorJson.has(ERROR_MESSAGE) -> Log.e(TAG, errorJson.getString(ERROR_MESSAGE))
                else -> Log.e(
                    TAG,
                    appContext.getString(R.string.error_cannot_process_json_results),
                    e
                )
            }
            return resultList
        }
    }

    private fun constructData(inputStreamReader: InputStreamReader, jsonResults: StringBuilder) {
        var read: Int
        val buff = CharArray(1024)
        loop@ do {
            read = inputStreamReader.read(buff)
            when {
                read != -1 -> jsonResults.append(buff, 0, read)
                else -> break@loop
            }
        } while (true)
    }

    private fun showDetailsError(resource: Int, listener: OnPlacesDetailsListener, e: Exception) {
        logError(e, resource)
        appContext.getString(resource).let { listener.onError(it) }
    }

    private fun parseDetailsError(
        jsonResults: StringBuilder, listener: OnPlacesDetailsListener, e: Exception
    ) {
        val errorJson = JSONObject(jsonResults.toString())
        if (errorJson.has(ERROR_MESSAGE)) {
            Log.e(TAG, errorJson.getString(ERROR_MESSAGE), e)
            listener.onError(errorJson.getString(ERROR_MESSAGE))
        } else {
            Log.e(TAG, appContext.getString(R.string.error_cannot_process_json_results), e)
            appContext.getString(R.string.error_cannot_process_json_results)
                .let { listener.onError(it) }
        }
    }

    private fun parseDetailsData(jsonResults: StringBuilder, listener: OnPlacesDetailsListener) {
        val jsonObj = JSONObject(jsonResults.toString())
        val resultJsonObject = jsonObj.getJSONObject(RESULT)
//        listener.onPlaceDetailsFetched(
//            PlaceDetails(
//                resultJsonObject.getString(ID), resultJsonObject.getString(NAME), address, lat, lng, placeId, vicinity
//            )
//        )
    }


    companion object {

        private val TAG = PlaceAPI::class.java.simpleName
        private const val PLACES_API_BASE = "https://www.qa.findadoctor.com/api/search/city/"
        private const val TYPE_AUTOCOMPLETE = "/autocomplete"

        private const val OUT_JSON = "/json"
        private const val RESULT = "result"
        private const val ERROR_MESSAGE = "error_message"
    }

    /**
     * The data class used as builder to allow the user to use different configs of the places API
     */
    data class Builder(
        private var apiKey: String? = null,
        private var sessionToken: String? = null
    ) {
        /**
         * Sets the api key for the PlaceAPI
         */
        fun apiKey(apiKey: String) = apply { this.apiKey = apiKey }

        /**
         * Sets a unique session token for billing in the PlaceAPI
         */
        fun sessionToken(sessionToken: String) = apply { this.sessionToken = sessionToken }

        /**
         * Builds and creates an object of the PlaceAPI
         */
        fun build(context: Context) = PlaceAPI(apiKey, sessionToken, context)
    }
}
