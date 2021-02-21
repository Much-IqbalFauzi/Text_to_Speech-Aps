package com.midtest.texttospeech.controller

class LanguageController {
    var allData = mutableListOf("Choose")

    fun getAll(): List<String> {
        return allData
    }

    fun addLang(lang: String): Unit {
        allData.add(lang)
    }
}