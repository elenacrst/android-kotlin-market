package com.example.productdelivery.di

import android.content.Context
import com.example.productdelivery.BuildConfig
import com.example.productdelivery.DeliveryApplication
import com.example.productdelivery.MainActivity
import com.example.productdelivery.data.DeliveryRepository
import com.example.productdelivery.data.api.BasicHeaderInterceptor
import com.example.productdelivery.data.api.DeliveryApiService
import com.example.productdelivery.data.api.NetworkFactory
import com.example.productdelivery.data.local.ILocalInformation
import com.example.productdelivery.data.local.LocalInformation
import com.example.productdelivery.data.local.PreferencesSource
import com.example.productdelivery.ui.categories.CategoryViewModel
import com.example.productdelivery.ui.products.ProductViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton
import kotlin.reflect.jvm.internal.impl.incremental.components.LocationInfo


val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//val basicHeaderInterceptor = BasicHeaderInterceptor()

val retrofitDeliveryService: DeliveryApiService by lazy {
    val networkFactory = NetworkFactory(moshi)
    networkFactory.createApi(
        DeliveryApiService::class.java,
        BuildConfig.SERVER_URL
    )
}

/*
val checkArticleModule = module {
    viewModel { CheckArticleViewModel(get()) }
}
*/

@Module
class AppModule {

    // 1
    @Provides
    @Singleton
    fun provideLocalInfo(preferencesSource: PreferencesSource): ILocalInformation =
        LocalInformation(preferencesSource)

    // 2
    @Provides
    @Singleton
    fun providePref(context: Context): PreferencesSource =
        PreferencesSource(context)

    // 3
    @Provides
    @Singleton
    fun provideContext(): Context = DeliveryApplication.deliveryApplicationContext

    @Provides
    @Singleton
    fun provideRepository(): DeliveryRepository = DeliveryRepository()


    @Provides
    @Singleton
    fun provideCategoriesViewModel(repository: DeliveryRepository): CategoryViewModel = CategoryViewModel(repository)


}