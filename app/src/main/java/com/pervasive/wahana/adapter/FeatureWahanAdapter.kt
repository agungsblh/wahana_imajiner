package com.pervasive.wahana.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pervasive.wahana.databinding.AdapterFeatureWahanaBinding
import com.pervasive.wahana.model.FeatureWahanaModel
import com.squareup.picasso.Picasso

class FeatureWahanAdapter(private var wahanaList:List<FeatureWahanaModel>):RecyclerView.Adapter<FeatureWahanAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: AdapterFeatureWahanaBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AdapterFeatureWahanaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int =wahanaList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val wahana = wahanaList[position]
        holder.binding.apply {
            Picasso.get().load(wahana.image).into(fotoFitur)
            namaFitur.text = wahana.nama
        }
    }
}