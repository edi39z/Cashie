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
        addRowButton.setOnClickListener { addNewRow() }

        // Simpan data ke Firebase saat tombol Save ditekan
        saveButton.setOnClickListener { saveDataToFirebase() }

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    Log.d("FirebaseData", "Data: ${data.value}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Error: ${error.message}")
            }
        })

    }



    private fun addNewRow() {
        val tableRow = TableRow(this)

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
    }

    private fun saveDataToFirebase() {
        val rowCount = tableLayout.childCount
        val data = mutableListOf<Map<String, Any>>()


            val row = tableLayout.getChildAt(i) as TableRow

            val kode = (row.getChildAt(0) as EditText).text.toString().trim()
            val nama = (row.getChildAt(1) as EditText).text.toString().trim()
            val jumlah = (row.getChildAt(2) as EditText).text.toString().trim()
            val harga = (row.getChildAt(3) as EditText).text.toString().trim()

            // Validasi input
            if (kode.isEmpty() || nama.isEmpty() || jumlah.isEmpty() || harga.isEmpty()) {
                Toast.makeText(this, "Baris $i: Semua kolom harus diisi!", Toast.LENGTH_SHORT).show()
                return
            }

            val jumlahInt = jumlah.toIntOrNull()
            val hargaInt = harga.toIntOrNull()
            if (jumlahInt == null || hargaInt == null) {
                Toast.makeText(this, "Baris $i: Jumlah dan Harga harus berupa angka!", Toast.LENGTH_SHORT).show()
                return
            }

            val item = mapOf(
                "kode" to kode,
                "nama_barang" to nama,
                "jumlah" to jumlahInt,
                "harga" to hargaInt
            )
            data.add(item)
        }

        // Simpan data ke Firebase
        data.forEach { item ->
            val key = database.push().key ?: return
            database.child(key).setValue(item)
        }

        Toast.makeText(this, "Data berhasil disimpan ke Firebase!", Toast.LENGTH_SHORT).show()
    }
}