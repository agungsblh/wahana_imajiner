package com.pervasive.wahana.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pervasive.wahana.R
import com.pervasive.wahana.model.MakananModel
import com.pervasive.wahana.model.ProdukVendingModel
import com.pervasive.wahana.utils.Converter
import com.squareup.picasso.Picasso

class ProdukVendingAdapter(private val dataList: List<ProdukVendingModel>, private val addToCartListener: AddToCartListener) : RecyclerView.Adapter<ProdukVendingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listminuman, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)


    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: ProdukVendingModel) {
            // Bind data ke elemen UI dalam tampilan item
            val namaProduk: TextView = itemView.findViewById(R.id.nama_produk)
            val hargaProduk: TextView = itemView.findViewById(R.id.harga)
            val stokProduk: TextView = itemView.findViewById(R.id.stok)
            val gambarProduk: ImageView = itemView.findViewById(R.id.gambar_produk)
            val addButton: ImageView = itemView.findViewById(R.id.bag)
            namaProduk.text = data.nama
            hargaProduk.text = Converter.mataUangRupiah(data.harga)
            stokProduk.text = data.stok.toString()
            Picasso.get().load(data.gambar).into(gambarProduk)
            addButton.setOnClickListener {
                addToCartListener.onAddToCartClicked(data)
                notifyDataSetChanged()
            }


        }
    }
    interface AddToCartListener {
        fun onAddToCartClicked(produk: ProdukVendingModel)

    }
}