package com.webaddicted.network.apiutils

import com.google.gson.Gson
import java.util.*
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class ReflectionUtil(private val gson: Gson) {


    fun convertPojoToMap(model: Any): java.util.HashMap<String, String> {
        val json = gson.toJson(model)
        return gson.fromJson<java.util.HashMap<String, String>>(json, HashMap::class.java)
    }

    fun getEmptyParams(): HashMap<String, String> {
        val hmParams = HashMap<String, String>()
        return hmParams
    }
}