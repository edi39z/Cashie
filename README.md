# Cashie - Aplikasi Kasir Berbasis Jetpack Compose

## Deskripsi
Cashie adalah aplikasi kasir berbasis **Jetpack Compose** yang dirancang untuk membantu pengelolaan transaksi penjualan dan stok produk dengan fitur barcode scanner, pengelolaan stok, dan pembayaran QRIS.

## Fitur Utama

### 1. Manajemen Produk
- **Menambahkan produk secara manual** dengan informasi:
  - Barcode
  - Nama Produk
  - Stok
  - Harga Jual
  - Modal
- **Edit produk** hanya bisa dilakukan dengan **password khusus**.

### 2. Pengelolaan Stok
- Terdapat **kolom pengelolaan stok** di **navbar atau kanan atas** UI.
- **Tabel stok** menampilkan produk yang tersedia dan bisa ditambahkan manual.
- Jika stok tidak tersedia, muncul opsi **"Tambahkan Stok"**.
- Jika suatu produk tidak perlu stok, maka perhitungan tetap bisa berjalan meskipun stoknya negatif.

### 3. Modul Kasir
- **Scanner kamera untuk membaca barcode produk**.
  - Jika scanner tidak berfungsi, barcode bisa dimasukkan secara manual.
- Jumlah produk bisa diakumulasikan otomatis atau dimasukkan secara manual.
- **Menampilkan daftar barang yang dibeli**.
- **Total belanja dihitung otomatis**.
- **Input jumlah uang dari pembeli** untuk menghitung kembalian.
- **Mutasi penjualan dapat dibagikan**.

### 4. Tabel Penjualan
- Menampilkan daftar produk yang telah terjual.
- **Hanya pengguna dengan kode akses yang bisa melihat data stok dan penjualan**.

## Teknologi yang Digunakan
- **Kotlin & Jetpack Compose** - UI modern dan reaktif.
- **CameraX** - Untuk fitur scanner barcode.
- **Firestore Database** - Untuk penyimpanan data produk dan transaksi.
- **Firebase** - Untuk backup dan sinkronisasi data.

**Cashie - Simple & Efficient POS System**


