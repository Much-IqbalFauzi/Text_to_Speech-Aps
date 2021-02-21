package com.midtest.texttospeech.controller

import com.midtest.texttospeech.httpHandler.HTTPHandler
import com.midtest.texttospeech.httpHandler.Url
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ParseInputLang() {
    var target = "en"
    init {

    }

  /*  fun getSource(bodyparse: String): String? {
        var lang:String?= null
        var url: Url = Url()
        var detect: HTTPHandler = HTTPHandler()
        var test = detect.postDetect(url.detect(),"Hello")
        if(test != null){
            try{
                var jsonObj = JSONObject(url.detect())
                var data: JSONObject = jsonObj.getJSONObject("data")
                var detections: JSONArray = data.getJSONArray("detections")
                var index: JSONArray = detections.getJSONArray(0)
                var index2: JSONObject = index.getJSONObject(0)

                var confidence:Int = index2.getInt("confidence")
                var isReliable:Boolean = index2.getBoolean("isReliable")
                lang = index2.getString("language")

            }catch (e: JSONException){
                e.printStackTrace()
            }
        }
        return lang
    }*/
}