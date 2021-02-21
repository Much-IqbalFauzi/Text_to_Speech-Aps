package com.midtest.texttospeech.itemAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.midtest.texttospeech.R
import com.midtest.texttospeech.databaseHandler.DatabaseHandler
import com.midtest.texttospeech.model.History

class HistoryItemAdapter(context: Context): BaseAdapter() {

    var allData: ArrayList<History> = ArrayList<History>()
    var konteks: Context
    init {
        val databaseHandler: DatabaseHandler = DatabaseHandler(context)
        allData = databaseHandler.getAll()
        konteks = context
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        convertView = LayoutInflater.from(konteks).inflate(R.layout.history_item, parent, false)

        val fromLang: TextView = convertView.findViewById(R.id.history_item_from)
        val targetLang: TextView = convertView.findViewById(R.id.history_item_result)

        fromLang.text = allData.get(position).bahasa+": "+allData.get(position).kalimat
        targetLang.text = allData.get(position).bahasatujuan+": "+allData.get(position).kalimathasil


        return convertView
    }

    override fun getItem(position: Int): Any {
        return allData.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return allData.size
    }

}