package com.example.productdelivery

import android.app.Application
import com.example.productdelivery.data.local.LocalInformation
import com.example.productdelivery.di.DaggerDeliveryComponent
import com.example.productdelivery.di.DaggerDeliveryComponent.builder
import com.example.productdelivery.di.DeliveryComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class DeliveryApplication : Application() {

     @Inject
     lateinit var localDataSource: LocalInformation

     lateinit var dLocale: Locale

    private lateinit var _appComponent: DeliveryComponent
    val appComponent: DeliveryComponent
        get() = _appComponent

    override fun onCreate() {
        super.onCreate()

        deliveryApplicationContext = this

        _appComponent = DaggerDeliveryComponent.builder().build()
        appComponent.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.i("App started")

        updateLocale()
    }

    fun updateLocale() {
        val change = localDataSource.getLanguage()
        dLocale = Locale(change)
    }

    companion object {
        lateinit var deliveryApplicationContext: DeliveryApplication
    }
}