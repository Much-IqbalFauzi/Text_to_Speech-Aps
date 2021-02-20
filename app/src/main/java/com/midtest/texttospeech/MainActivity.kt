package com.midtest.texttospeech

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridView
import com.midtest.texttospeech.httpHandler.HTTPHandler
import com.midtest.texttospeech.httpHandler.Url
import com.midtest.texttospeech.itemAdapter.HistoryItemAdapter

class MainActivity : Activity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val translate: Button = findViewById(R.id.main_translate_action)
        translate.setOnClickListener(this)

        val gridHistory: GridView = findViewById(R.id.main_history)
        val historyAdapter = HistoryItemAdapter(this)
        gridHistory.adapter = historyAdapter

        val httpHandler = HTTPHandler()
        httpHandler.getLanguages()

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.main_translate_action -> startActivity(Intent(this@MainActivity, DetailTranslate::class.java))
        }
    }
}