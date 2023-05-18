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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTopupBinding.inflate(inflater, container, false)
        onAction()



        return binding.root
    }
    private fun onAction(){
        binding.apply {

        }
    }
}