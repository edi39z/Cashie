package com.example.myapplication.views.casier.`fun`

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.tasks.await

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
            result.rawValue
        } catch (e: Exception) {
            null
        }
    }
}
