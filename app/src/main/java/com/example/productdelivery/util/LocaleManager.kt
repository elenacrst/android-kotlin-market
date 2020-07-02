package com.example.productdelivery.util

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.view.ContextThemeWrapper
import com.example.productdelivery.DeliveryApplication
import java.util.*


//update locale from app
fun updateLocaleConfig(wrapper: ContextThemeWrapper) {
    if (DeliveryApplication.deliveryApplicationContext.dLocale != Locale("")) {

        Locale.setDefault(DeliveryApplication.deliveryApplicationContext.dLocale)
        val configuration = Configuration()
        configuration.setLocale(DeliveryApplication.deliveryApplicationContext.dLocale)

        wrapper.applyOverrideConfiguration(configuration)
    }
}

//update locale from activity, so as the strings from application to change too
@Suppress("DEPRECATION")
fun wrap(context: Context, language: String): ContextWrapper {
    val newContext: Context

    val locale = Locale(language)
    val res = context.resources
    val configuration = res.configuration

    newContext = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        configuration.setLocale(locale)

        val localeList = LocaleList(locale)
        LocaleList.setDefault(localeList)
        configuration.setLocales(localeList)

        context.createConfigurationContext(configuration)

    } else {
        configuration.setLocale(locale)
        context.createConfigurationContext(configuration)
    }

    //for string from app context
    context.applicationContext.resources.updateConfiguration(configuration, res.displayMetrics)

    return ContextWrapper(newContext)
}