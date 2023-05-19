package com.pervasive.wahana.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.FragmentAkunBinding
import com.pervasive.wahana.databinding.FragmentRestoSatuBinding


class RestoSatuFragment : Fragment() {
    private var _binding : FragmentRestoSatuBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRestoSatuBinding.inflate(inflater, container, false)


        return binding.root
    }
}