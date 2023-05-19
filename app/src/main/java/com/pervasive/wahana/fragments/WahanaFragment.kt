package com.pervasive.wahana.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pervasive.wahana.R
import com.pervasive.wahana.adapter.WahanaAdapter
import com.pervasive.wahana.databinding.FragmentAkunBinding
import com.pervasive.wahana.databinding.FragmentWahanaBinding
import com.pervasive.wahana.model.WahanaModel

class WahanaFragment : Fragment() {
    private var _binding : FragmentWahanaBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentWahanaBinding.inflate(inflater, container, false)


        val wahanaList = ArrayList<WahanaModel>()
        wahanaList.add(WahanaModel(R.drawable.wahana_rumah_hantu,"Rumah Hantu","blablablasbalsals"))
        wahanaList.add(WahanaModel(R.drawable.wahana_roller_coaster,"Roller Coaster","blablablasbalsals"))
        wahanaList.add(WahanaModel(R.drawable.wahana_komedi_putar,"Komedi Putar","blablablasbalsals"))
        val adapter = WahanaAdapter(wahanaList)

        binding.apply {
            carouselRecyclerview.adapter = adapter
            carouselRecyclerview.set3DItem(true)
            carouselRecyclerview.setAlpha(true)
            carouselRecyclerview.setInfinite(true)

        }


        return binding.root
    }
    private fun onAction(){
        binding.apply {
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}