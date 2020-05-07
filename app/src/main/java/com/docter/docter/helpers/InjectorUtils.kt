/*
 * Created by M1028379 on 18/12/2019
 * Copyright (c) CARTUS B2B 2019 .
 * All rights reserved.
 * Last modified 12/17/19 3:05 PM
 *
 * CHANGE LOG
 * —------------------------------------------------------
 * ID     Bug ID   Author Name     Date         Description
 * —------------------------------------------------------
 */

package com.docter.docter.helpers

import android.os.Looper
import android.os.Message
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

object InjectorUtils {
    private var retrofit: Retrofit? = null
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                val client = OkHttpClient().newBuilder().addInterceptor(object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        try {
                            val request = chain.request()
                                .newBuilder()
                                .addHeader("Client-Id", "")
                                .addHeader("Client-Secret", "")
                                .addHeader("Content-Type", "application/json")
                                .build()
                            return chain.proceed(request)
                        } catch (exception: Exception) {
                            val handler =
                                object : android.os.Handler(Looper.getMainLooper()) {
                                    override fun handleMessage(msg: Message?) {
                                        super.handleMessage(msg)

                                    }
                                }
                            when (exception) {
                                is SocketTimeoutException -> {
                                    val message = handler.obtainMessage()
                                    message.sendToTarget()
                                }
                                is SocketException -> {
                                    val message = handler.obtainMessage()
                                    message.sendToTarget()
                                }
                                is IOException -> {
                                    val message = handler.obtainMessage()
                                    message.sendToTarget()
                                }
                                else -> exception.stackTrace
                            }
                            return onOnIntercept(chain)
                        }

                    }
                }).connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()


                retrofit = Retrofit.Builder()
                    .baseUrl("")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            return retrofit

        }

    @Throws(IOException::class)
    private fun onOnIntercept(chain: Interceptor.Chain): Response {
        try {
            val response = chain.proceed(chain.request())
            val content = response.body().toString()
            return response.newBuilder()
                .body(ResponseBody.create(response.body()!!.contentType(), content)).build()
        } catch (exception: SocketTimeoutException) {
            exception.printStackTrace()
        }

        return chain.proceed(chain.request())
    }

}