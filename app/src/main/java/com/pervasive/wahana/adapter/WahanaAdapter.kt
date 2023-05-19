package com.pervasive.wahana.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pervasive.wahana.databinding.AdapterWahanaBinding
import com.pervasive.wahana.model.WahanaModel

class WahanaAdapter(private var wahanaList:List<WahanaModel>):RecyclerView.Adapter<WahanaAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: AdapterWahanaBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AdapterWahanaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int =wahanaList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val wahana = wahanaList[position]
        holder.binding.apply {
            Glide.with(wahanaImage).load(wahana.image).into(wahanaImage)
            namaWahana.text = wahana.nama
        }
    }
}