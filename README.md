# Monitoring Zakat Express API

Repository ini berisi Express API untuk menyediakan endpoint untuk mengambil data monitoring zakat berdasarkan bulan, memasukkan data zakat, dan menghapus semua data monitoring zakat.

<!--
## Daftar Isi

- [Penggunaan](#penggunaan)
- [Endpoint](#endpoint)
- [Kontribusi](#kontribusi)
- [Lisensi](#lisensi)
-->

## Penggunaan

Setelah mengikuti langkah-langkah instalasi, server Express akan berjalan secara lokal di `http://localhost:9000`. Anda dapat mengakses endpoint API menggunakan alat seperti Postman, atau mengintegrasikannya ke dalam aplikasi frontend Anda.

## Endpoint

### Dapatkan Data Monitoring Zakat per Bulan

Ambil data monitoring zakat untuk bulan tertentu.

- **URL:** `/monitoring-zakat/:bulan`
- **Metode:** `GET`
- **Parameter:**
  - `bulan` (string): Bulan untuk mengambil data (misalnya, `jan`, `feb`).
- **Respon:** JSON array berisi data monitoring zakat untuk bulan yang ditentukan.

### Masukkan Data Zakat

Masukkan data zakat palsu untuk tujuan pengujian.

- **URL:** `/dummy/zakat`
- **Metode:** `POST`
- **Respon:** Pesan sukses atau pesan error.

### Hapus Semua Data Monitoring Zakat

Hapus semua data monitoring zakat dari database.

- **URL:** `/monitoring-zakat`
- **Metode:** `DELETE`
- **Respon:** Pesan sukses atau pesan error.

<!--
## Kontribusi

Kontribusi untuk proyek ini sangat diharapkan. Untuk berkontribusi, ikuti langkah-langkah berikut:

1. Fork repositori.
2. Buat cabang baru untuk fitur Anda: `git checkout -b nama-fitur`.
3. Lakukan perubahan dan commit: `git commit -am 'Tambahkan fitur'`.
4. Dorong ke cabang: `git push origin nama-fitur`.
5. Ajukan pull request.

Harap ikuti gaya kode yang ada dan pastikan kode Anda didokumentasikan dengan baik. 
-->

## Lisensi

Proyek ini dilisensikan di bawah [Lisensi MIT](LICENSE).
