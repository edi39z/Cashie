package com.example.myapplication.Views

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        tableLayout = findViewById(R.id.tableLayout)
        database = FirebaseDatabase.getInstance().reference.child("database")

        val addRowButton = findViewById<Button>(R.id.addRowButton)
        val saveButton = findViewById<Button>(R.id.saveButton)

        // Tambahkan baris baru saat tombol Add Row ditekan
        addRowButton.setOnClickListener {
            Log.d("DatabaseActivity", "Add Row button clicked")
            addNewRow()
        }

        // Simpan data ke Firebase saat tombol Save ditekan
        saveButton.setOnClickListener {
            Log.d("DatabaseActivity", "Save button clicked")
            saveDataToFirebase()
        }

        // Membaca data dari Firebase untuk keperluan debugging
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    Log.d("FirebaseData", "Data from Firebase: ${data.value}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Error while reading Firebase: ${error.message}")
            }
        })
    }

    private fun addNewRow() {
        val tableRow = TableRow(this)
        Log.d("DatabaseActivity", "Adding new row")

        // Tambahkan EditText untuk setiap kolom
        val kodeEditText = EditText(this).apply { hint = "Kode" }
        val namaEditText = EditText(this).apply { hint = "Nama Barang" }
        val jumlahEditText = EditText(this).apply { hint = "Jumlah" }
        val hargaEditText = EditText(this).apply { hint = "Harga (₺)" }

        // Tambahkan EditText ke baris
        tableRow.addView(kodeEditText)
        tableRow.addView(namaEditText)
        tableRow.addView(jumlahEditText)
        tableRow.addView(hargaEditText)

        // Tambahkan baris ke tabel
        tableLayout.addView(tableRow)
        Log.d("DatabaseActivity", "Row added successfully")
    }

    private fun saveDataToFirebase() {
        CoroutineScope(Dispatchers.IO).launch {
            val rowCount = tableLayout.childCount
            Log.d("DatabaseActivity", "Saving data to Firebase. Total rows: $rowCount")
            val data = mutableListOf<Map<String, Any>>()

            for (i in 0 until rowCount) {
                val row = tableLayout.getChildAt(i) as? TableRow ?: continue

                val kode = (row.getChildAt(0) as EditText).text.toString().trim()
                val nama = (row.getChildAt(1) as EditText).text.toString().trim()
                val jumlah = (row.getChildAt(2) as EditText).text.toString().trim()
                val harga = (row.getChildAt(3) as EditText).text.toString().trim()

                if (kode.isEmpty() || nama.isEmpty() || jumlah.isEmpty() || harga.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@DatabaseActivity,
                            "Baris $i: Semua kolom harus diisi!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }

                val jumlahInt = jumlah.toIntOrNull()
                val hargaInt = harga.toIntOrNull()
                if (jumlahInt == null || hargaInt == null) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@DatabaseActivity,
                            "Baris $i: Jumlah dan Harga harus berupa angka!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    return@launch
                }

                val item = mapOf(
                    "kode" to kode,
                    "nama_barang" to nama,
                    "jumlah" to jumlahInt,
                    "harga" to hargaInt
                )
                data.add(item)
            }

            // Simpan ke Firebase
            data.forEach { item ->
                val key = database.push().key ?: return@launch
                database.child(key).setValue(item).addOnSuccessListener {
                    Log.d("DatabaseActivity", "Data saved successfully: $item")
                }.addOnFailureListener { e ->
                    Log.e("DatabaseActivity", "Error saving data to Firebase: ${e.message}")
                }
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@DatabaseActivity,
                    "Data berhasil disimpan ke Firebase!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}