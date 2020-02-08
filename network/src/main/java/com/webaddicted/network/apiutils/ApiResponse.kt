package com.webaddicted.network.apiutils

import java.net.SocketTimeoutException
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class ApiResponse<T>(val status: Status, val data: T?, private val error: Throwable?) {

     var errorMessage: String? = null
     private var errorType: Error? = null
    private var networkError = "Network Error"
    private var internalServerError = "Internal Server Error"

    init {
        if (error == null && status == Status.ERROR) {
            errorType = Error.API_ERROR
        } else if (error is SocketTimeoutException) {
            errorType = Error.TIMEOUT_ERROR
            errorMessage = networkError
        } else {
            errorType = Error.SERVER_ERROR
            errorMessage = internalServerError
        }
    }

    companion object {
        fun <T> loading(): ApiResponse<T> {
            return ApiResponse(
                Status.LOADING,
                null,
                null
            )
        }

        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: Throwable?): ApiResponse<T> {
            return ApiResponse(
                Status.ERROR,
                null,
                error
            )
        }
    }

    enum class Status {
        LOADING,
        SUCCESS,
        ERROR,
    }

    enum class Error {
        SERVER_ERROR,
        TIMEOUT_ERROR,
        API_ERROR

    }
}