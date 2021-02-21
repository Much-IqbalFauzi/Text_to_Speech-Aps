package com.midtest.texttospeech

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.GsonBuilder
import com.midtest.texttospeech.databaseHandler.DatabaseHandler
import com.midtest.texttospeech.itemAdapter.HistoryItemAdapter
import com.midtest.texttospeech.model.History
import com.midtest.texttospeech.model.Language
import okhttp3.*
import java.io.IOException

class MainActivity : Activity(), View.OnClickListener, AdapterView.OnItemLongClickListener {

    lateinit var dialog: Dialog

    lateinit var dataBaseHandle: DatabaseHandler

    lateinit var dataSelected: History

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataBaseHandle = DatabaseHandler(this)

        val translate: Button = findViewById(R.id.main_translate_action)
        translate.setOnClickListener(this)

        val swap: FloatingActionButton = findViewById(R.id.main_swap_lang)
        swap.setOnClickListener(this)

        val gridHistory: GridView = findViewById(R.id.main_history)
        val historyAdapter = HistoryItemAdapter(this)
        gridHistory.adapter = historyAdapter

        getLanguages()

        val gridView: GridView = findViewById(R.id.main_history)
        gridView.setOnItemLongClickListener(this)

        dialog = Dialog(this)

    }

    override fun onItemLongClick(parent: AdapterView<*>?, p1: View?, index: Int, id: Long): Boolean {
        dataSelected = parent?.getItemAtPosition(index) as History
        dialog.setContentView(R.layout.dialog_trash)
        dialog.window!!.setBackgroundDrawable(getDrawable(R.drawable.dialong_bg))
        dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val dialogCancel: Button = dialog.findViewById(R.id.main_cancel_delete)
        val dialogDeleteData: Button = dialog.findViewById(R.id.main_delete_data)
        dialogCancel.setOnClickListener(this)
        dialogDeleteData.setOnClickListener(this)
        dialog.show()

        return true
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.main_translate_action -> {
                val intent: Intent = Intent(this@MainActivity, DetailTranslate::class.java)
                val spinnerFrom = GetSpinnerFrom()
                val spinnerTarget = GetSpinnerTarget()
                val text: EditText = findViewById(R.id.main_input)
                intent.putExtra("spinnerFrom", spinnerFrom)
                intent.putExtra("spinnerTarget", spinnerTarget)
                intent.putExtra("teks", text.text.toString())
                startActivity(intent)
            }
            R.id.main_swap_lang -> {
                val spinerFrom: Spinner = findViewById(R.id.main_spinner_langfrom)
                val spinnerTarget: Spinner = findViewById(R.id.main_spinner_langto)

                val index = spinnerTarget.selectedItemPosition
                spinnerTarget.setSelection(spinerFrom.selectedItemPosition-1)
                spinerFrom.setSelection(index+1)
            }
            R.id.main_cancel_delete -> dialog.dismiss()
            R.id.main_delete_data -> {
                dataBaseHandle.delete(dataSelected.id)
                dialog.dismiss()
                startActivity(Intent(this@MainActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    fun SpinnerValue(lang: Language): Unit {
        val spinner: Spinner = findViewById(R.id.main_spinner_langfrom)
        val spinnerTarget: Spinner = findViewById(R.id.main_spinner_langto)
        val spinnerValue = mutableListOf("Erabe!")
        var spinnerTargetValue = ArrayList<String>()
        for(data in lang.data.languages) {
            spinnerValue.add(data.language.toString())
        }
        for(data in lang.data.languages) {
            spinnerTargetValue.add(data.language.toString())
        }
        val adapter1 = ArrayAdapter(this, R.layout.spinner_item, spinnerTargetValue)
        val adapter2 = ArrayAdapter(this, R.layout.spinner_item, spinnerTargetValue)
        spinner.adapter = adapter1
        spinnerTarget.adapter = adapter2

    }

    fun GetSpinnerFrom(): String {
        val spinner: Spinner = findViewById(R.id.main_spinner_langfrom)
        return spinner.getItemAtPosition(spinner.selectedItemPosition).toString()
    }

    fun GetSpinnerTarget(): String {
        val spinner: Spinner = findViewById(R.id.main_spinner_langto)
        return spinner.getItemAtPosition(spinner.selectedItemPosition).toString()
    }

    fun getLanguages(): Unit{
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

                val gson = GsonBuilder().create()
                val data = gson.fromJson(body, Language::class.java)

                this@MainActivity.runOnUiThread {
                    SpinnerValue(data)
                }
            }
        })
    }



}


