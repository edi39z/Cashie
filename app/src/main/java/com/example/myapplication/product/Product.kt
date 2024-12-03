package com.example.myapplication.product

data class Product(
    val id: String = "",
    val barcode : String = "",
    val name: String = "",
    val price: Double = 0.0,
    var stock: Int = 0
)

data class PreviewProduct(
    val productId: String = "",
    val barcode: String = "",
    val name: String,
    var count: Int,
    var price: Double
)
