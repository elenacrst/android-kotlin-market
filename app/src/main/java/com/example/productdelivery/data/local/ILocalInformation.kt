package com.example.productdelivery.data.local

import com.example.productdelivery.DeliveryApplication
import com.example.productdelivery.ui.LANGUAGE_RO
import javax.inject.Inject

interface ILocalInformation {

    fun saveLanguage(language: String)
     fun getLanguage(): String
     fun clearSharedPrefs()

}