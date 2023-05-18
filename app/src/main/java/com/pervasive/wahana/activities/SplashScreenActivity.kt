package com.pervasive.wahana.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//        var finishIntro = sharedPreferences.getBoolean("FinishIntro?",false)
        binding.logoapp.alpha = 0f

        binding.logoapp.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()


        }
    }
}