package com.example.productdelivery.di

import android.content.Context
import com.example.productdelivery.DeliveryApplication
import com.example.productdelivery.MainActivity
import com.example.productdelivery.data.local.LocalInformation
import com.example.productdelivery.data.local.PreferencesSource
import com.example.productdelivery.ui.categories.CategoriesFragment
import com.example.productdelivery.ui.products.ProductsFragment
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerFragment
import javax.inject.Singleton


@Component(
    modules = [AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class
    ]
)
@Singleton
interface DeliveryComponent {

    fun context(): Context

    fun localInfo(): LocalInformation

    fun preferences(): PreferencesSource

    fun inject(activity: MainActivity)
    fun inject(app: DeliveryApplication)
    fun inject(fragment: CategoriesFragment)
    fun inject(fragment: ProductsFragment)
}
