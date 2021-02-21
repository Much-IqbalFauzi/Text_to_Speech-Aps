package com.midtest.texttospeech.httpHandler

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.UiThread
import com.google.gson.GsonBuilder
import com.midtest.texttospeech.R
import com.midtest.texttospeech.controller.LanguageController
import com.midtest.texttospeech.model.Language
import okhttp3.*
import java.io.IOException

class HTTPHandler {
    fun getLanguages(context: Context): Unit{
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://google-translate1.p.rapidapi.com/language/translate/v2/languages")
            .get()
            .addHeader("accept-encoding", "application/gzip")
            .addHeader("x-rapidapi-key", "296aa00a8amsh7ca281fb38b28fcp1f842ajsna0c578fe3ad5")
            .addHeader("x-rapidapi-host", "google-translate1.p.rapidapi.com")
            .build()
        val all = client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Fail to requeset")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                println(body)

                val gson = GsonBuilder().create()
                val data = gson.fromJson(body, Language::class.java)
                println("==============================================================="+data.data.languages[1].language.toString())
                val langController: LanguageController = LanguageController()
                for (i in data.data.languages) {
                    langController.addLang(i.language.toString())
                    println(i.language.toString())
                }



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

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Fauilllll")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                println("======================================="+body)
            }

        })
    }

    fun yay() {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://translated-mymemory---translation-memory.p.rapidapi.com/api/get?langpair=en%7Cit&q=Hello%20World!&mt=1&onlyprivate=0&de=a%40b.c")
            .get()
            .addHeader("x-rapidapi-key", "59a25d5c87mshe5c5a3698d75ab3p19d513jsnfc4f0c3af1dd")
            .addHeader("x-rapidapi-host", "translated-mymemory---translation-memory.p.rapidapi.com")
            .build()
        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Fauilllll")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                println(body)
            }

        })
    }

    fun yoks() {
        val client = OkHttpClient()

        val mediaType = MediaType.parse("application/x-www-form-urlencoded")
        val body = RequestBody.create(mediaType, "q=kawaii%20imouto")
        val request = Request.Builder()
            .url("https://google-translate1.p.rapidapi.com/language/translate/v2/detect")
            .post(body)
            .addHeader("content-type", "application/x-www-form-urlencoded")
            .addHeader("x-rapidapi-key", "59a25d5c87mshe5c5a3698d75ab3p19d513jsnfc4f0c3af1dd")
            .addHeader("x-rapidapi-host", "google-translate1.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Fauilllll")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                println("======================================="+body)
            }

        })
    }

    /*fun postDetect(url: String, bodyParse: String){
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
    }*/
}