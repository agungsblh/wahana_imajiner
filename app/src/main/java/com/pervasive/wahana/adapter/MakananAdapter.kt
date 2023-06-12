package com.pervasive.wahana.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pervasive.wahana.R
import com.pervasive.wahana.activities.RestaurantScanningActivity
import com.pervasive.wahana.model.MakananModel
import com.pervasive.wahana.model.OrderModel
import com.pervasive.wahana.utils.Converter

class MakananAdapter(var context:Context, private val restoActivity: RestaurantScanningActivity, var list:List<MakananModel>, private val addToCartListener: AddToCartListener,private val keranjangLainAdapter: CartMakananAdapter):RecyclerView.Adapter<MakananAdapter.MakananViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakananViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_makanan, parent, false)
        return MakananViewHolder(view)
    }
    inner class MakananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btn_add: Button = itemView.findViewById(R.id.add)
        val nama_makanan: TextView = itemView.findViewById(R.id.nama_menu)
        val harga_makanan: TextView = itemView.findViewById(R.id.harga)
    }

    override fun onBindViewHolder(holder: MakananViewHolder, position: Int) {
        val product = list[position]
        holder.nama_makanan.text = product.nama
        holder.harga_makanan.text = Converter.mataUangRupiah(product.harga)
        holder.btn_add.setOnClickListener {
            addToCartListener.onAddToCartClicked(product)
            notifyDataSetChanged()
            restoActivity.updateTotalHarga()
            panggilFungsiAdapterLain()

        }
    }
    private fun panggilFungsiAdapterLain() {
        keranjangLainAdapter.updateTotalHarga()
    }
    override fun getItemCount(): Int = list.size

    interface AddToCartListener {
        fun onAddToCartClicked(makanan: MakananModel)

    }

}