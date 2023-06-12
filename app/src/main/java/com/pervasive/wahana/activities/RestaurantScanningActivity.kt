package com.pervasive.wahana.activities

import android.app.AlertDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pervasive.wahana.R
import com.pervasive.wahana.adapter.CartMakananAdapter
import com.pervasive.wahana.adapter.MakananAdapter
import com.pervasive.wahana.adapter.ProdukVendingAdapter
import com.pervasive.wahana.adapter.RiwayatTransaksiAdapter
import com.pervasive.wahana.databinding.ActivityRestaurantScanningBinding
import com.pervasive.wahana.model.CartItem
import com.pervasive.wahana.model.MakananModel
import com.pervasive.wahana.model.ProdukVendingModel
import com.pervasive.wahana.model.RiwayatTransaksiModel
import com.pervasive.wahana.utils.Converter
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialog
import com.pervasive.wahana.utils.LoadingDialogFrg
import java.util.Locale

class RestaurantScanningActivity : AppCompatActivity(),MakananAdapter.AddToCartListener {
    private lateinit var binding : ActivityRestaurantScanningBinding
    var listMakanan = mutableListOf<MakananModel>()
    val loading = LoadingDialog(this)
    private val keranjangList = mutableListOf<MakananModel>()
    private lateinit var keranjangAdapter: CartMakananAdapter
    //private lateinit var keranjangLainAdapter: CartMakananAdapter
    private val originalProdukList = mutableListOf<MakananModel>()
    private val filteredProdukList = mutableListOf<MakananModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantScanningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var resto = intent.getIntExtra("resto",1)
        var meja = intent.getIntExtra("meja",1)
        if(resto==1){
            binding.namaResto.text = resources.getString(R.string.nama_resto_1)
        }else{
            binding.namaResto.text = resources.getString(R.string.nama_resto_2)
        }
        onAction(resto,meja)

        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight=120
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        getListMakanan()

        val recyclerViewKeranjang: RecyclerView = findViewById(R.id.recyclerCart)
        recyclerViewKeranjang.layoutManager = LinearLayoutManager(this)
        keranjangAdapter = CartMakananAdapter(this,keranjangList,this)
        recyclerViewKeranjang.adapter = keranjangAdapter


        //
        binding.search.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchProduk(newText)
                return true
            }
        })

        val adapterku = MakananAdapter(this,this,filteredProdukList, this,keranjangAdapter)
        // ...
        binding.recyclerMenu.adapter = adapterku
        //

    }

    private fun onAction(id_resto:Int,id_meja:Int){
        binding.apply {
            bayar.setOnClickListener {
                if (keranjangAdapter.totalHarga==0){
                    Toast.makeText(this@RestaurantScanningActivity,"Belum ada menu yang dipilih",Toast.LENGTH_SHORT).show()
                }else{
                    createPesanan(id_resto,id_meja,keranjangAdapter.totalHarga)
                }
            }
        }
    }
    private fun createPesanan(id_resto:Int,id_meja:Int,total:Int){
        loading.startLoading()
        var url:String = LinkApi.link_create_orderan_resto
        var request: RequestQueue = Volley.newRequestQueue(applicationContext)
        var stringRequest = StringRequest(
            Request.Method.GET,url+"?id_user="+GlobalData.id_user+"&id_restaurant="+id_resto+"&meja="+id_meja+"&total="+total,
            { response ->
                loading.isDismiss()
                if(response.toString()=="Sukses"){
                    showDialogComplete("Sukses","Pesanan berhasil dibuat, selamat menikmati makanan kami",R.raw.anim_complete)
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
    private fun searchProduk(query: String) {
        val keyword = query.trim().toLowerCase(Locale.getDefault())
        filteredProdukList.clear()
        filteredProdukList.addAll(originalProdukList.filter {
            it.nama.toLowerCase(Locale.getDefault()).contains(keyword)
        })
        binding.recyclerMenu.adapter?.notifyDataSetChanged()
    }
    private fun getListMakanan(){
        loading.startLoading()
        var queue: RequestQueue = Volley.newRequestQueue(this)
        var reques = JsonArrayRequest(
            Request.Method.GET, LinkApi.link_get_list_makanan+"?resto="+intent.getIntExtra("resto",1),null,
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
                        var adapterku = MakananAdapter(this,this,listMakanan,this,keranjangAdapter)
                        binding.recyclerMenu.layoutManager = LinearLayoutManager(this,
                            LinearLayoutManager.VERTICAL,false)
                        binding.recyclerMenu.setHasFixedSize(true)
                        binding.recyclerMenu.adapter = adapterku
                        //
                        //
                        originalProdukList.addAll(listMakanan)
                        filteredProdukList.addAll(listMakanan)


                        val adapterkuu = MakananAdapter(this,this,filteredProdukList, this,keranjangAdapter)
                        // ...
                        binding.recyclerMenu.adapter = adapterkuu
                        //
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
    override fun onAddToCartClicked(product: MakananModel) {

        Toast.makeText(this,"Menambahkah " +product.nama,Toast.LENGTH_SHORT).show()

        // Cek apakah produk sudah ada dalam keranjang
        val existingProduk = keranjangList.find { it.nama == product.nama }

        if (existingProduk != null) {
            // Jika produk sudah ada, tingkatkan jumlahnya
            existingProduk.jumlah++
            keranjangAdapter.notifyDataSetChanged()
            updateTotalHarga()
        } else {
            // Jika produk belum ada, tambahkan ke dalam keranjang
            keranjangList.add(product)
            keranjangAdapter.notifyDataSetChanged()
            updateTotalHarga()
        }


        // Refresh RecyclerView keranjang
        //keranjangAdapter.notifyDataSetChanged()


    }
    public fun updateTotalHarga(){
        var totalHarga = keranjangAdapter.totalHarga
        binding.total.text = "Total: "+Converter.mataUangRupiah(totalHarga)


    }
}