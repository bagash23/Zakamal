# API Express Back-End untuk ZakAmal

Repositori ini berisi API Express yang menyediakan berbagai endpoint untuk memfasilitasi pengambilan data pada Aplikasi ZakAmal.

## Daftar Isi

- [Penggunaan](#penggunaan)
- [Endpoint](#endpoint)
- [Lisensi](#lisensi)

## Penggunaan

Setelah mengikuti langkah-langkah instalasi, server Express akan berjalan secara lokal di `http://localhost:9000`. Anda dapat mengakses endpoint API menggunakan alat seperti Postman atau mengintegrasikannya dengan aplikasi frontend.

## Endpoint

### Data Dummy

#### Memasukkan Data Dummy Monitoring Zakat
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

### Data Provinsi

#### Mendapatkan Semua Data Provinsi
- **URL:** `/admin/provinsi`
- **Metode:** `GET`
- **Respon:** Data zakat atau pesan error.

#### Mendapatkan Data Provinsi berdasarkan ID
- **URL:** `/admin/provinsi/:id_provinsi`
- **Param Path:** `id_provinsi`
- **Metode:** `GET`
- **Respon:** Data zakat atau pesan error.

#### Menambahkan Data Zakat berdasarkan Provinsi
- **URL:** `/admin/provinsi/add`
- **Body Input:** `nama_provinsi`
- **Metode:** `POST`
- **Respon:** Data zakat atau pesan error.

#### Mengubah Data Zakat berdasarkan Provinsi
- **URL:** `/admin/provinsi/update/:id_provinsi`
- **Param Path:** `id_provinsi`
- **Body Input:** `nama_provinsi`
- **Metode:** `PUT`
- **Respon:** Data zakat atau pesan error.

#### Menghapus Data Zakat berdasarkan Provinsi
- **URL:** `/admin/provinsi/delete/:id_provinsi`
- **Param Path:** `id_provinsi`
- **Metode:** `DELETE`
- **Respon:** Data zakat atau pesan error.

#### Monitoring Data Zakat

#### Mendapatkan Data Zakat berdasarkan Provinsi
- **URL:** `/admin/monitoring-zakat/provinsi/:nama_provinsi`
- **Param Path:** `nama_provinsi`
- **Metode:** `GET`
- **Respon:** Data zakat atau pesan error.

#### Mendapatkan Data Zakat berdasarkan Provinsi dan Tahun
- **URL:** `/admin/monitoring-zakat/provinsi/:nama_provinsi/tahun/:tahun`
- **Param Path:** `nama_provinsi` dan `tahun`
- **Metode:** `GET`
- **Respon:** Data zakat atau pesan error.

#### Menambahkan Data Zakat Baru
- **URL:** `/admin/monitoring-zakat/add`
- **Body Input:** `id_provinsi, bulan, tahun, total_terkumpul, total_pengeluaran`
- **Metode:** `POST`
- **Respon:** Pesan sukses atau pesan error.

#### Mengubah Data Zakat berdasarkan ID Monitoring Zakat
- **URL:** `/admin/monitoring-zakat/update/:id_mon_zakat`
- **Param Path:** `id_mon_zakat`
- **Body Input:** `id_provinsi, bulan, tahun, total_terkumpul, total_pengeluaran`
- **Metode:** `PUT`
- **Respon:** Pesan sukses atau pesan error.

#### Menghapus Data Zakat berdasarkan ID Monitoring Zakat
- **URL:** `/admin/monitoring-zakat/delete/:id_mon_zakat`
- **Param Path:** `id_mon_zakat`
- **Metode:** `DELETE`
- **Respon:** Pesan sukses atau pesan error.

## Lisensi

Proyek ini dilisensikan di bawah [Lisensi MIT](LICENSE).
