package com.pervasive.wahana.utils

class LinkApi {
    companion object{
        //master link
        val link_api:String = "https://mythd.site/wahana/android/api/"
        val link_image:String = "https://mythd.site/wahana/android/images/"

        //////////////////////////GET
        //get penyakit
        val link_get_penyakit = link_api+"get_list_penyakit.php"

        ////////////////////END GET


        /////////////////////////SEND
        val link_register_user = link_api+"register_user.php"

        /////////////////////////END SEND

    }
}