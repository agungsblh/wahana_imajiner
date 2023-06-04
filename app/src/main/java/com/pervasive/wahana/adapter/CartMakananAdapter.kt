package com.pervasive.wahana.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pervasive.wahana.R
import com.pervasive.wahana.activities.RestaurantScanningActivity
import com.pervasive.wahana.model.CartItem
import com.pervasive.wahana.model.MakananModel
import com.pervasive.wahana.utils.Converter

class CartMakananAdapter(var context:Context,private val makananItems: MutableList<MakananModel>,private val restoActivity: RestaurantScanningActivity):
    RecyclerView.Adapter<CartMakananAdapter.CartViewHolder>() {
    public var totalHarga: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_order, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = makananItems[position]
        holder.productName.text = cartItem.nama
        holder.productPrice.text = Converter.mataUangRupiah(cartItem.harga)
        holder.jumlahItem.text = cartItem.jumlah.toString()
        holder.kurangButton.setOnClickListener {
            if (cartItem.jumlah > 1) {
                cartItem.jumlah--
                updateTotalHarga()
            } else {
                makananItems.removeAt(position)
                updateTotalHarga()
            }
            notifyDataSetChanged()
        }

        holder.tambahButton.setOnClickListener {
            cartItem.jumlah++
            updateTotalHarga()
            notifyDataSetChanged()
        }
    }
    public fun updateTotalHarga() {
        totalHarga = 0
        for (produk in makananItems) {
            totalHarga += produk.harga * produk.jumlah
        }
        restoActivity.updateTotalHarga()
    }
    override fun getItemCount(): Int {
        return makananItems.size
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val jumlahItem:TextView = itemView.findViewById(R.id.jumlah_item)
        val productName: TextView = itemView.findViewById(R.id.nama_menu)
        val productPrice: TextView = itemView.findViewById(R.id.total_harga)
        val kurangButton: FrameLayout = itemView.findViewById(R.id.kurang)
        val tambahButton: FrameLayout = itemView.findViewById(R.id.tambah)
    }

}