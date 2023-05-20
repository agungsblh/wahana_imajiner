package com.pervasive.wahana.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.ActivityRestaurantScanningBinding

class RestaurantScanningActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRestaurantScanningBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantScanningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight=120
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }
}