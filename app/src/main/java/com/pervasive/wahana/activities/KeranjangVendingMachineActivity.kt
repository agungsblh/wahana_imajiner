package com.pervasive.wahana.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pervasive.wahana.R
import com.pervasive.wahana.adapter.CartVendingAdapter
import com.pervasive.wahana.databinding.ActivityKeranjangVendingMachineBinding
import com.pervasive.wahana.model.ProdukVendingModel
import com.pervasive.wahana.utils.Converter
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialog
import com.pervasive.wahana.viewmodel.KeranjangVendingViewModel

class KeranjangVendingMachineActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKeranjangVendingMachineBinding
    private lateinit var keranjangAdapter: CartVendingAdapter
    val loading = LoadingDialog(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeranjangVendingMachineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()
        val bundle = intent.extras
        val keranjangList = bundle?.getParcelableArrayList<ProdukVendingModel>("KERANJANG_LIST")

        if (keranjangList != null && keranjangList.isNotEmpty()) {
            binding.recyclerKeranjangVM.layoutManager = LinearLayoutManager(this)
            keranjangAdapter = CartVendingAdapter(this, keranjangList, this)
            binding.recyclerKeranjangVM.adapter = keranjangAdapter
            keranjangAdapter.notifyDataSetChanged()
            updateTotalHarga()
            keranjangAdapter.updateTotalHarga()
        } else {
            // Tampilkan pesan bahwa keranjang kosong atau tidak ada data
            Toast.makeText(this, "Keranjang kosong", Toast.LENGTH_SHORT).show()
        }


    }

    private fun onAction(){
        binding.apply {
            bayar.setOnClickListener {
                bayarVendingMachine()
            }
            btnBack.setOnClickListener {
                finish()
            }
        }
    }
    private fun bayarVendingMachine(){
        loading.startLoading()
        var url:String = LinkApi.link_bayar_vending_machine
        var request: RequestQueue = Volley.newRequestQueue(applicationContext)
        var stringRequest = StringRequest(
            Request.Method.GET,url+"?id_user="+ GlobalData.id_user+"&email="+GlobalData.email_user+"&password="+GlobalData.password+"&total_harga="+keranjangAdapter.totalHarga,
            { response ->
                loading.isDismiss()
                if(response.toString()=="Sukses"){
                    showDialogComplete("Sukses","Selamat menikmati produk kami",R.raw.anim_complete)
                }else{
                    showDialogComplete("Ooops",response.toString(),R.raw.oops)
                }
            },
            { error ->
                Log.d("ErrorApp",error.toString())
                loading.isDismiss()
                finish()
            })
        request.add(stringRequest)
    }
    private fun showDialogComplete(judulnya:String,isinya:String,anims:Int){
        val view = View.inflate(this, R.layout.dialog_anim_ok,null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val dialog = builder.create()

        val judul = view.findViewById<TextView>(R.id.judul)
        val isi = view.findViewById<TextView>(R.id.isi)
        val btn_yes = view.findViewById<Button>(R.id.btn_ok)
        val anim = view.findViewById<LottieAnimationView>(R.id.anim)

        anim.setAnimation(anims)
        anim.loop(false)
        judul.text = judulnya
        isi.text = isinya

        try {
            dialog.show()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            btn_yes.setOnClickListener {
                dialog.dismiss()
                finish()
            }
        }catch (e:java.lang.Exception){

        }

    }
    public fun updateTotalHarga(){
        var totalHarga = keranjangAdapter.totalHarga
        binding.totalHarga.text = "Total: "+ Converter.mataUangRupiah(totalHarga)
        var totalItem = keranjangAdapter.totalItem
        binding.totalItem.text = "Total item: "+totalItem

    }



}