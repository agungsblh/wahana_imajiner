package com.pervasive.wahana.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.FragmentAkunBinding
import com.pervasive.wahana.databinding.FragmentBerandaBinding


class AkunFragment : Fragment() {
    private var _binding : FragmentAkunBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        onAction()



        return binding.root
    }
    private fun onAction(){
        binding.apply {

        }
    }
}