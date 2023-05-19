package com.pervasive.wahana.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pervasive.wahana.databinding.ActivityVendingMachineBinding

class VendingMachine : AppCompatActivity() {

    private lateinit var binding : ActivityVendingMachineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendingMachineBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}