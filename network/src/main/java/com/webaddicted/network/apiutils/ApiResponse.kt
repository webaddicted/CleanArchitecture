package com.webaddicted.network.apiutils

import java.net.SocketTimeoutException
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class ApiResponse<T>(val status: Status, val data: T?, val error: Throwable?) {

     var errorMessage: String? = null
     var errorType: Error? = null
    private var network_error = "Network Error"
    private var internal_server_error = "Internal Server Error"

    init {
        if (error == null && status == Status.ERROR) {
            errorType = Error.API_ERROR
        } else if (error is SocketTimeoutException) {
            errorType = Error.TIMEOUT_ERROR
            errorMessage = network_error
        } else {
            errorType = Error.SERVER_ERROR
            errorMessage = internal_server_error
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