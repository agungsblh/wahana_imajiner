package com.pervasive.wahana.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.FragmentBerandaBinding
import com.pervasive.wahana.utils.GlobalData

class BerandaFragment : Fragment() {
    private var _binding : FragmentBerandaBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        onAction()

        binding.saldo.text = "Rp. "+GlobalData.saldo.toString().reversed().chunked(3).joinToString(".").reversed()

        return binding.root
    }
    private fun onAction(){
        binding.apply {
            topUp.setOnClickListener {
                findNavController().navigate(R.id.action_berandaFragment_to_topupFragment)
            }
        }
    }
    private fun getDataAkun(){

    }
}