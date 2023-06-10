package com.pervasive.wahana.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.FragmentKeranjangVendingMachineBinding
import com.pervasive.wahana.databinding.FragmentTopupBinding
import com.pervasive.wahana.model.ProdukVendingModel


class KeranjangVendingMachineFragment : Fragment() {
    private var _binding : FragmentKeranjangVendingMachineBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentKeranjangVendingMachineBinding.inflate(inflater, container, false)

        onAction()


        return binding.root
    }

    private fun onAction(){
        binding.apply {

        }
    }
}