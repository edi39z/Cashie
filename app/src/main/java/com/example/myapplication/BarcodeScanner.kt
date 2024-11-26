package com.example.myapplication

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class BarcodeScanner(
    appContext: Context
) {

    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    private val scanner = GmsBarcodeScanning.getClient(appContext, options)

    suspend fun startScan(): String? {
        return try {
            val result = scanner.startScan().await()
            Timber.d("Scan Result: ${result.rawValue}")
            result.rawValue
        } catch (e: Exception) {
            Timber.d("Scan error: $e")
            null
        }
    }

    fun startScanInBackground(onResult: (String?) -> Unit) {
        // Launch a background thread for scanning
        kotlinx.coroutines.GlobalScope.launch {
            val result = startScan()
            onResult(result)
        }
    }
}
