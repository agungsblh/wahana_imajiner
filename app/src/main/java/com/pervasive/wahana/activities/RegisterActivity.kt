package com.pervasive.wahana.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pervasive.wahana.MainActivity
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.ActivityRegisterBinding
import com.pervasive.wahana.model.Penyakit
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialog

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    lateinit var pilihanListPenyakit: ArrayAdapter<CharSequence>
    var listPenyakit = ArrayList<String>()
    var cekPenyakit:Int = -1
    val loading = LoadingDialog(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPenyakit()
        binding.penyakit.setOnItemClickListener { parent, view, position, id ->
            cekPenyakit = position
        }

        onAction()
    }
    private fun onAction(){
        binding.apply {
            daftar.setOnClickListener {
                checkField()
                //Toast.makeText(this@RegisterActivity,cekPenyakit.toString(),Toast.LENGTH_SHORT).show()
            }
            login.setOnClickListener {
                val i = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }
    private fun checkField(){
        binding.apply {
            if (nama.text.toString().isNullOrEmpty()){
                nama.error = "Tidak boleh kosong"
                nama.requestFocus()
                return
            }
            if (nomorHp.text.toString().isNullOrEmpty()){
                nomorHp.error = "Tidak boleh kosong"
                nomorHp.requestFocus()
                return
            }
            if (nomorHp.text.toString().length < 11){
                nomorHp.error = "Tidak valid"
                nomorHp.requestFocus()
                return
            }
            if (emailRegister.text.toString().isNullOrEmpty()){
                emailRegister.error = "Tidak boleh kosong"
                emailRegister.requestFocus()
                return
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(emailRegister.text.toString()).matches()){
                emailRegister.error = "Email tidak valid"
                emailRegister.requestFocus()
                return
            }
            if(password.text.toString().isNullOrEmpty()||password.text.toString().length<8){
                password.error = "Password harus lebih dari 8 karakter"
                password.requestFocus()
                return
            }
            if (konfirmPassword.text.toString() != password.text.toString()){
                konfirmPassword.error = "Kata sandi tidak sama"
                konfirmPassword.requestFocus()
                return
            }

            /////riwayat
            if (penyakit.text.toString().isEmpty()){
                Toast.makeText(this@RegisterActivity,"Penyakit kosong", Toast.LENGTH_SHORT).show()
                penyakit.requestFocus()
                return
            }
            /////end riwayat

            if (beratBadan.text.toString().toInt() < 20){
                beratBadan.error = "Tidak valid"
                beratBadan.requestFocus()
                return
            }
            if (tinggiBadan.text.toString().toInt() < 10){
                tinggiBadan.error = "Tidak valid"
                tinggiBadan.requestFocus()
                return
            }
            register()
        }
    }
    private fun register(){
        loading.startLoading()
        var url:String = LinkApi.link_register_user
        var request: RequestQueue = Volley.newRequestQueue(this)
        var stringRequest = StringRequest(
            Request.Method.GET,url+"?nama="+binding.nama.text.toString()+"&no_hp="+binding.nomorHp.text.toString()+"&email="+binding.emailRegister.text.toString()+"&password="+binding.password.text.toString()+"&id_penyakit="+cekPenyakit.toString()+"&berat_badan="+binding.beratBadan.text.toString()+"&tinggi_badan="+binding.tinggiBadan.text.toString(),
            { response ->
                loading.isDismiss()
                if(response.equals("Register sukses")){
                    try {
                        showDialogComplete()
                    }catch (ex:Exception){

                    }
                }else{
                    Log.d("ERROR",response)
                }
            },
            { error ->
                Log.d("ErrorApp",error.toString())
                loading.isDismiss()
            })
        request.add(stringRequest)
    }
    private fun showDialogComplete(){
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
        judul.text = "Sukses"
        isi.text = "Berhasil mendaftar akun"

        try {
            dialog.show()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            btn_yes.setOnClickListener {
                dialog.dismiss()
                loginAfterRegist()
            }
        }catch (e:java.lang.Exception){

        }

    }
    private fun loginAfterRegist(){
        loading.startLoading()
        var queue: RequestQueue = Volley.newRequestQueue(this)
        var reques = JsonArrayRequest(
            Request.Method.GET, LinkApi.link_get_data_akun+"?email="+binding.emailRegister.text.toString()+"&password="+binding.password.text.toString(),null,
            { response ->
                if(response.length()==0){
                    loading.isDismiss()
                }else{
                    loading.isDismiss()
                    for (s in 0..response.length()-1){
                        val job = response.getJSONObject(s)
                        GlobalData.id_user = job.getInt("id")
                        GlobalData.nama_user = job.getString("nama")
                        GlobalData.email_user = job.getString("email")
                        GlobalData.password= job.getString("password")
                        GlobalData.no_hp= job.getString("no_hp")
                        GlobalData.id_riwayat_penyakit = job.getInt("riwayat_penyakit")
                        GlobalData.tinggi_badan = job.getInt("tinggi_badan")
                        GlobalData.berat_badan = job.getInt("berat_badan")
                        GlobalData.saldo = job.getInt("saldo")
                        GlobalData.detail_riwayat_penyakit = job.getString("nama_penyakit")
                    }
                    try {
                        val i = Intent(this,MainActivity::class.java)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(i)

                    }catch (e:Exception){
                        Toast.makeText(this,"Terjadi kesalahan",Toast.LENGTH_SHORT).show()
                    }
                }
            },
            { error ->
                loading.isDismiss()
                Toast.makeText(this,error.toString(),Toast.LENGTH_SHORT).show()
            })
        queue.add(reques)
    }

    private fun getPenyakit(){
        loading.startLoading()
        var queue: RequestQueue = Volley.newRequestQueue(this)
        var reques = JsonArrayRequest(
            Request.Method.GET, LinkApi.link_get_penyakit,null,
            { response ->
                if(response.length()==0){
                    loading.isDismiss()
                }else{
                    loading.isDismiss()
                    for (s in 0..response.length()-1){
                        val job = response.getJSONObject(s)
                        val id = job.getInt("id")
                        val nama_penyakit = job.getString("nama_penyakit")
                        listPenyakit.add(nama_penyakit)
//                        listPenyakit.add(Penyakit(id,nama_penyakit))
                    }
                    try {
                        val adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1, listPenyakit)
                        binding.penyakit.setAdapter(adapter)
                    }catch (e:Exception){
                        Toast.makeText(this,"Terjadi kesalahan",Toast.LENGTH_SHORT).show()
                    }
                }
            },
            { error ->
                loading.isDismiss()
                Toast.makeText(this,error.toString(),Toast.LENGTH_SHORT).show()
            })
        queue.add(reques)
    }
}