# 🧾 Aplikasi Kasir Toko (Java Swing)

Project ini merupakan **aplikasi kasir toko berbasis desktop** yang dibangun menggunakan **Java Swing**. Aplikasi ini dibuat untuk memenuhi kebutuhan pengelolaan produk, transaksi penjualan, serta laporan transaksi secara sederhana dan terstruktur.

## ✨ Fitur Utama

- 📦 **Manajemen Produk**
    - Tambah, edit, dan hapus produk
    - Pengelompokan produk berdasarkan kategori
    - Penyimpanan data produk ke file CSV

- 🛒 **Transaksi Penjualan**
    - Menambahkan produk ke keranjang
    - Menghitung total harga otomatis
    - Menyimpan transaksi ke dalam riwayat

- 📑 **Riwayat & Laporan Transaksi**
    - Melihat daftar transaksi yang pernah dilakukan
    - Menyimpan riwayat transaksi ke file CSV
    - Format tampilan harga dan tanggal yang rapi

- 🖥️ **Antarmuka GUI**
    - Menggunakan Java Swing
    - Tampilan dashboard kasir
    - Layout terpisah untuk kasir, produk, dan laporan

## 🛠️ Teknologi yang Digunakan

- **Bahasa Pemrograman**: Java
- **GUI Framework**: Java Swing
- **Build Tool**: Maven
- **Penyimpanan Data**:
    - CSV (`produk.csv`, `transaksi.csv`)
    - File serialisasi (`keranjang.dat`)
- **IDE (Opsional)**: IntelliJ IDEA / NetBeans

## 📋 Prasyarat Instalasi

Pastikan perangkat kamu sudah memiliki:

- Java Development Kit (JDK) 8 atau lebih baru
- Apache Maven
- Git (opsional, jika clone repository)

Cek versi Java:
```bash
  java -version
```

## 🚀 Cara Menjalankan Aplikasi

Clone repository:
```bash
  git clone https://github.com/username/Project-UAP_PEMLAN.git
```

Masuk ke folder project:
```bash
  cd Project-UAP_PEMLAN 
```

Build project dengan Maven:
```bash
  mvn clean install
```
Jalankan class:
```bash
  org.example.kasirtoko.MainApp
```
## Implementasi Konsep OOP
```bash
  ---

Aplikasi ini dikembangkan dengan menerapkan prinsip **Object-Oriented Programming (OOP)** sebagai berikut:

### 1. Encapsulation
Setiap class memiliki atribut dan method yang dibungkus dalam satu kesatuan objek.
Contoh:
- Class `Produk` menyimpan data seperti `id`, `nama`, `harga`, dan `stok`
- Akses data dilakukan melalui method (getter dan setter)

### 2. Abstraction
Detail implementasi disembunyikan dari pengguna.
Contoh:
- Class `ProdukManager` menangani logika pengelolaan produk tanpa memperlihatkan detail penyimpanan file
- Class `TransaksiManager` mengatur proses transaksi secara terpisah dari tampilan GUI

### 3. Separation of Concern
Setiap package memiliki tanggung jawab masing-masing:
- `model` → Representasi data
- `service` → Logika bisnis
- `view` → Antarmuka pengguna
- `util` → Fungsi pendukung

### 4. Reusability
Class utilitas seperti `FileUtil` dan `FormatterUtil` dapat digunakan kembali di berbagai bagian aplikasi tanpa duplikasi kode.

---

## Diagram UML (Class Diagram – Ringkas)

```text
+----------------+
|     Produk     |
+----------------+
| - nama         |
| - harga        |
| - stok         |
+----------------+
| + getId()      |
| + getNama()    |
| + getHarga()   |
| + getStok()    |
+----------------+

+---------------------+
|   ProdukManager     |
+---------------------+
| - daftarProduk      |
+---------------------+
| + tambahProduk()    |
| + hapusProduk()     |
| + updateProduk()    |
| + simpanKeCSV()     |
| + loadDariCSV()     |
+---------------------+

+------------------+
|    Transaksi     |
+------------------+
| - tanggal        |
| - totalHarga     |
| - itemList       |
+------------------+
| + hitungTotal()  |
+------------------+

+------------------------+
|  TransaksiManager      |
+------------------------+
| + simpanTransaksi()    |
| + loadRiwayat()        |
+------------------------+

+------------------+
|     FileUtil     |
+------------------+
| + readCSV()      |
| + writeCSV()     |
+------------------+

```

## 🗂️ Struktur Project
```bash
 Project-UAP_PEMLAN
│
├── data/
│   └── transaksi.csv
│
├── src/main/java/org/example/kasirtoko
│   ├── MainApp.java
│   │
│   ├── model/
│   │   ├── Produk.java
│   │   ├── Kategori.java
│   │   ├── Transaksi.java
│   │   └── ItemTransaksi.java
│   │
│   ├── service/
│   │   ├── ProdukManager.java
│   │   ├── TransaksiManager.java
│   │   └── LaporanManager.java
│   │
│   ├── util/
│   │   ├── FileUtil.java
│   │   └── FormatterUtil.java
│   │
│   └── view/
│       ├── DashboardView.java
│       ├── KasirView.java
│       ├── ProdukFormView.java
│       ├── ProdukListView.java
│       ├── RiwayatTransaksiView.java
│       └── UIStyle.java
│
├── produk.csv
├── keranjang.dat
├── pom.xml
└── README.md
```

## 🧪 Contoh Penggunaan
Jalankan aplikasi

Masuk ke Dashboard

Tambahkan produk melalui menu Manajemen Produk

Lakukan transaksi di menu Kasir

Lihat riwayat transaksi pada menu Riwayat Tr

## 🤝 Kontribusi
Kontribusi sangat terbuka!

Langkah kontribusi:
1. Fork Repository ini
2. Buat Branch baru untuk fitur baru
3. Commit Perubahan
4. Push ke branch
5. Buat pull request

*Pastikan kode tetap rapi dan mengikuti struktur project.*

## 📄 Lisensi
Project ini menggunakan lisensi MIT License.
```bash
  MIT License

Copyright (c) 2025

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...

```

## Catatan Akademik
Proyek ini dikembangkan murni untuk keperluan akademik dan pembelajaran.
Yang beranggotakan:
1. Hamdan Maulana
NIM : 202410370110223
2. Maulvinazir Achmad Indraraia
Nim : 202410370110254

---



