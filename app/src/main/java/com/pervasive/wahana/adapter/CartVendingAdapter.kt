package com.pervasive.wahana.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pervasive.wahana.R
import com.pervasive.wahana.activities.KeranjangVendingMachineActivity
import com.pervasive.wahana.model.ProdukVendingModel
import com.pervasive.wahana.utils.Converter
import com.squareup.picasso.Picasso

class CartVendingAdapter(var context:Context, private val keranjangList: MutableList<ProdukVendingModel>,private val vmActivity: KeranjangVendingMachineActivity):
    RecyclerView.Adapter<CartVendingAdapter.CartViewHolder>() {
    public var totalHarga: Int = 0
    public var totalItem: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_keranjang_vm, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = keranjangList[position]
        holder.namaProduk.text = cartItem.nama
        holder.hargaProduk.text = Converter.mataUangRupiah(cartItem.harga)
        holder.jumlahItem.text = cartItem.jumlah.toString()
        Picasso.get().load(cartItem.gambar).into(holder.gambarProduk)
        holder.kurangButton.setOnClickListener {
            if (cartItem.jumlah > 1) {
                cartItem.jumlah--
                updateTotalHarga()
            } else {
                keranjangList.removeAt(position)
                updateTotalHarga()
            }
            notifyDataSetChanged()
        }

        holder.tambahButton.setOnClickListener {
            cartItem.jumlah++
            updateTotalHarga()
            notifyDataSetChanged()
        }
        holder.hapusButtom.setOnClickListener {
            keranjangList.removeAt(position)
            updateTotalHarga()
            notifyDataSetChanged()
        }
    }
    public fun updateTotalHarga() {
        totalHarga = 0
        totalItem = 0
        for (produk in keranjangList) {
            totalHarga += produk.harga * produk.jumlah
            totalItem+= produk.jumlah
        }
        vmActivity.updateTotalHarga()
    }
    override fun getItemCount(): Int {
        return keranjangList.size
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gambarProduk:ImageView = itemView.findViewById(R.id.gambar_produk)
        val namaProduk: TextView = itemView.findViewById(R.id.nama_produk)
        val jumlahItem:TextView = itemView.findViewById(R.id.jumlah_item)
        val hargaProduk: TextView = itemView.findViewById(R.id.harga_produk)
        val kurangButton: FrameLayout = itemView.findViewById(R.id.kurang)
        val tambahButton: FrameLayout = itemView.findViewById(R.id.tambah)
        val hapusButtom:LinearLayout = itemView.findViewById(R.id.hapus)
    }


}