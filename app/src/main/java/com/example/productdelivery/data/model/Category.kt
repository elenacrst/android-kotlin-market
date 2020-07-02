package com.example.productdelivery.data.model

import com.squareup.moshi.Json

data class Category(
    @field:Json(name = "idCat") @Json(name = "idCat")
    var categoryId: Long? = 0,

    @field:Json(name = "name") @Json(name = "name")
    var name: String? = null
)