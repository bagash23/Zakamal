-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 27 Agu 2023 pada 19.41
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `zakamal`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `monitoring_zakat`
--

CREATE TABLE `monitoring_zakat` (
  `id_mon_zakat` int(11) NOT NULL,
  `id_provinsi` int(11) NOT NULL,
  `bulan` varchar(25) NOT NULL,
  `tahun` int(11) NOT NULL,
  `total_terkumpul` bigint(20) NOT NULL,
  `total_pengeluaran` bigint(20) NOT NULL,
  `total_keseluruhan` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `pezakat`
--

CREATE TABLE `pezakat` (
  `id_pezakat` int(11) NOT NULL,
  `nama_lengkap` varchar(35) NOT NULL,
  `telepon` bigint(20) NOT NULL,
  `email` varchar(25) NOT NULL,
  `password` varchar(60) NOT NULL,
  `id_provinsi` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `post_feed`
--

CREATE TABLE `post_feed` (
  `id_feed` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `nama` varchar(35) NOT NULL,
  `image` blob NOT NULL,
  `tanggal_post` timestamp NOT NULL DEFAULT current_timestamp(),
  `content` text NOT NULL,
  `status` enum('Pending','Approve','Rejected') NOT NULL,
  `id_role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `provinsi`
--

CREATE TABLE `provinsi` (
  `id_provinsi` int(11) NOT NULL,
  `nama_provinsi` varchar(35) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `provinsi`
--

INSERT INTO `provinsi` (`id_provinsi`, `nama_provinsi`) VALUES
(1, 'Nanggroe Aceh Darussalam'),
(2, 'Sumatera Utara'),
(3, 'Sumatera Selatan'),
(4, 'Sumatera Barat'),
(5, 'Bengkulu'),
(6, 'Riau'),
(7, 'Kepulauan Riau'),
(8, 'Jambi'),
(9, 'Lampung'),
(10, 'Bangka Belitung'),
(11, 'Kalimantan Barat'),
(12, 'Kalimantan Timur'),
(13, 'Kalimantan Selatan'),
(14, 'Kalimantan Tengah'),
(15, 'Kalimantan Utara'),
(16, 'Banten'),
(17, 'DKI Jakarta'),
(18, 'Jawa Barat'),
(19, 'Jawa Tengah'),
(20, 'Daerah Istimewa Yogyakarta'),
(21, 'Jawa Timur'),
(22, 'Bali'),
(23, 'Nusa Tenggara Timur'),
(24, 'Nusa Tenggara Barat'),
(25, 'Gorontalo'),
(26, 'Sulawesi Barat'),
(27, 'Sulawesi Tengah'),
(28, 'Sulawesi Utara'),
(29, 'Sulawesi Tenggara'),
(30, 'Sulawesi Selatan'),
(31, 'Maluku Utara'),
(32, 'Maluku'),
(33, 'Papua Barat'),
(34, 'Papua'),
(35, 'Papua Tengah'),
(36, 'Papua Pegunungan'),
(37, 'Papua Selatan'),
(38, 'Papua Barat Daya');

-- --------------------------------------------------------

--
-- Struktur dari tabel `role`
--

CREATE TABLE `role` (
  `id_role` int(11) NOT NULL,
  `nama_role` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `role`
--

INSERT INTO `role` (`id_role`, `nama_role`) VALUES
(1, 'admin'),
(2, 'user');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `id_pezakat` int(11) DEFAULT NULL,
  `nama_lengkap` varchar(35) NOT NULL,
  `telepon` bigint(20) DEFAULT NULL,
  `email` varchar(25) NOT NULL,
  `password` varchar(60) NOT NULL,
  `id_provinsi` int(11) DEFAULT NULL,
  `id_role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `id_pezakat`, `nama_lengkap`, `telepon`, `email`, `password`, `id_provinsi`, `id_role`) VALUES
(1, NULL, 'Admin', NULL, 'admin@mail.com', 'admin123', NULL, 1),
(2, NULL, 'Master', NULL, 'master@mail.com', 'master123', NULL, 1),
(3, NULL, 'Superuser', NULL, 'superuser@mail.com', 'superuser123', NULL, 1),
(4, NULL, 'BAZNAS', NULL, 'baznas@mail.com', 'baznas123', NULL, 1),
(5, NULL, 'TeamBaznas', NULL, 'teambaznas@mail.com', 'teambaznas123', NULL, 1);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `monitoring_zakat`
--
ALTER TABLE `monitoring_zakat`
  ADD PRIMARY KEY (`id_mon_zakat`),
  ADD KEY `id_provinsi` (`id_provinsi`);

--
-- Indeks untuk tabel `pezakat`
--
ALTER TABLE `pezakat`
  ADD PRIMARY KEY (`id_pezakat`),
  ADD KEY `id_provinsi` (`id_provinsi`);

--
-- Indeks untuk tabel `post_feed`
--
ALTER TABLE `post_feed`
  ADD PRIMARY KEY (`id_feed`),
  ADD UNIQUE KEY `id_role` (`id_role`),
  ADD KEY `id_user` (`id_user`);

--
-- Indeks untuk tabel `provinsi`
--
ALTER TABLE `provinsi`
  ADD PRIMARY KEY (`id_provinsi`);

--
-- Indeks untuk tabel `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id_role`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD KEY `id_role` (`id_role`),
  ADD KEY `id_provinsi` (`id_provinsi`),
  ADD KEY `id_pezakat` (`id_pezakat`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `monitoring_zakat`
--
ALTER TABLE `monitoring_zakat`
  MODIFY `id_mon_zakat` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `pezakat`
--
ALTER TABLE `pezakat`
  MODIFY `id_pezakat` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `post_feed`
--
ALTER TABLE `post_feed`
  MODIFY `id_feed` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `provinsi`
--
ALTER TABLE `provinsi`
  MODIFY `id_provinsi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT untuk tabel `role`
--
ALTER TABLE `role`
  MODIFY `id_role` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `monitoring_zakat`
--
ALTER TABLE `monitoring_zakat`
  ADD CONSTRAINT `monitoring_zakat_ibfk_1` FOREIGN KEY (`id_provinsi`) REFERENCES `provinsi` (`id_provinsi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `pezakat`
--
ALTER TABLE `pezakat`
  ADD CONSTRAINT `pezakat_ibfk_1` FOREIGN KEY (`id_provinsi`) REFERENCES `provinsi` (`id_provinsi`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`id_role`) REFERENCES `role` (`id_role`),
  ADD CONSTRAINT `user_ibfk_2` FOREIGN KEY (`id_provinsi`) REFERENCES `provinsi` (`id_provinsi`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_ibfk_3` FOREIGN KEY (`id_pezakat`) REFERENCES `pezakat` (`id_pezakat`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
