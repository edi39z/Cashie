package com.example.myapplication.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    fun fetchProducts() {
        viewModelScope.launch {
            _products.value = repository.getAllProducts()
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            repository.addProduct(product)
            fetchProducts()
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
            fetchProducts()
        }
    }

    fun deleteProduct(idProduk: String) {
        viewModelScope.launch {
            repository.deleteProduct(idProduk)
            fetchProducts()
        }
    }
}
