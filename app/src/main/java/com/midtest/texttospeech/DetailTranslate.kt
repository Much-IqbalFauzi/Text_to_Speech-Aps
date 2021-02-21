package com.midtest.texttospeech

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.gson.GsonBuilder
import com.midtest.texttospeech.databaseHandler.DatabaseHandler
import com.midtest.texttospeech.model.Translated
import okhttp3.*
import java.io.IOException
import java.util.*

class DetailTranslate : Activity(), View.OnClickListener, TextToSpeech.OnInitListener {

    lateinit var databaseHandler: DatabaseHandler
    private lateinit var tts: TextToSpeech
    private lateinit var buttonFrom: Button
    private lateinit var buttonTarget: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail__translate)

        tts = TextToSpeech(this, this)
        buttonFrom = findViewById(R.id.detail_from_speak)
        buttonTarget = findViewById(R.id.detail_target_speak)

        buttonFrom.setOnClickListener(this)
        buttonTarget.setOnClickListener(this)

        val back: Button = findViewById(R.id.detail_back)
        back.setOnClickListener(this)

        databaseHandler = DatabaseHandler(this)

        val langFrom = intent.getStringExtra("spinnerFrom")
        val langTarget = intent.getStringExtra("spinnerTarget")
        val text = intent.getStringExtra("teks")

        val viewFrom: TextView = findViewById(R.id.detail_from_lang)
        val viewTarget: TextView = findViewById(R.id.detail_target_lang)
        val viewTextFrom: TextView = findViewById(R.id.detail_from_text)

        viewFrom.text = langFrom
        viewTarget.text = langTarget
        viewTextFrom.text = text

        val arre = text?.split(" ")
        if(arre?.size!! > 1){
            var all: String = ""
            for(i in 0..arre?.size-1) {
                if (i < arre?.size-1) {
                    all += arre[i]+"%20"
                } else {
                    all += arre[i]
                }
            }
            posttranslate(langFrom.toString(), langTarget.toString(), all)
        } else {
            posttranslate(langFrom.toString(), langTarget.toString(), text.toString())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        autoSave()
        startActivity(Intent(this@DetailTranslate, MainActivity::class.java))
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.detail_back -> {
                autoSave()
                startActivity(Intent(this@DetailTranslate, MainActivity::class.java))
            }
            R.id.detail_from_speak -> {
                val text: TextView = findViewById(R.id.detail_from_text)
                tts.speak(text.text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
            }
            R.id.detail_target_speak -> {
                val text: TextView = findViewById(R.id.detail_target_text)
                tts.speak(text.text.toString(), TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            val res = tts.setLanguage(Locale.JAPANESE)
            if(res != TextToSpeech.LANG_MISSING_DATA || res != TextToSpeech.LANG_NOT_SUPPORTED) {
                buttonFrom.isEnabled = true
                buttonTarget.isEnabled = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(tts != null) {
            tts.stop()
            tts.shutdown()
        }
    }

    fun setResult(trans: String) {
        val viewTextTarget: TextView = findViewById(R.id.detail_target_text)
        viewTextTarget.text = trans
    }

    fun posttranslate(source: String, target: String, teks: String) {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://translated-mymemory---translation-memory.p.rapidapi.com/api/get?langpair=$source%7C$target&q=$teks")
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

                val gson = GsonBuilder().create()
                val res = gson.fromJson(body, Translated::class.java)

                this@DetailTranslate.runOnUiThread {
                    setResult(res.responseData.translatedText)

                }

            }

        })
    }

    fun autoSave() {
        val fromLang: TextView = findViewById(R.id.detail_from_lang)
        val targetLang: TextView = findViewById(R.id.detail_target_lang)
        val fromText: TextView = findViewById(R.id.detail_from_text)
        val targetText: TextView = findViewById(R.id.detail_target_text)
        databaseHandler.addData(
                bahasa=fromLang.text.toString(), kalimat=fromText.text.toString(),
                bahasatujuan=targetLang.text.toString(), kalimathasil=targetText.text.toString())
    }
}