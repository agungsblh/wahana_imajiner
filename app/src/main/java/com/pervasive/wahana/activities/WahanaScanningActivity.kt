package com.pervasive.wahana.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.ActivityWahanaScanningBinding
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialog
import com.pervasive.wahana.utils.LoadingDialogFrg

class WahanaScanningActivity : AppCompatActivity() {
    private lateinit var binding:ActivityWahanaScanningBinding
    val loading = LoadingDialog(this)
    lateinit var sharedPreferences: SharedPreferences

    //var kode = String()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWahanaScanningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        //val intent = intent
        if (intent.getStringExtra("state").equals("CREATE")){
            var kode_wahana = intent.getStringExtra("kode")
            when(kode_wahana){
                "WHN-001-RH"->{
                    binding.namaWahana.text = "Rumah Hantu"
                    createAntrian(1)
                }
                "WHN-002-RC"->{
                    binding.namaWahana.text = "Roller Coaster"
                    createAntrian(2)
                }
                "WHN-003-KP"->{
                    binding.namaWahana.text = "Komedi Putar"
                    createAntrian(3)
                }
            }

        }else{
            // cek barcode
            binding.namaWahana.text = "Wahana"
            writeCode(intent.getStringExtra("KODE").toString())
        }

        binding.batalkanAntrian.setOnClickListener {
            batalkanAntrian()
        }
        check_tiket()
    }
    private fun check_tiket(){
        val handler = Handler()
        val delay = 3000 //milliseconds
        handler.postDelayed(object :Runnable{
            override fun run() {
                var url:String = LinkApi.link_cek_tiket_antrian_wahana
                var request: RequestQueue = Volley.newRequestQueue(this@WahanaScanningActivity)
                var stringRequest = StringRequest(
                    Request.Method.GET,url+"?barcode="+binding.kode.text.toString(),
                    { response ->

                        if(response.equals("Sudah discan")){

                            try {
                                showDialogComplete("Masuk","Selamat menikmati wahana kami, semoga menyenangkan")
                                handler.removeCallbacks(this)
                            }catch (ex:Exception){

                            }

                        }else{
                            //loop
                            handler.postDelayed(this,delay.toLong())
                        }
                    },
                    { error ->
                        Toast.makeText(this@WahanaScanningActivity,"Terjadi kesalahan sistem, coba lagi",Toast.LENGTH_SHORT).show()
                    })
                request.add(stringRequest)
            }
        },delay.toLong())
    }
    private fun batalkanAntrian(){
        loading.startLoading()
        var url:String = LinkApi.link_hapus_antrian
        var request: RequestQueue = Volley.newRequestQueue(this)
        var stringRequest = StringRequest(
            Request.Method.GET,url+"?barcode="+binding.kode.text.toString(),
            { response ->
                loading.isDismiss()
                if(response.equals("Sukses")){
                    try {
                        showDialogComplete("Antrian dibatalkan","Silahkan meninggalkan barisan antrian")
                    }catch (ex:Exception){

                    }

                }else{
                    Toast.makeText(this,"Terjadi kesalahan sistem, coba lagi",Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                loading.isDismiss()
                Toast.makeText(this,"Terjadi kesalahan sistem, coba lagi",Toast.LENGTH_SHORT).show()
            })
        request.add(stringRequest)
    }
    private fun showDialogComplete(judulnya:String,isinya:String){
        val view = View.inflate(this, R.layout.dialog_anim_ok,null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val dialog = builder.create()

        val judul = view.findViewById<TextView>(R.id.judul)
        val isi = view.findViewById<TextView>(R.id.isi)
        val btn_yes = view.findViewById<Button>(R.id.btn_ok)
        val anim = view.findViewById<LottieAnimationView>(R.id.anim)

        anim.setAnimation(R.raw.anim_complete)
        anim.loop(false)
        judul.text = judulnya
        isi.text = isinya

        try {
            dialog.show()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            btn_yes.setOnClickListener {
                finish()
            }
        }catch (e:java.lang.Exception){

        }

    }
    private fun createAntrian(id_wahana:Int){
        loading.startLoading()
        var url:String = LinkApi.link_create_antrian_wahana
        var request: RequestQueue = Volley.newRequestQueue(applicationContext)
        var stringRequest = StringRequest(
            Request.Method.GET,url+"?email="+GlobalData.email_user+"&password="+GlobalData.password+"&id_wahana="+id_wahana+"&riwayat_penyakit="+GlobalData.id_riwayat_penyakit,
            { response ->
                loading.isDismiss()
                if(response.toString().length==10){
                    try {
                        val editor:SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("barcode_antrian",response.toString())
                        editor.apply()
                        writeCode(response.toString())
                    }catch (ex:Exception){
                        Toast.makeText(this,"Terjadi Kesalahan",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }else{
                    Toast.makeText(this,response.toString(),Toast.LENGTH_SHORT).show()
                    finish()
                }
            },
            { error ->
                Log.d("ErrorApp",error.toString())
                loading.isDismiss()
                finish()

            })
        request.add(stringRequest)
    }
    private fun writeCode(code:String){
        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(code,BarcodeFormat.QR_CODE,512,512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565)
            for(x in 0 until width){
                for (y in 0 until height){
                    bmp.setPixel(x,y,if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                }
            }
            binding.QRCode.setImageBitmap(bmp)
            binding.kode.text = code
            binding.namaUser.text = GlobalData.nama_user
        }catch (e:Exception){

        }
    }
}