package com.pervasive.wahana.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pervasive.wahana.databinding.FragmentNewBerandaBinding


class NewBerandaFragment : Fragment() {
    private var _binding : FragmentNewBerandaBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNewBerandaBinding.inflate(inflater, container, false)


        return binding.root
    }
}