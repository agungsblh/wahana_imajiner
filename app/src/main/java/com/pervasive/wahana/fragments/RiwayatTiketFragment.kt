package com.pervasive.wahana.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.FragmentRiwayatTiketBinding
import com.pervasive.wahana.databinding.FragmentRiwayatTransaksiBinding

class RiwayatTiketFragment : Fragment() {
    private var _binding : FragmentRiwayatTiketBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRiwayatTiketBinding.inflate(inflater, container, false)


        return binding.root
    }
}