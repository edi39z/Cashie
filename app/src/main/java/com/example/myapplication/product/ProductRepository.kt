package com.example.myapplication.product

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductRepository {
    private val db = FirebaseFirestore.getInstance()

    // Mendapatkan ID user saat ini
    private val currentUserId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    // Mendapatkan subkoleksi "products" dari user tertentu
    private fun getUserProductsCollection() =
        currentUserId?.let { db.collection("users").document(it).collection("products") }

    // Mengambil semua produk dari Firestore
    suspend fun getAllProducts(): List<Product> {
        return try {
            getUserProductsCollection()
                ?.get()
                ?.await()
                ?.documents
                ?.mapNotNull { it.toObject(Product::class.java) }
                ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Menambahkan produk baru
    suspend fun addProduct(product: Product) {
        try {
            val collection = getUserProductsCollection() ?: return
            collection.document(product.id_produk).set(product).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Memperbarui produk
    suspend fun updateProduct(product: Product) {
        try {
            val collection = getUserProductsCollection() ?: return
            collection.document(product.id_produk).set(product).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Menghapus produk berdasarkan ID
    suspend fun deleteProduct(idProduk: String) {
        try {
            val collection = getUserProductsCollection() ?: return
            collection.document(idProduk).delete().await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Menambahkan dummy data
    suspend fun addDummyData() {
        val dummyProducts = listOf(
            Product(id_produk = "P001", nama_produk = "Kopi Hitam", harga_produk = 15000.0, stock_produk = 25),
            Product(id_produk = "P002", nama_produk = "Teh Manis", harga_produk = 12000.0, stock_produk = 40),
            Product(id_produk = "P003", nama_produk = "Coklat Panas", harga_produk = 20000.0, stock_produk = 15)
        )

        try {
            val collection = getUserProductsCollection() ?: return
            for (product in dummyProducts) {
                collection.document(product.id_produk).set(product).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
