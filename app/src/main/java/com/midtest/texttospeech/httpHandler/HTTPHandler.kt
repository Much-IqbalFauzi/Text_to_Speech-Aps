package com.midtest.texttospeech.httpHandler

import com.google.gson.GsonBuilder
import com.midtest.texttospeech.model.Language
import okhttp3.*
import java.io.IOException

class HTTPHandler {
    fun getLanguages()
    {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://google-translate1.p.rapidapi.com/language/translate/v2/languages")
            .get()
            .addHeader("accept-encoding", "application/gzip")
            .addHeader("x-rapidapi-key", "296aa00a8amsh7ca281fb38b28fcp1f842ajsna0c578fe3ad5")
            .addHeader("x-rapidapi-host", "google-translate1.p.rapidapi.com")
            .build()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Fail to requeset")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                println(body)

                val gson = GsonBuilder().create()

                val lang = gson.fromJson(body, Language::class.java)
                println(lang)
                val dataList = lang.languages.data
                println(dataList)
            }
        })
    }

    fun postTranslate(url: String, bodyParse: String){
        val client = OkHttpClient()

        val mediaType = MediaType.parse("application/x-www-form-urlencoded")
        val body = RequestBody.create(mediaType, bodyParse)
//        q=Hello%2C%20world!&source=en&target=es
        val request = Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("accept-encoding", "application/gzip")
                .addHeader("x-rapidapi-key", "a699bfbdd5mshc95dcc983d1f71ep1634a7jsn798b17cb476d")
                .addHeader("x-rapidapi-host", "google-translate1.p.rapidapi.com")
                .build()

        val response = client.newCall(request).execute()
    }

    fun postDetect(url: String, bodyParse: String){
        val client = OkHttpClient()

        val mediaType = MediaType.parse("application/x-www-form-urlencoded")
        val body = RequestBody.create(mediaType, bodyParse)
//        q=English%20is%20hard%2C%20but%20detectably%20so
        val request = Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("accept-encoding", "application/gzip")
                .addHeader("x-rapidapi-key", "a699bfbdd5mshc95dcc983d1f71ep1634a7jsn798b17cb476d")
                .addHeader("x-rapidapi-host", "google-translate1.p.rapidapi.com")
                .build()

        val response = client.newCall(request).execute()
    }
}