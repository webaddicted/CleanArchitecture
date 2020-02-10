package com.webaddicted.network.utils

/**
 * Created by Deepak Sharma(webaddicted) on 09-02-2020.
 */
class ApiResponse<T>(val status: Int, code:Int,val message:String?, val data: T?){

    companion object {
        fun <T> loading(): ApiResponse<T> {
            return ApiResponse(
                ApiStatus.LOADING,
                0,
                null,
                null
            )
        }

        fun <T> success(code:Int, data: T?): ApiResponse<T> {
            return ApiResponse(
                ApiStatus.SUCCESS,
                code,
                null,
                data
            )
        }

        fun <T> error(code: Int,error: String, data: T?): ApiResponse<T> {
            return ApiResponse(
                ApiStatus.ERROR,
                code,
                error,
                null
            )
        }
    }

}