package com.pervasive.wahana.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.pervasive.wahana.adapter.MakananAdapter
import com.pervasive.wahana.adapter.ProdukVendingAdapter
import com.pervasive.wahana.databinding.ActivityVendingMachineBinding
import com.pervasive.wahana.model.MakananModel
import com.pervasive.wahana.model.ProdukVendingModel
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialog

class VendingMachine : AppCompatActivity() {

    private lateinit var binding : ActivityVendingMachineBinding
    val loading = LoadingDialog(this)

    var listProduk = ArrayList<ProdukVendingModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendingMachineBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getListProduk()

    }
    private fun getListProduk(){
        loading.startLoading()
        var queue: RequestQueue = Volley.newRequestQueue(this)
        var reques = JsonArrayRequest(
            Request.Method.GET, LinkApi.link_get_produk_vending_machine,null,
            { response ->
                loading.isDismiss()
                if(response.length()==0){

                }else{

                    for (s in 0..response.length()-1){
                        try {
                            val job = response.getJSONObject(s)
                            var id = job.getInt("id")
                            var nama = job.getString("nama")
                            var harga = job.getInt("harga")
                            var gambar = job.getString("gambar")
                            var stok = job.getInt("stok")
                            var jenis = job.getString("jenis")

                            listProduk.add(ProdukVendingModel(id,nama,harga,gambar,stok,jenis))

                        }catch (e:Exception){

                        }

                    }
                    try {
                        var adapterku = ProdukVendingAdapter(listProduk)
                        val gridLayoutManager = GridLayoutManager(this, calculateNoOfColumns())
                        binding.recycleProduk.layoutManager = gridLayoutManager
                        binding.recycleProduk.setHasFixedSize(true)
                        binding.recycleProduk.adapter = adapterku
                        //
                        adapterku.notifyDataSetChanged()
                    }catch (e:Exception){
                        Toast.makeText(this,"Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            { error ->
                loading.isDismiss()
                Toast.makeText(this,error.toString(), Toast.LENGTH_SHORT).show()
            })
        queue.add(reques)
    }
    private fun calculateNoOfColumns(): Int {
        val displayMetrics = resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        val columnWidthDp = 200 // Ukuran kolom yang diinginkan dalam dp
        return (screenWidthDp / columnWidthDp + 0.5).toInt() // Pembulatan ke atas
    }
}