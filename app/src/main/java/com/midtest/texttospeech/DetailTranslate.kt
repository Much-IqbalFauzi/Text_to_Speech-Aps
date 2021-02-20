package com.midtest.texttospeech

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.midtest.texttospeech.databaseHandler.DatabaseHandler

class DetailTranslate : AppCompatActivity(), View.OnClickListener {

    lateinit var databaseHandler: DatabaseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail__translate)

        val back: Button = findViewById(R.id.detail_back)
        back.setOnClickListener(this)

        databaseHandler = DatabaseHandler(this)

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

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.detail_back -> startActivity(Intent(this@DetailTranslate, MainActivity::class.java))
        }
    }
}