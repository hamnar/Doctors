package com.docter.docter.helpers

import android.os.Looper
import android.os.Message
import android.widget.Toast
import com.docter.docter.main.MainApplication
import com.docter.docter.R
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

object DoctersInjectorUtils {
    private var retrofit: Retrofit? = null

    val handler =
        object : android.os.Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Toast.makeText(
                    MainApplication.appContext,
                    MainApplication.appContext.getString(
                        R.string.request_time_out
                    ),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    // core will be shared across both clients
    private val baseOkHttpClient = OkHttpClient()

    // customize client for first Retrofit instance for Client Id and Secret
    private val okHttpClient: OkHttpClient = baseOkHttpClient
        .newBuilder()
        .followRedirects(false)
        .build()

    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                val client = okHttpClient.newBuilder()
                    .addInterceptor(object : Interceptor {
                        override fun intercept(chain: Interceptor.Chain): Response {
                            try {
                                val request = chain.request()
                                    .newBuilder()
                                    .addHeader("Content-Type", "application/json")
                                    .build()
                                return chain.proceed(request)
                            } catch (exception: Exception) {
                                when (exception) {
                                    is SocketTimeoutException -> {
                                        handler.obtainMessage()
                                    }
                                    is SocketException -> {
                                        handler.obtainMessage()
                                    }
                                    is UnknownHostException -> {
                                        handler.obtainMessage()
                                    }
                                    is IOException -> {
                                        handler.obtainMessage()
                                    }
                                    else -> exception.stackTrace
                                }
                                return onOnIntercept(chain)
                            }

                        }

                    }).connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build()


                retrofit = Retrofit.Builder()
                    .baseUrl("https://www.qa.findadoctor.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
            }
            return retrofit

        }


    @Throws(IOException::class)
    private fun onOnIntercept(chain: Interceptor.Chain): Response {
        val response: Response?
        try {
            response = chain.proceed(chain.request())
            val content = response.body().toString()
            return response.newBuilder()
                .body(ResponseBody.create(response.body()!!.contentType(), content)).build()
        } catch (exception: Exception) {
            exception.printStackTrace()
            return Response.Builder()
                .code(700) //Whatever code
                .protocol(Protocol.HTTP_2)
                .message("Dummy response")
                .body(ResponseBody.create(MediaType.parse("application/json"), "{}"))
                .request(chain.request())
                .build()

        }
    }
}