package com.pervasive.wahana.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.pervasive.wahana.R
import com.pervasive.wahana.model.RiwayatTiketModel
import com.pervasive.wahana.model.RiwayatTransaksiModel
import com.pervasive.wahana.utils.Converter

class RiwayatTiketAdapter(var context:Context, var list:ArrayList<RiwayatTiketModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val namaTransaksi:TextView = itemView.findViewById(R.id.nama_transaksi)
        val tanggalTransaksi:TextView = itemView.findViewById(R.id.tanggal_transaksi)
        val hargaTransaksi:TextView = itemView.findViewById(R.id.harga)

        fun adapter(nama_transaksi:String,tanggal_transaksi:String,harga_transaksi:Int){
            namaTransaksi.text = nama_transaksi
            tanggalTransaksi.text = tanggal_transaksi
            hargaTransaksi.text = Converter.tigatitikuang(harga_transaksi)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.adapter_lates_transaction,parent,false)
        return RiwayatTiketAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RiwayatTiketAdapter.MyViewHolder).adapter(list[position].no_tiket,list[position].date,list[position].harga)
    }

}