package com.example.zakamal.dummy

data class DummyTahun(val id: Int, val name: String) {
    companion object {
        fun  createDummyListTahun(): List<DummyTahun> {
            return listOf(
                DummyTahun(3, "2021"),
                DummyTahun(2, "2022"),
                DummyTahun(3, "2023")
            )
        }
    }

}