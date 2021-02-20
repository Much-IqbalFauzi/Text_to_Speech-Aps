package com.midtest.texttospeech

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : Activity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val translate: Button = findViewById(R.id.main_translate_action)
        translate.setOnClickListener(this)

        
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.main_translate_action -> startActivity(Intent(this@MainActivity, DetailTranslate::class.java))
        }
    }
}