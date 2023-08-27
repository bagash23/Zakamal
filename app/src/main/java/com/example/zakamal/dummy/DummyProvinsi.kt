package com.example.zakamal.dummy

data class DummyProvinsi(val id: Int, val name: String) {
    companion object {
        fun createDummyList(): List<DummyProvinsi> {
            return listOf(
                DummyProvinsi(1, "Aceh"),
                DummyProvinsi(2, "Sumatera Utara"),
                DummyProvinsi(3, "Sumatera Barat"),
                DummyProvinsi(4, "Riau"),
                DummyProvinsi(5, "Jambi"),
                DummyProvinsi(6, "Sumatera Selatan"),
                DummyProvinsi(7, "Bengkulu"),
                DummyProvinsi(8, "Lampung"),
                DummyProvinsi(9, "Kepulauan Bangka Belitung"),
                DummyProvinsi(10, "Kepulauan Riau"),
                DummyProvinsi(11, "DKI Jakarta"),
                DummyProvinsi(12, "Jawa Barat"),
                DummyProvinsi(13, "Jawa Tengah"),
                DummyProvinsi(14, "DI Yogyakarta"),
                DummyProvinsi(15, "Jawa Timur"),
                DummyProvinsi(16, "Banten"),
                DummyProvinsi(17, "Bali"),
                DummyProvinsi(18, "Nusa Tenggara Barat"),
                DummyProvinsi(19, "Nusa Tenggara Timur"),
                DummyProvinsi(20, "Kalimantan Barat"),
                DummyProvinsi(21, "Kalimantan Tengah"),
                DummyProvinsi(22, "Kalimantan Selatan"),
                DummyProvinsi(23, "Kalimantan Timur"),
                DummyProvinsi(24, "Kalimantan Utara"),
                DummyProvinsi(25, "Sulawesi Utara"),
                DummyProvinsi(26, "Sulawesi Tengah"),
                DummyProvinsi(27, "Sulawesi Selatan"),
                DummyProvinsi(28, "Sulawesi Tenggara"),
                DummyProvinsi(29, "Gorontalo"),
                DummyProvinsi(30, "Sulawesi Barat"),
                DummyProvinsi(31, "Maluku"),
                DummyProvinsi(32, "Maluku Utara"),
                DummyProvinsi(33, "Papua Barat"),
                DummyProvinsi(34, "Papua")
            )
        }
    }
}