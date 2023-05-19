package com.pervasive.wahana.model

class RiwayatTransaksiModel {
    var id:Int
    var id_user:Int
    var deskripsi:String
    var date:String

    constructor(id: Int, id_user: Int, deskripsi: String, date: String) {
        this.id = id
        this.id_user = id_user
        this.deskripsi = deskripsi
        this.date = date
    }




}