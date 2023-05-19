package com.pervasive.wahana.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.FragmentRestoDuaBinding
import com.pervasive.wahana.databinding.FragmentRestoSatuBinding


class RestoDuaFragment : Fragment() {
    private var _binding : FragmentRestoDuaBinding?= null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRestoDuaBinding.inflate(inflater, container, false)


        return binding.root
    }
}