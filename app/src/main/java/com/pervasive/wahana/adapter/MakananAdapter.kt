package com.pervasive.wahana.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pervasive.wahana.R
import com.pervasive.wahana.model.MakananModel
import com.pervasive.wahana.model.OrderModel

class MakananAdapter(var context:Context, var list:ArrayList<MakananModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val nama_makanan: TextView = itemView.findViewById(R.id.nama_menu)
        val harga_makanan: TextView = itemView.findViewById(R.id.harga)
        val btn_add: Button = itemView.findViewById(R.id.add)
        fun adapter(namaMananan:String,hargaMakanan:String){
            nama_makanan.text = namaMananan
            harga_makanan.text = "Rp. " + hargaMakanan.reversed().chunked(3).joinToString(".").reversed()
            btn_add.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.adapter_makanan,parent,false)
        return MakananAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MakananAdapter.MyViewHolder).adapter(list[position].nama,list[position].harga.toString())
    }

    private fun addToOrder(makananModel:MakananModel){

    }

}