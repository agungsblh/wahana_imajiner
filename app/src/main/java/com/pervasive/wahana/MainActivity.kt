package com.pervasive.wahana

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.pervasive.wahana.activities.ScannerActivity
import com.pervasive.wahana.databinding.ActivityMainBinding
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialog
import com.pervasive.wahana.utils.LoadingDialogFrg

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    val loading = LoadingDialog(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataAkun()
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("Login?",true)
        editor.putString("email",GlobalData.email_user)
        editor.putString("password",GlobalData.password)
        editor.putInt("saldo",GlobalData.saldo)
        editor.apply()

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
    private fun getDataAkun(){
        loading.startLoading()
        var queue: RequestQueue = Volley.newRequestQueue(this)
        var reques = JsonArrayRequest(
            Request.Method.GET, LinkApi.link_get_data_akun+"?email="+GlobalData.email_user+"&password="+GlobalData.password,null,
            { response ->
                loading.isDismiss()
                if(response.length()==0){

                }else{

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

                }
            },
            { error ->
                LoadingDialogFrg.hideLoading()
                Toast.makeText(this,error.toString(), Toast.LENGTH_SHORT).show()
            })
        queue.add(reques)
    }

}