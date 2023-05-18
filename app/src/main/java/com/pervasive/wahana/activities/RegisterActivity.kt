package com.pervasive.wahana.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.pervasive.wahana.databinding.ActivityRegisterBinding
import com.pervasive.wahana.model.Penyakit
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
        }
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