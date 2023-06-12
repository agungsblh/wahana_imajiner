package com.pervasive.wahana.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.pervasive.wahana.R
import com.pervasive.wahana.adapter.CartVendingAdapter
import com.pervasive.wahana.adapter.ProdukVendingAdapter
import com.pervasive.wahana.databinding.ActivityVendingMachineBinding
import com.pervasive.wahana.databinding.ActivityVendingMachineMakananBinding
import com.pervasive.wahana.model.ProdukVendingModel
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialog
import java.util.Locale

class VendingMachineMakananActivity : AppCompatActivity(), ProdukVendingAdapter.AddToCartListener {
    private lateinit var binding : ActivityVendingMachineMakananBinding
    val loading = LoadingDialog(this)

    private val listProduk = mutableListOf<ProdukVendingModel>()

    val keranjangList = mutableListOf<ProdukVendingModel>()
    private lateinit var keranjangAdapter: CartVendingAdapter

    private val originalProdukList = mutableListOf<ProdukVendingModel>()
    private val filteredProdukList = mutableListOf<ProdukVendingModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendingMachineMakananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getListProduk()

        keranjangAdapter = CartVendingAdapter(this,keranjangList, KeranjangVendingMachineActivity())

        binding.keranjang.setOnClickListener {
            openKeranjang()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }

        //
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchProduk(newText)
                return true
            }
        })



        val adapterku = ProdukVendingAdapter(filteredProdukList, this)
        // ...
        binding.recycle.adapter = adapterku
        //

    }
    private fun openKeranjang() {
        val intent = Intent(this, KeranjangVendingMachineActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelableArrayList("KERANJANG_LIST", ArrayList(keranjangList))
        intent.putExtra("VENDING","VMC-001-MK")
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun getListProduk(){
        loading.startLoading()
        var queue: RequestQueue = Volley.newRequestQueue(this)
        var reques = JsonArrayRequest(
            Request.Method.GET, LinkApi.link_get_produk_vending_machine_makanan,null,
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
                        var adapterku = ProdukVendingAdapter(listProduk,this)
                        val gridLayoutManager = GridLayoutManager(this, calculateNoOfColumns())
                        binding.recycle.layoutManager = gridLayoutManager
                        binding.recycle.setHasFixedSize(true)
                        binding.recycle.adapter = adapterku

                        //
                        originalProdukList.addAll(listProduk)
                        filteredProdukList.addAll(listProduk)


                        val adapterkuu = ProdukVendingAdapter(filteredProdukList, this)
                        // ...
                        binding.recycle.adapter = adapterkuu
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
    private fun searchProduk(query: String) {
        val keyword = query.trim().toLowerCase(Locale.getDefault())
        filteredProdukList.clear()
        filteredProdukList.addAll(originalProdukList.filter {
            it.nama.toLowerCase(Locale.getDefault()).contains(keyword)
        })
        binding.recycle.adapter?.notifyDataSetChanged()
    }

    private fun calculateNoOfColumns(): Int {
        val displayMetrics = resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        val columnWidthDp = 200 // Ukuran kolom yang diinginkan dalam dp
        return (screenWidthDp / columnWidthDp + 0.5).toInt() // Pembulatan ke atas
    }

    override fun onAddToCartClicked(product: ProdukVendingModel) {

        Toast.makeText(this,"Menambahkan " +product.nama, Toast.LENGTH_SHORT).show()

        // Cek apakah produk sudah ada dalam keranjang
        val existingProduk = keranjangList.find { it.nama == product.nama }

        if (existingProduk != null) {
            // Jika produk sudah ada, tingkatkan jumlahnya
            existingProduk.jumlah++
            keranjangAdapter.notifyDataSetChanged()
//            updateTotalHarga()
        } else {
            // Jika produk belum ada, tambahkan ke dalam keranjang
            keranjangList.add(product)

            keranjangAdapter.notifyDataSetChanged()
//            updateTotalHarga()
        }
        if (keranjangList.size!=0){
            binding.indikator.visibility = View.VISIBLE
        }else{
            binding.indikator.visibility = View.GONE
        }

    }
}