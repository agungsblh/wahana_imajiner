package com.pervasive.wahana.activities

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.ActivityWahanaScanningBinding
import com.pervasive.wahana.utils.GlobalData

class WahanaScanningActivity : AppCompatActivity() {
    private lateinit var binding:ActivityWahanaScanningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWahanaScanningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var kode = "HUHUHU89"


        //button
        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(kode,BarcodeFormat.QR_CODE,512,512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
            for(x in 0 until width){
                for (y in 0 until height){
                    bmp.setPixel(x,y,if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                }
            }
            binding.QRCode.setImageBitmap(bmp)
            binding.kode.text = kode
            binding.namaUser.text = GlobalData.nama_user
        }catch (e:Exception){

        }



    }
}