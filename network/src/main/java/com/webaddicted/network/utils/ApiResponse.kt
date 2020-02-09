package com.webaddicted.network.utils

/**
 * Created by Deepak Sharma(webaddicted) on 09-02-2020.
 */
class ApiResponse<T>(val status: Int, val message:String, val data: T, val code:Int)