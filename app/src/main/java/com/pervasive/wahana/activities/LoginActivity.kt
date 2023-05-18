package com.pervasive.wahana.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pervasive.wahana.MainActivity
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        onAction()


    }
    private fun onAction(){
        binding.apply {
            loginMasuk.setOnClickListener {
                val i = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(i)
                finish()
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
}