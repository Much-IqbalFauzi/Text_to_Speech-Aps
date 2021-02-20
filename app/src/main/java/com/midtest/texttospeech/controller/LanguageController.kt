package com.midtest.texttospeech.controller

class LanguageController {
    var allData: ArrayList<String> = ArrayList<String>()

    fun getAll(): ArrayList<String> {
        return allData
    }

    fun addLang(lang: String): Unit {
        allData.add(lang)
    }
}