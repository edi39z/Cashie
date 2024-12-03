package com.example.myapplication.product

data class Product(
    val id_produk: String = "",
    val id_barcode : String = "",
    val nama_produk: String = "",
    val harga_produk: Int = 0,
    var stock_produk: Int = 0
)
