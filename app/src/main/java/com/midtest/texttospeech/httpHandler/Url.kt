package com.midtest.texttospeech.httpHandler

class Url{
    fun lang(): String {
        return "https://google-translate1.p.rapidapi.com/language/translate/v2/languages"
    }

    fun detect(): String{
         return "https://google-translate1.p.rapidapi.com/language/translate/v2/detect"
    }

    fun trans(): String {
        return "https://google-translate1.p.rapidapi.com/language/translate/v2"
    }
}