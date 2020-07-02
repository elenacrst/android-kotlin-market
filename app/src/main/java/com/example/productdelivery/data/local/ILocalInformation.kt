package com.example.productdelivery.data.local

interface ILocalInformation {

    fun saveLanguage(language: String)
     fun getLanguage(): String
     fun clearSharedPrefs()

}