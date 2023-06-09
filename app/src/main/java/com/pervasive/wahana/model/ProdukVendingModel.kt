package com.pervasive.wahana.model

class ProdukVendingModel {
    var id:Int
    var nama:String
    var harga:Int
    var gambar : String
    var stok:Int
    var jenis:String
    var jumlah:Int = 1


    constructor(
        id: Int,
        nama: String,
        harga: Int,
        gambar: String,
        stok: Int,
        jenis: String,
    ) {
        this.id = id
        this.nama = nama
        this.harga = harga
        this.gambar = gambar
        this.stok = stok
        this.jenis = jenis
    }
}