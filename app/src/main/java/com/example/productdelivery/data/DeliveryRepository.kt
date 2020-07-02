package com.example.productdelivery.data


import com.example.productdelivery.DeliveryApplication
import com.example.productdelivery.R
import com.example.productdelivery.data.api.NoNetworkConnectionException
import com.example.productdelivery.data.model.Category
import com.example.productdelivery.data.model.Product
import com.example.productdelivery.di.retrofitDeliveryService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class DeliveryRepository @Inject constructor() {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    @Throws(Exception::class)
    suspend fun getCategories(): Result<*> {
        var response: Response<List<Category>>? = null

        withContext(ioDispatcher) {
            try {
                response = retrofitDeliveryService.getCategories()
            } catch (e: Exception) {
                e.printStackTrace()
                throw NoNetworkConnectionException()
            }
        }

        return if (response!!.isSuccessful) {
            Result.Success(response?.body())
        } else {
            Result.Error(
                message = DeliveryApplication.deliveryApplicationContext.getString(
                    R.string.general_error_message
                ),
                code = response!!.code()
            )
        }
    }

    @Throws(Exception::class)
    suspend fun getCategoryProducts(categoryId: Long): Result<*> {
        var response: Response<List<Product>>? = null

        withContext(ioDispatcher) {
            try {
                response = retrofitDeliveryService.getCategoryProducts(categoryId)
            } catch (e: Exception) {
                e.printStackTrace()
                throw NoNetworkConnectionException()
            }
        }

        return if (response!!.isSuccessful) {
            Result.Success(response?.body())
        } else {
            Result.Error(
                message = DeliveryApplication.deliveryApplicationContext.getString(
                    R.string.general_error_message
                ),
                code = response!!.code()
            )
        }
    }
}