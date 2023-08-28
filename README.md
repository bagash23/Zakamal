# Back-End Express API untuk ZakAmal

Repositori ini berisi API Express yang menyediakan berbagai endpoint untuk memfasilitasi pengambilan data pada Aplikasi ZakAmal.

## Daftar Isi

- [Penggunaan](#penggunaan)
- [Endpoint](#endpoint)
- [Lisensi](#lisensi)

## Penggunaan

Setelah mengikuti langkah-langkah instalasi, server Express akan berjalan secara lokal di `http://localhost:9000`. Anda dapat mengakses endpoint API menggunakan alat seperti Postman atau mengintegrasikannya dengan aplikasi frontend.

## Endpoint

### Dummy Data

#### Memasukkan Dummy Data Monitoring Zakat
- **URL:** `/dummy/zakat`
- **Metode:** `POST`
- **Respon:** Pesan sukses atau pesan error.

#### Menghapus Semua Data Monitoring Zakat
- **URL:** `/dummy/zakat`
- **Metode:** `DELETE`
- **Respon:** Pesan sukses atau pesan error.

#### Memasukkan Data Dummy Pezakat
- **URL:** `/dummy/pezakat`
- **Metode:** `POST`
- **Respon:** Pesan sukses atau pesan error.

#### Menghapus Semua Data Pezakat
- **URL:** `/dummy/pezakat`
- **Metode:** `DELETE`
- **Respon:** Pesan sukses atau pesan error.

### Admin

#### Menampilkan Data Zakat berdasarkan Provinsi
- **URL:** `/admin/monitoring-zakat/provinsi/:nama_provinsi`
- **Param Input:** `nama_provinsi`
- **Metode:** `GET`
- **Respon:** Data zakat atau pesan error.

#### Menampilkan Data Zakat berdasarkan Provinsi dan Tahun
- **URL:** `/admin/monitoring-zakat/provinsi/:nama_provinsi/tahun/:tahun`
- **Param Input:** `nama_provinsi` dan `tahun`
- **Metode:** `GET`
- **Respon:** Data zakat atau pesan error.

#### Menambahkan Data Zakat Baru
- **URL:** `/admin/monitoring-zakat/add`
- **Body Input:** `id_provinsi, bulan, tahun, total_terkumpul, total_pengeluaran`
- **Metode:** `POST`
- **Respon:** Pesan sukses atau pesan error.

#### Mengubah Data Zakat berdasarkan Id Monitoring Zakat
- **URL:** `/admin/monitoring-zakat/update/:id_mon_zakat`
- **Param Input:** `id_mon_zakat`
- **Body Input:** `id_provinsi, bulan, tahun, total_terkumpul, total_pengeluaran`
- **Metode:** `PUT`
- **Respon:** Pesan sukses atau pesan error.

#### Menghapus Data Zakat berdasarkan Id Monitoring Zakat
- **URL:** `/admin/monitoring-zakat/delete/:id_mon_zakat`
- **Param Input:** `id_mon_zakat`
- **Metode:** `DELETE`
- **Respon:** Pesan sukses atau pesan error.

## Lisensi

Proyek ini dilisensikan di bawah [Lisensi MIT](LICENSE).
