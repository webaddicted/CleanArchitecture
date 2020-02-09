package com.webaddicted.network.utils

import org.json.JSONException
import retrofit2.Response
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): ApiResponse<T> {
        val response = call.invoke()
        if (response.isSuccessful) return ApiResponse(ApiStatus.SUCCESS,
            response.message(),
            response.body()!!,
            response.code()
        ) else {
            val error = response.errorBody()?.toString()
            return try {
                error?.let {
                    ApiResponse(
                        ApiStatus.ERROR,
                        it,
                        response.body()!!,
                        response.code()
                    )
                }!!
            } catch (e: JSONException) {
                ApiResponse(ApiStatus.ERROR, e.toString(), response.body()!!, response.code())
            }
        }
    }
}