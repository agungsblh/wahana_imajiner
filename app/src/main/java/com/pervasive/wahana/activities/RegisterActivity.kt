package com.pervasive.wahana.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        onAction()
    }
    private fun onAction(){
        binding.apply {
            daftar.setOnClickListener {
                checkField()
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
}