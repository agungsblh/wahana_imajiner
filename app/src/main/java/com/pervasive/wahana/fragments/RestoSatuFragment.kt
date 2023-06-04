package com.pervasive.wahana.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.FragmentAkunBinding
import com.pervasive.wahana.databinding.FragmentRestoSatuBinding
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialogFrg
import com.squareup.picasso.Picasso


class RestoSatuFragment : Fragment() {
    private var _binding : FragmentRestoSatuBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRestoSatuBinding.inflate(inflater, container, false)

        val handler = Handler()
        val delay = 3000 //milliseconds
        handler.postDelayed(object :Runnable{
            override fun run() {
                getAntrianResto()
                handler.postDelayed(this,delay.toLong())
            }
        },delay.toLong())


        return binding.root
    }
    private fun getAntrianResto(){
        var queue: RequestQueue = Volley.newRequestQueue(requireContext())
        var reques = JsonArrayRequest(
            Request.Method.GET, LinkApi.link_get_antrian_resto+"?id_restaurant="+1,null,
            { response ->
                LoadingDialogFrg.hideLoading()
                if(response.length()==0){
                    Picasso.get().load(R.drawable.restaurant_tersedia).into(binding.meja1)
                    Picasso.get().load(R.drawable.restaurant_tersedia).into(binding.meja2)
                    Picasso.get().load(R.drawable.restaurant_tersedia).into(binding.meja3)
                    Picasso.get().load(R.drawable.restaurant_tersedia).into(binding.meja4)
                    Picasso.get().load(R.drawable.restaurant_tersedia).into(binding.meja5)
                }else{
                    for (s in 0..response.length()-1){
                        val job = response.getJSONObject(s)
                        if(job.getInt("meja")==1){
                            Picasso.get().load(R.drawable.restaurant_tidak_tersedia).into(binding.meja1)
                        }else{
                            Picasso.get().load(R.drawable.restaurant_tersedia).into(binding.meja1)
                        }
                        if(job.getInt("meja")==2){
                            Picasso.get().load(R.drawable.restaurant_tidak_tersedia).into(binding.meja2)
                        }else{
                            Picasso.get().load(R.drawable.restaurant_tersedia).into(binding.meja2)
                        }
                        if(job.getInt("meja")==3){
                            Picasso.get().load(R.drawable.restaurant_tidak_tersedia).into(binding.meja3)
                        }else{
                            Picasso.get().load(R.drawable.restaurant_tersedia).into(binding.meja3)
                        }
                        if(job.getInt("meja")==4){
                            Picasso.get().load(R.drawable.restaurant_tidak_tersedia).into(binding.meja4)
                        }else{
                            Picasso.get().load(R.drawable.restaurant_tersedia).into(binding.meja4)
                        }
                        if(job.getInt("meja")==5){
                            Picasso.get().load(R.drawable.restaurant_tidak_tersedia).into(binding.meja5)
                        }else{
                            Picasso.get().load(R.drawable.restaurant_tersedia).into(binding.meja5)
                        }

                    }
                }
            },
            { error ->
                Toast.makeText(requireContext(),error.toString(), Toast.LENGTH_SHORT).show()
            })
        queue.add(reques)
    }
}