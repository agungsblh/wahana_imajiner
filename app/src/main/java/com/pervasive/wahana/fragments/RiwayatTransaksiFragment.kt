package com.pervasive.wahana.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.FragmentBerandaBinding
import com.pervasive.wahana.databinding.FragmentRiwayatTransaksiBinding
import com.pervasive.wahana.utils.GlobalData


class RiwayatTransaksiFragment : Fragment() {
    private var _binding : FragmentRiwayatTransaksiBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRiwayatTransaksiBinding.inflate(inflater, container, false)


        return binding.root
    }
}