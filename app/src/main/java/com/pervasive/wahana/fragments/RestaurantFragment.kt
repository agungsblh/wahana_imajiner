package com.pervasive.wahana.fragments

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.pervasive.wahana.R
import com.pervasive.wahana.adapter.TabRestaurantAdapter
import com.pervasive.wahana.databinding.FragmentAkunBinding
import com.pervasive.wahana.databinding.FragmentRestaurantBinding

class RestaurantFragment : Fragment() {
    private var _binding : FragmentRestaurantBinding?= null
    private val binding get() = _binding!!
    var tabTitle = arrayOf("Le Meilleur","Three Square Café")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRestaurantBinding.inflate(inflater, container, false)
        onAction()

        var pager = binding.viewPager2
        var tl = binding.tabLayout
        pager.adapter = TabRestaurantAdapter(requireActivity().supportFragmentManager,lifecycle)

        tl.setSelectedTabIndicatorColor(Color.parseColor("#F6F8FD"))
        tl.setSelectedTabIndicatorHeight(8)
//        tl.setBackgroundColor(Color.parseColor(colors[]))
        TabLayoutMediator(tl,pager){
                tab,position->tab.text = tabTitle[position]
        }.attach()
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