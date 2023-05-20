package com.pervasive.wahana.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pervasive.wahana.R
import com.pervasive.wahana.adapter.MakananAdapter
import com.pervasive.wahana.adapter.RiwayatTransaksiAdapter
import com.pervasive.wahana.databinding.ActivityRestaurantScanningBinding
import com.pervasive.wahana.model.MakananModel
import com.pervasive.wahana.model.RiwayatTransaksiModel
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialog
import com.pervasive.wahana.utils.LoadingDialogFrg

class RestaurantScanningActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRestaurantScanningBinding
    var listMakanan = ArrayList<MakananModel>()
    val loading = LoadingDialog(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantScanningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight=120
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        getListMakanan()
    }

    private fun getListMakanan(){
        loading.startLoading()
        var queue: RequestQueue = Volley.newRequestQueue(this)
        var reques = JsonArrayRequest(
            Request.Method.GET, LinkApi.link_get_list_makanan+"?resto="+intent.getStringExtra("resto"),null,
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
                            var stok = job.getInt("stok")
                            var kategori = job.getString("kategori")
                            listMakanan.add(MakananModel(id, nama, harga, stok, kategori))
                        }catch (e:Exception){

                        }

                    }
                    try {
                        var adapterku = MakananAdapter(this,listMakanan)
                        binding.recyclerMenu.layoutManager = LinearLayoutManager(this,
                            LinearLayoutManager.VERTICAL,false)
                        binding.recyclerMenu.setHasFixedSize(true)
                        binding.recyclerMenu.adapter = adapterku
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

}