package com.pervasive.wahana.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pervasive.wahana.MainActivity
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.ActivityLoginBinding
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialog

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    val loading = LoadingDialog(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        onAction()
    }
    private fun onAction(){
        binding.apply {
            loginMasuk.setOnClickListener {
                checkField()
            }
            lupaSandi.setOnClickListener {

            }
            buatakun.setOnClickListener {
                val i = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }
    private fun checkField(){
        binding.apply {
            if (loginEmail.text.toString().isNullOrEmpty()){
                loginEmail.error = "Tidak boleh kosong"
                loginEmail.requestFocus()
                return
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(loginEmail.text.toString()).matches()){
                loginEmail.error = "Email tidak valid"
                loginEmail.requestFocus()
                return
            }
            if(loginPassword.text.toString().isNullOrEmpty()||loginPassword.text.toString().length<8){
                loginPassword.error = "Password harus lebih dari 8 karakter"
                loginPassword.requestFocus()
                return
            }

            login()
        }
    }
    private fun login(){
        loading.startLoading()
        var url:String = LinkApi.link_login
        var request: RequestQueue = Volley.newRequestQueue(applicationContext)
        var stringRequest = StringRequest(
            Request.Method.GET,url+"?email="+binding.loginEmail.text.toString()+"&password="+binding.loginPassword.text.toString(),
            { response ->
                loading.isDismiss()
                if(response.equals("Selamat datang")){
                    try {
                        getApiAkun()
                    }catch (ex:Exception){

                    }

                }else{

                }
            },
            { error ->
                Log.d("ErrorApp",error.toString())
                loading.isDismiss()

            })
        request.add(stringRequest)
    }
    private fun getApiAkun(){
        loading.startLoading()
        var queue: RequestQueue = Volley.newRequestQueue(this)
        var reques = JsonArrayRequest(
            Request.Method.GET, LinkApi.link_get_data_akun+"?email="+binding.loginEmail.text.toString()+"&password="+binding.loginPassword.text.toString(),null,
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
                        Toast.makeText(this,"Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            { error ->
                loading.isDismiss()
                Toast.makeText(this,error.toString(), Toast.LENGTH_SHORT).show()
            })
        queue.add(reques)
    }
}