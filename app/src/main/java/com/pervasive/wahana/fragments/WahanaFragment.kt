package com.pervasive.wahana.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import com.pervasive.wahana.R
import com.pervasive.wahana.adapter.FeatureWahanAdapter
import com.pervasive.wahana.adapter.WahanaAdapter
import com.pervasive.wahana.databinding.FragmentAkunBinding
import com.pervasive.wahana.databinding.FragmentWahanaBinding
import com.pervasive.wahana.model.FeatureWahanaModel
import com.pervasive.wahana.model.WahanaModel
import java.lang.reflect.Array

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

        val featureWahanaList = ArrayList<FeatureWahanaModel>()
        featureWahanaList.add(FeatureWahanaModel(R.drawable.fitur_rh_hantu,"Hantu menyeramkan"))
        featureWahanaList.add(FeatureWahanaModel(R.drawable.fitur_rh_labu,"Area yang luas"))
        featureWahanaList.add(FeatureWahanaModel(R.drawable.fitur_rh_kereta,"Kereta luas dan nyaman"))
        val adapterFitur = FeatureWahanAdapter(featureWahanaList)
        binding.recycleFitur.adapter = adapterFitur
        binding.recycleFitur.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL,false)
        binding.recycleFitur.adapter = adapterFitur
        binding.apply {
            carouselRecyclerview.adapter = adapter
            carouselRecyclerview.set3DItem(true)
            carouselRecyclerview.setAlpha(true)
            carouselRecyclerview.setInfinite(true)
            wahanaRumahHantu.visibility = View.VISIBLE

            carouselRecyclerview.setItemSelectListener(object : CarouselLayoutManager.OnSelected {
                override fun onItemSelected(position: Int) {
                    //Center item
                    if (position==0){
                        wahanaRumahHantu.visibility = View.VISIBLE
                        wahanaRollerCoaster.visibility = View.GONE
                        wahanaKomediPutar.visibility = View.GONE
                        val featureWahanaList = ArrayList<FeatureWahanaModel>()
                        featureWahanaList.add(FeatureWahanaModel(R.drawable.fitur_rh_hantu,"Hantu menyeramkan"))
                        featureWahanaList.add(FeatureWahanaModel(R.drawable.fitur_rh_labu,"Area yang luas"))
                        featureWahanaList.add(FeatureWahanaModel(R.drawable.fitur_rh_kereta,"Kereta luas dan nyaman"))
                        val adapterFitur = FeatureWahanAdapter(featureWahanaList)
                        binding.recycleFitur.adapter = adapterFitur
                        binding.recycleFitur.layoutManager = LinearLayoutManager(requireContext(),
                            LinearLayoutManager.HORIZONTAL,false)
                        binding.recycleFitur.adapter = adapterFitur
                    }else if(position==1){
                        wahanaRumahHantu.visibility = View.GONE
                        wahanaRollerCoaster.visibility = View.VISIBLE
                        wahanaKomediPutar.visibility = View.GONE
                        val featureWahanaList = ArrayList<FeatureWahanaModel>()
                        featureWahanaList.add(FeatureWahanaModel(R.drawable.whn_rc_01,"Wahana seru"))
                        featureWahanaList.add(FeatureWahanaModel(R.drawable.whn_rc_02,"Menegangkan"))
                        featureWahanaList.add(FeatureWahanaModel(R.drawable.whn_rc_03,"Menguji adrenalin"))
                        val adapterFitur = FeatureWahanAdapter(featureWahanaList)
                        binding.recycleFitur.adapter = adapterFitur
                        binding.recycleFitur.layoutManager = LinearLayoutManager(requireContext(),
                            LinearLayoutManager.HORIZONTAL,false)
                        binding.recycleFitur.adapter = adapterFitur
                    }else if(position==2){
                        wahanaRumahHantu.visibility = View.GONE
                        wahanaRollerCoaster.visibility = View.GONE
                        wahanaKomediPutar.visibility = View.VISIBLE
                        val featureWahanaList = ArrayList<FeatureWahanaModel>()
                        featureWahanaList.add(FeatureWahanaModel(R.drawable.whn_kp_01,"Hantu menyeramkan"))
                        featureWahanaList.add(FeatureWahanaModel(R.drawable.whn_kp_02,"Area yang luas"))
                        featureWahanaList.add(FeatureWahanaModel(R.drawable.whn_kp_03,"Kereta luas dan nyaman"))
                        val adapterFitur = FeatureWahanAdapter(featureWahanaList)
                        binding.recycleFitur.layoutManager = LinearLayoutManager(requireContext(),
                            LinearLayoutManager.HORIZONTAL,false)
                        binding.recycleFitur.adapter = adapterFitur
                    }

                }
            })

        }

        onAction()

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