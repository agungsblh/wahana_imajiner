package com.pervasive.wahana.model

class MakananModel {
    var id:Int
    var nama:String
    var harga:Int
    var stok:Int
    var kategori:String
    var jumlah:Int = 1

    constructor(id: Int, nama: String, harga: Int, stok: Int, kategori: String) {
        this.id = id
        this.nama = nama
        this.harga = harga
        this.stok = stok
        this.kategori = kategori
    }
}