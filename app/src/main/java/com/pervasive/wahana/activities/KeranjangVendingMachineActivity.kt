package com.pervasive.wahana.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pervasive.wahana.R
import com.pervasive.wahana.adapter.CartVendingAdapter
import com.pervasive.wahana.databinding.ActivityKeranjangVendingMachineBinding
import com.pervasive.wahana.model.ProdukVendingModel
import com.pervasive.wahana.utils.Converter
import com.pervasive.wahana.viewmodel.KeranjangVendingViewModel

class KeranjangVendingMachineActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKeranjangVendingMachineBinding
    private lateinit var keranjangAdapter: CartVendingAdapter

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

        }
    }
    public fun updateTotalHarga(){
        var totalHarga = keranjangAdapter.totalHarga
        binding.totalHarga.text = "Total: "+ Converter.mataUangRupiah(totalHarga)
        var totalItem = keranjangAdapter.totalItem
        binding.totalItem.text = "Total item: "+totalItem

    }



}