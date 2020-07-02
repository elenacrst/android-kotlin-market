package com.example.productdelivery.data.local

import com.example.productdelivery.DeliveryApplication
import com.example.productdelivery.ui.LANGUAGE_RO
import javax.inject.Inject

class LocalInformation @Inject constructor(private val preferencesSource: PreferencesSource) : ILocalInformation{

    override fun saveLanguage(language: String) {
        preferencesSource.customPrefs()[LANGUAGE_PREFS] = language
    }

    override fun getLanguage(): String {
        return preferencesSource.customPrefs()[LANGUAGE_PREFS] ?: LANGUAGE_RO
    }

    override fun clearSharedPrefs() {
        preferencesSource.customPrefs().edit().clear().apply()
        //let application know the shared prefs changed
        DeliveryApplication.deliveryApplicationContext.updateLocale()
    }

    companion object {
        const val PREFERENCES_FILE_NAME = "preferences"
        const val LANGUAGE_PREFS = "language prefs"
    }
}