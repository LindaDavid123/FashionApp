package com.example.fashion.response.produk

data class UserCartResponse(
    val id_produk: String,
    val nama_produk: String,
    val harga: String,
    val size: String,
    val gambar_produk: String,
    val jumlah_produk: Int,
    var isChecked: Boolean = false
)
