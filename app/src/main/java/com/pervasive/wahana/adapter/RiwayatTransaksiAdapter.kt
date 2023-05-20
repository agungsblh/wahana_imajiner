package com.pervasive.wahana.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.pervasive.wahana.R
import com.pervasive.wahana.model.RiwayatTransaksiModel
class RiwayatTransaksiAdapter(var context:Context, var list:ArrayList<RiwayatTransaksiModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val fotos: ShapeableImageView = itemView.findViewById(R.id.img)
        val deskripsis: TextView = itemView.findViewById(R.id.deskripsi)
        fun adapter(deskripsi:String){
            deskripsis.text = deskripsi
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.adapter_riwayat_transaksi,parent,false)
        return RiwayatTransaksiAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RiwayatTransaksiAdapter.MyViewHolder).adapter(list[position].deskripsi)
    }

}