package com.pervasive.wahana.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.pervasive.wahana.R
import com.pervasive.wahana.adapter.CartVendingAdapter
import com.pervasive.wahana.databinding.ActivityKeranjangVendingMachineBinding
import com.pervasive.wahana.model.ProdukVendingModel

class KeranjangVendingMachineActivity : AppCompatActivity() {
    private lateinit var binding : ActivityKeranjangVendingMachineBinding
    private lateinit var adapter: CartVendingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKeranjangVendingMachineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onAction()
        val bundle = intent.extras
        val keranjangList = bundle?.getParcelableArrayList<ProdukVendingModel>("KERANJANG_LIST")

        binding.recyclerKeranjangVM.layoutManager = LinearLayoutManager(this)
        adapter = CartVendingAdapter(this, keranjangList!!)
        binding.recyclerKeranjangVM.adapter = adapter


    }

    private fun onAction(){
        binding.apply {

        }
    }


}