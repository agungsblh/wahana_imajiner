package com.pervasive.wahana.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pervasive.wahana.MainActivity
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.ActivitySplashScreenBinding
import com.pervasive.wahana.utils.GlobalData

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        var keepLogin = sharedPreferences.getBoolean("Login?",false)
//        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//        var finishIntro = sharedPreferences.getBoolean("FinishIntro?",false)
        binding.logoapp.alpha = 0f

        binding.logoapp.animate().setDuration(1500).alpha(1f).withEndAction{
            if (keepLogin.equals(true)){
                val i = Intent(this, MainActivity::class.java)
                GlobalData.saldo = sharedPreferences.getInt("saldo",0)
                GlobalData.email_user = sharedPreferences.getString("email","")!!
                GlobalData.password = sharedPreferences.getString("password","")!!
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                finish()
            }else{
                val i = Intent(this, LoginActivity::class.java)
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                finish()
            }



        }
    }
}