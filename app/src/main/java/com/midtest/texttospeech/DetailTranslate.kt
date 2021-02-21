package com.midtest.texttospeech

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.midtest.texttospeech.databaseHandler.DatabaseHandler
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

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.detail_back -> startActivity(Intent(this@DetailTranslate, MainActivity::class.java))
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