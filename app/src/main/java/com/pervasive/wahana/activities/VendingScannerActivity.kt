package com.pervasive.wahana.activities

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.ActivityScannerBinding
import com.pervasive.wahana.databinding.ActivityVendingScannerBinding
import com.pervasive.wahana.utils.Converter
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialog

class VendingScannerActivity : AppCompatActivity() {
    private lateinit var binding:ActivityVendingScannerBinding
    private lateinit var codeScanner: CodeScanner
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 7
    val loading = LoadingDialog(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendingScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkAndroidVersion()

        var kode_vending = intent.getStringExtra("VENDING")

        codeScanner = CodeScanner(this, binding.scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        onAction()
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                //Toast.makeText(this, "Scan: ${it.text}", Toast.LENGTH_LONG).show()
                /////////////////////
                if (it.text.toString().length==10){
                    when(it.text){
                        "VMC-001-MK"->{
                            if (kode_vending.equals("VMC-001-MK")){
                                //bayar vending machine makanan
//                                Toast.makeText(this,"Bayar vending makanan", Toast.LENGTH_SHORT).show()
                                bayarVendingMachine()
//                                finish()
                            }else{
                                //salah vending
                                Toast.makeText(this,"Salah scan barcode", Toast.LENGTH_SHORT).show()
                            }
                        }
                        "VMC-002-MN"->{
                            if (kode_vending.equals("VMC-002-MN")){
                                //bayar vending machine minuman
//                                Toast.makeText(this,"Bayar vending minuman", Toast.LENGTH_SHORT).show()
                                bayarVendingMachine()
//                                finish()
                            }else{
                                //salah vending
                                Toast.makeText(this,"Salah scan barcode", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                //action
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun bayarVendingMachine(){
        loading.startLoading()
        var url:String = LinkApi.link_bayar_vending_machine
        var request: RequestQueue = Volley.newRequestQueue(applicationContext)
        var stringRequest = StringRequest(
            Request.Method.GET,url+"?id_user="+ GlobalData.id_user+"&email="+ GlobalData.email_user+"&password="+ GlobalData.password+"&total_harga="+intent.getIntExtra("TOTAL_HARGA",0),
            { response ->
                loading.isDismiss()
                if(response.toString()=="Sukses"){
                    showDialogComplete("Sukses","Selamat menikmati produk kami",R.raw.anim_complete)
                }else{
                    showDialogComplete("Ooops",response.toString(),R.raw.moneyrun)
                }
            },
            { error ->
                Log.d("ErrorApp",error.toString())
                loading.isDismiss()
                finish()
            })
        request.add(stringRequest)
    }
    private fun showDialogComplete(judulnya:String,isinya:String,anims:Int){
        val view = View.inflate(this, R.layout.dialog_anim_ok,null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val dialog = builder.create()

        val judul = view.findViewById<TextView>(R.id.judul)
        val isi = view.findViewById<TextView>(R.id.isi)
        val btn_yes = view.findViewById<Button>(R.id.btn_ok)
        val anim = view.findViewById<LottieAnimationView>(R.id.anim)

        anim.setAnimation(anims)
        anim.loop(true)
        judul.text = judulnya
        isi.text = isinya

        try {
            dialog.show()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            btn_yes.setOnClickListener {
                dialog.dismiss()
                finish()
            }
        }catch (e:java.lang.Exception){

        }

    }
    private fun onAction(){
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
        }
    }
    override fun onResume() {
        super.onResume()

        codeScanner.startPreview()
    }
    private fun checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermissions()
        } else {
            // code for lollipop and pre-lollipop devices
        }
    }
    private fun checkAndRequestPermissions(): Boolean {
        val camera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA)
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray<String>(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }
}