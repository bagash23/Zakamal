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
- **Respon:** Pesan sukses atau pesan error.

#### Mendapatkan Data Provinsi berdasarkan ID Provinsi
- **URL:** `/admin/provinsi/:id_provinsi`
- **Param Path:** `id_provinsi`
- **Metode:** `GET`
- **Respon:** Pesan sukses atau pesan error.

#### Menambahkan Data Provinsi
- **URL:** `/admin/provinsi/add`
- **Body Input:** `nama_provinsi`
- **Metode:** `POST`
- **Respon:** Pesan sukses atau pesan error.

#### Mengubah Data Provinsi berdasarkan ID Provinsi
- **URL:** `/admin/provinsi/update/:id_provinsi`
- **Param Path:** `id_provinsi`
- **Body Input:** `nama_provinsi`
- **Metode:** `PUT`
- **Respon:** Pesan sukses atau pesan error.

#### Menghapus Data Provinsi berdasarkan ID Provinsi
- **URL:** `/admin/provinsi/delete/:id_provinsi`
- **Param Path:** `id_provinsi`
- **Metode:** `DELETE`
- **Respon:** Pesan sukses atau pesan error.


#### Monitoring Data Zakat

#### Mendapatkan Data Zakat berdasarkan Nama Provinsi
- **URL:** `/admin/monitoring-zakat/provinsi/:nama_provinsi`
- **Param Path:** `nama_provinsi`
- **Metode:** `GET`
- **Respon:** Pesan sukses atau pesan error.

#### Mendapatkan Data Zakat berdasarkan Nama Provinsi dan Tahun
- **URL:** `/admin/monitoring-zakat/provinsi/:nama_provinsi/tahun/:tahun`
- **Param Path:** `nama_provinsi` dan `tahun`
- **Metode:** `GET`
- **Respon:** Pesan sukses atau pesan error.

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


#### Posting Feed/Pengajuan

#### Mendapatkan Data Semua Post Feed
- **Param Path:** `/user/post_feed/all`
- **Metode:** `GET`
- **Respon:** Pesan sukses atau pesan error.

#### Mendapakan Data Post Feed berdasarkan ID Post Feed
- **URL:** `/user/post_feed/:id_post_feed`
- **Param Path:** `id_post_feed`
- **Metode:** `GET`
- **Respon:** Pesan sukses atau pesan error.

#### Mendapatkan Data Post Feed berdasarkan Nama Provinsi
- **URL:** `/user/post_feed/provinsi/:nama_provinsi`
- **Param Path:** `nama_provinsi`
- **Metode:** `GET`
- **Respon:** Pesan sukses atau pesan error.

#### Mendapatkan Data Post Feed berdasarkan Status Pending
- **URL:** `/user/post_feed/status/1`
- **Metode:** `GET`
- **Respon:** Pesan sukses atau pesan error.

#### Mendapatkan Data Post Feed berdasarkan Status Diterima
- **URL:** `/user/post_feed/status/2`
- **Metode:** `GET`
- **Respon:** Pesan sukses atau pesan error.

#### Mendapatkan Data Post Feed berdasarkan Status Ditolak
- **URL:** `/user/post_feed/status/3`
- **Metode:** `GET`
- **Respon:** Pesan sukses atau pesan error.

#### Mendapatkan Dokumen Post Feed berdasarkan ID Post Feed
- **URL:** `/user/post_feed/:id_post_feed/dokumen`
- **Param Path:** `id_post_feed`
- **Metode:** `GET`
- **Respon:** Pesan sukses atau pesan error.

#### Mendapatkan Dokumen Post Feed berdasarkan ID User dan ID Post Feed
- **URL:** `/user/:id_user/post_feed/:id_post_feed/dokumen`
- **Param Path:** `id_user` dan `id_post_feed`
- **Metode:** `GET`
- **Respon:** Pesan sukses atau pesan error.

#### Menambahkan Data Post Feed berdasarkan ID User
- **URL:** `/user/:id_user/post_feed/add`
- **Param Path:** `id_user`
- **Body Input:** `id_provinsi`, `judul_post`, `biaya`, `alamat`, `keterangan` dan `dokumen`
- **Metode:** `POST`
- **Respon:** Pesan sukses atau pesan error.


#### Komentar pada Post Feed

#### Mendapatkan Data Komentar berdasarkan ID Post Feed
- **URL:** `/user/:id_post_feed/komentar`
- **Param Path:** `id_post_feed`
- **Metode:** `GET`
- **Respon:** Pesan sukses atau pesan error.

#### Menambahkan Data Komentar berdasarkan ID User dan ID Post Feed
- **URL:** `/user/:id_user/:id_post_feed/komentar/add`
- **Param Path:** `id_user`
- **Body Input:** `id_user` dan `id_post_feed`
- **Metode:** `POST`
- **Respon:** Pesan sukses atau pesan error.

#### Mengubah Data Status Post Feed berdasarkan ID Post Feed
- **URL:** `/admin/post_feed/:id_post_feed/status`
- **Param Path:** `id_post_feed`
- **Body Input:** `status`
- **Metode:** `PUT`
- **Respon:** Pesan sukses atau pesan error.

#### Menghapus Data Post Feed berdasarkan ID Post Feed
- **URL:** `/admin/post_feed/delete/:id_post_feed`
- **Param Path:** `id_post_feed`
- **Metode:** `DELETE`
- **Respon:** Pesan sukses atau pesan error.


## Lisensi

Proyek ini dilisensikan di bawah [Lisensi MIT](LICENSE).
