package com.pervasive.wahana.model

class OrderModel {
    var id:Int
    var id_menu:Int
    var nama_menu:String
    var jumlah:Int
    var kategori:String

    constructor(id: Int, id_menu: Int, nama_menu: String, jumlah: Int, kategori: String) {
        this.id = id
        this.id_menu = id_menu
        this.nama_menu = nama_menu
        this.jumlah = jumlah
        this.kategori = kategori
    }
}