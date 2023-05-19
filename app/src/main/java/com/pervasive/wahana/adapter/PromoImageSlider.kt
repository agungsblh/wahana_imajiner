package com.pervasive.wahana.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.pervasive.wahana.R

class PromoImageSlider(private val context : Context, private val ListGambar : Array<Int>): RecyclerView.Adapter<PromoImageSlider.MyViewHolder>() {

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val gambar : ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemgambar,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = ListGambar[position]
        holder.gambar.setImageResource(currentItem)
        holder.gambar.setOnClickListener{
            when(position){
                1->{

                }
                2->{

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return ListGambar.size
    }
}