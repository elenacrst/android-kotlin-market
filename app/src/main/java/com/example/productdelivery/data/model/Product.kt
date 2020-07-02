package com.example.productdelivery.data.model

import com.squareup.moshi.Json

data class Product(
    @field:Json(name = "idProd") @Json(name = "idProd")
    var productId: Long? = 0,

    @field:Json(name = "name") @Json(name = "name")
    var name: String? = null,

    @field:Json(name = "price") @Json(name = "price")
    var price: Double? = 0.0
)