package com.webaddicted.network.utils

import org.json.JSONException
import retrofit2.Response
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): ApiResponse<T> {
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
}