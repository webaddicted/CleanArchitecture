package com.webaddicted.network.utils

import org.json.JSONException
import retrofit2.Response
import java.io.IOException

/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): ApiResponse<T> {
        if (!isNetworkAvailable1())return ApiResponse.error(111,"internet not available", null)
        val response = call.invoke()
        if (response.isSuccessful) return ApiResponse.success(
            response.code(),
            response.body()
        ) else {
            val error = response.errorBody()?.toString()
            return try {
                error?.let {
                    ApiResponse.error(
                        response.code(),
                        it,
                        response.body()
                    )
                }!!
            } catch (e: JSONException) {
                ApiResponse.error(response.code(),e.toString(), response.body())
            }
        }
    }
    fun isNetworkAvailable1(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return false
    }
}