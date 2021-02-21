package com.midtest.texttospeech.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Detections{
    @SerializedName("language")
    @Expose
    var language:Int? = null

    @SerializedName("confidence")
    @Expose
    var confidence:Int? = null

    @SerializedName("isReliable")
    @Expose
    var isReliable:Int? = null
}