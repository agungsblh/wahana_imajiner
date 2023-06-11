package com.pervasive.wahana.model

class RiwayatTiketModel {
    var id:Int
    var id_user:Int
    var no_tiket:String
    var harga:Int
    var date:String

    constructor(id: Int, id_user: Int, no_tiket: String, harga: Int, date: String) {
        this.id = id
        this.id_user = id_user
        this.no_tiket = no_tiket
        this.harga = harga
        this.date = date
    }
}