package com.pervasive.wahana.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.FragmentBerandaBinding
import com.pervasive.wahana.databinding.FragmentTopupBinding


class TopupFragment : Fragment() {
    private var _binding : FragmentTopupBinding?= null
    private val binding get() = _binding!!
    private var tp_lv1 = 0
    private var tp_lv2 = 0
    private var tp_lv3 = 0
    private var tp_lv4 = 0
    private var tp_lv5 = 0
    private var tp_lv6 = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTopupBinding.inflate(inflater, container, false)
        onAction()

        topup_custombox()

        return binding.root
    }
    private fun onAction(){
        binding.apply {
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
    private fun topup_custombox(){
        binding.apply {
            lv1.setOnClickListener {
                tp_lv1 = 1
                tp_lv2 = 0
                tp_lv3 = 0
                tp_lv4 = 0
                tp_lv5 = 0
                tp_lv6 = 0
                lv1.setCardBackgroundColor(resources.getColor(R.color.hijau_soft))
            }
        }
    }
}