package com.pervasive.wahana

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.lottie.LottieAnimationView
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
                ||destination.id == R.id.akunFragment
                ||destination.id == R.id.riwayatTransaksiFragment
                ||destination.id == R.id.riwayatTiketFragment) {
                binding.bottomAppBar.visibility = View.VISIBLE
                binding.scanner.visibility = View.VISIBLE
            } else {
                binding.scanner.visibility = View.GONE
                binding.bottomAppBar.visibility = View.GONE
            }
        }
        onAction()
    }

    override fun onDestroy() {
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("Login?",true)
        editor.putString("email",GlobalData.email_user)
        editor.putString("password",GlobalData.password)
        editor.putInt("saldo",GlobalData.saldo)
        editor.apply()
        super.onDestroy()

    }
    private fun onAction(){
        binding.apply {
            scanner.setOnClickListener {
                if (GlobalData.status_available==1){
                    showDialogWarning()
                }else{
                    val intent = Intent(this@MainActivity,ScannerActivity::class.java)
                    startActivity(intent)
                }

            }
        }
    }
    private fun showDialogWarning(){
        val view = View.inflate(this, R.layout.dialog_anim_ok,null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val dialog = builder.create()

        val judul = view.findViewById<TextView>(R.id.judul)
        val isi = view.findViewById<TextView>(R.id.isi)
        val btn_yes = view.findViewById<Button>(R.id.btn_ok)
        val anim = view.findViewById<LottieAnimationView>(R.id.anim)

        anim.setAnimation(R.raw.oops)
        anim.loop(false)
        judul.text = "Ooop"
        isi.text = "Masih ada transaksi lain yang sedang berjalan"

        try {
            dialog.show()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            btn_yes.setOnClickListener {
                dialog.dismiss()
            }
        }catch (e:java.lang.Exception){

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