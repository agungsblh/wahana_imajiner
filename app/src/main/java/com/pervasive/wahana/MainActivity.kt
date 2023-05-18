package com.pervasive.wahana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pervasive.wahana.activities.ScannerActivity
import com.pervasive.wahana.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragment)
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.berandaFragment
                ||destination.id == R.id.akunFragment) {
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.scanner.visibility = View.VISIBLE
            } else {
                binding.scanner.visibility = View.GONE
                binding.bottomAppBar.visibility = View.GONE
            }
        }
        onAction()
    }
    private fun onAction(){
        binding.apply {
            scanner.setOnClickListener {
                val intent = Intent(this@MainActivity,ScannerActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp()||return super.onSupportNavigateUp()
    }

}