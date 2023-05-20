package com.pervasive.wahana.utils

class LinkApi {
    companion object{

        //master link
        val link_api:String = "https://mythd.site/wahana/android/api/"
        val link_image:String = "https://mythd.site/wahana/android/images/"
        val link_login = link_api+"login.php"

        //////////////////////////GET
        val link_get_penyakit = link_api+"get_list_penyakit.php"
        val link_get_data_akun = link_api+"get_data_akun.php"
        val link_get_list_riwayat_transaksi = link_api+"get_list_riwayat_transaksi.php"
        val link_cek_tiket_antrian_wahana = link_api+"cek_tiket_antrian_wahana.php"
        val link_cek_status_user = link_api+"cek_status_user.php"
        ////////////////////END GET

        /////////////////////////SEND
        val link_register_user = link_api+"register_user.php"
        val link_topup = link_api+"top_up.php"
        val link_create_antrian_wahana = link_api+"create_antrian.php"
        val link_hapus_antrian = link_api+"hapus_antrian.php"
        /////////////////////////END SEND
    }
}