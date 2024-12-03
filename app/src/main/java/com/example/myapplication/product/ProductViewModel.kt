package com.example.myapplication.product

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // Menggunakan StateFlow untuk mengelola status produk
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    // Validasi data produk sebelum ditambahkan/diupdate
    private fun validateProduct(product: Product): Boolean {
        return product.nama_produk.isNotEmpty() && product.harga_produk > 0 && product.stock_produk >= 0
    }
    fun listenToProducts() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        fetchProducts(userId)
    }


    // Fungsi untuk mengambil data produk dari Firestore
    fun fetchProducts(userId: String) {
        db.collection("users")
            .document(userId)
            .collection("products")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("Firestore", "Gagal mengambil data produk", e)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val productList = snapshot.documents.mapNotNull { it.toObject(Product::class.java) }
                    _products.value = productList
                } else {
                    _products.value = emptyList()
                }
            }
    }

    // Fungsi untuk menambahkan produk ke Firestore
    fun addProduct(userId: String, product: Product, context: Context) {
        if (!validateProduct(product)) {
            Log.e("Validation", "Produk tidak valid")
            Toast.makeText(context, "Produk tidak valid!", Toast.LENGTH_SHORT).show()
            return
        }

        // Periksa apakah id_produk sudah ada
        db.collection("users")
            .document(userId)
            .collection("products")
            .whereEqualTo("id_barcode", product.id_barcode)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Jika produk dengan id_produk yang sama sudah ada
                    Toast.makeText(context, "Produk dengan ID ini sudah ada!", Toast.LENGTH_SHORT).show()
                } else {
                    val newProductDoc = db.collection("users").document(userId).collection("products").document()
                    val generatedId = newProductDoc.id
                    newProductDoc
                        .set(product.copy(id_produk = generatedId))
                        .addOnSuccessListener {
                            Toast.makeText(context, "Produk berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                            Log.d("Firestore", "Produk berhasil ditambahkan")
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Gagal menambahkan produk!", Toast.LENGTH_SHORT).show()
                            Log.e("Firestore", "Gagal menambahkan produk", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Terjadi kesalahan saat memeriksa produk!", Toast.LENGTH_SHORT).show()
                Log.e("Firestore", "Gagal memeriksa id_produk", e)
            }
    }


    // Fungsi untuk mengupdate data produk di Firestore
    fun updateProduct(userId: String, product: Product) {
        if (validateProduct(product)) {
            db.collection("users")
                .document(userId)
                .collection("products")
                .document(product.id_produk)
                .set(product)
                .addOnSuccessListener {
                    Log.d("Firestore", "Produk berhasil diperbarui")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Gagal memperbarui produk", e)
                }
        } else {
            Log.e("Validation", "Produk tidak valid")
        }
    }

    fun deleteProduct(productId: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Cari dokumen dengan id_produk yang sesuai
        db.collection("users")
            .document(userId)
            .collection("products")
            .whereEqualTo("id_produk", productId) // Query berdasarkan id_produk
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Hapus semua dokumen yang ditemukan
                    for (document in querySnapshot.documents) {
                        db.collection("users")
                            .document(userId)
                            .collection("products")
                            .document(document.id) // Gunakan ID dokumen dari hasil query
                            .delete()
                            .addOnSuccessListener {
                                Log.d("Firestore", "Produk berhasil dihapus")
                                fetchProducts(userId) // Refresh data setelah penghapusan
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firestore", "Gagal menghapus produk", e)
                            }
                    }
                } else {
                    Log.w("Firestore", "Produk dengan id_produk: $productId tidak ditemukan")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Gagal mencari produk berdasarkan id_produk", e)
            }
    }


}
