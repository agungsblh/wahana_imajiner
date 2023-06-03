package com.pervasive.wahana.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Converter {
    fun timeInMillisToDate(timeinmillis:Long):String{
        val tanggal = "dd"
        val tahun = "yyyy"
        val bulan = "MM"
        val tanggal_string = SimpleDateFormat(tanggal, Locale.US).format(timeinmillis)
        val tahun_string = SimpleDateFormat(tahun, Locale.US).format(timeinmillis)
        var bulan_strings = SimpleDateFormat(bulan, Locale.US).format(timeinmillis)

        return "$tahun_string-$bulan_strings-$tanggal_string"
    }
    fun mataUangRupiah(uang:Int):String{

        return "Rp. "+uang.toString().reversed().chunked(3).joinToString(".").reversed()
    }

}