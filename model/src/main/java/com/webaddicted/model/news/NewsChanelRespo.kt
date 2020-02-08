package com.webaddicted.model.news

import com.google.gson.annotations.SerializedName
import java.io.Serializable
/**
 * Created by Deepak Sharma(webaddicted) on 15/01/20.
 */
class NewsChanelRespo : Serializable {
    @SerializedName("status")
    var status: String = ""
    @SerializedName("sources")
    var sources = ArrayList<Source>()

    open class Source : Serializable {
        @SerializedName("id")
        var id: String = ""
        @SerializedName("name")
        var name: String = ""
        @SerializedName("description")
        var description: String = ""
        @SerializedName("url")
        var url: String = ""
        @SerializedName("category")
        var category: String = ""
        @SerializedName("language")
        var language: String = ""
        @SerializedName("country")
        var country: String = ""
    }
}