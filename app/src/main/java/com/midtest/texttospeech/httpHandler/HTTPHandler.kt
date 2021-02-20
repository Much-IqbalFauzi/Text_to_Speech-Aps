package com.midtest.texttospeech.httpHandler

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

class HTTPHandler {
    fun getlanguages(url:String)
    {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(url)
                .get()
                .addHeader("accept-encoding", "application/gzip")
                .addHeader("x-rapidapi-key", "SIGN-UP-FOR-KEY")
                .addHeader("x-rapidapi-host", "google-translate1.p.rapidapi.com")
                .build()
        val response = client.newCall(request).execute()
    }

    fun posttranslate(url: String){
        val client = OkHttpClient()

        val mediaType = MediaType.parse("application/x-www-form-urlencoded")
        val body = RequestBody.create(mediaType, "q=Hello%2C%20world!&source=en&target=es")
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

    fun postdetect(url: String){
        val client = OkHttpClient()

        val mediaType = MediaType.parse("application/x-www-form-urlencoded")
        val body = RequestBody.create(mediaType, "q=English%20is%20hard%2C%20but%20detectably%20so")
        val request = Request.Builder()
                .url("url")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("accept-encoding", "application/gzip")
                .addHeader("x-rapidapi-key", "a699bfbdd5mshc95dcc983d1f71ep1634a7jsn798b17cb476d")
                .addHeader("x-rapidapi-host", "google-translate1.p.rapidapi.com")
                .build()

        val response = client.newCall(request).execute()
    }
}