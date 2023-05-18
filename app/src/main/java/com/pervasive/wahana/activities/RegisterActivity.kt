package com.pervasive.wahana.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

            }
            login.setOnClickListener {
                val i = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }
}