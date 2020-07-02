package com.example.productdelivery.data.api

import com.example.productdelivery.data.model.Category
import com.example.productdelivery.data.model.Product
import retrofit2.Response
import retrofit2.http.*

interface DeliveryApiService {

    @GET("/api/categories")
    suspend fun getCategories(): Response<List<Category>>

    @GET("/api/products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("/api/products/{idCat}")
    suspend fun getCategoryProducts(@Path("idCat") categoryId: Long): Response<List<Product>>
}
