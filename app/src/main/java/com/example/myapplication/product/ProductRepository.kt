//package com.example.myapplication.product
//
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import kotlinx.coroutines.tasks.await
//
//class ProductRepository {
//    private val db = FirebaseFirestore.getInstance()
//
//    // Mendapatkan ID user saat ini
//    private val currentUserId: String?
//        get() = FirebaseAuth.getInstance().currentUser?.uid
//
//    // Mendapatkan subkoleksi "products" dari user tertentu
//    private fun getUserProductsCollection() =
//        currentUserId?.let { db.collection("users").document(it).collection("products") }
//
//    // Mengambil semua produk dari Firestore
//    suspend fun getAllProducts(): List<Product> {
//        return try {
//            getUserProductsCollection()
//                ?.get()
//                ?.await()
//                ?.documents
//                ?.mapNotNull { it.toObject(Product::class.java) }
//                ?: emptyList()
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }
//
//    fun listenToProducts(onProductsChange: (List<Product>) -> Unit) {
//        getUserProductsCollection()?.addSnapshotListener { snapshot, e ->
//            if (e != null) {
//                return@addSnapshotListener
//            }
//            val products = snapshot?.documents?.mapNotNull { it.toObject(Product::class.java) } ?: emptyList()
//            onProductsChange(products)
//        }
//    }
//
//
//    // Menambahkan produk baru
//    suspend fun addProduct(product: Product) {
//        try {
//            val collection = getUserProductsCollection() ?: return
//            collection.document(product.id).set(product).await()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    // Memperbarui produk
//    suspend fun updateProduct(product: Product) {
//        try {
//            val collection = getUserProductsCollection() ?: return
//            collection.document(product.id).set(product).await()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    // Menghapus produk berdasarkan ID
//    suspend fun deleteProduct(idProduk: String) {
//        try {
//            val collection = getUserProductsCollection() ?: return
//            collection.document(idProduk).delete().await()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//}
