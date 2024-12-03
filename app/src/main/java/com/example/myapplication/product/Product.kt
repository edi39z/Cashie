package com.example.myapplication.product

import com.google.firebase.Timestamp

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
data class Receipt(
    val id: String = "",
    val items : List<PreviewProduct> = emptyList(),
    val totalPrice: Double = 0.0,
    val changes: Double = 0.0,
    val payment : Double = 0.0,
    val timestamp: Timestamp = Timestamp.now(),
)
