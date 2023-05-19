package com.pervasive.wahana.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pervasive.wahana.R
import com.pervasive.wahana.adapter.PromoImageSlider
import com.pervasive.wahana.databinding.FragmentBerandaBinding
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialogFrg

class BerandaFragment : Fragment() {
    private var _binding : FragmentBerandaBinding?= null
    private val binding get() = _binding!!
    private lateinit var adapter : PromoImageSlider
    private lateinit var handler : Handler
    private lateinit var runnable: Runnable
    private var currentView = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)

        val imageList = arrayOf(
            R.drawable.promo1,
            R.drawable.promo2,
            R.drawable.promo3
        )
        adapter =  PromoImageSlider(requireContext(),imageList)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position ->
            val icon = ImageView(requireContext())
            icon.setImageResource(R.drawable.titikindicator)
            val params = LinearLayout.LayoutParams(20,20)
            icon.layoutParams = params
            tab.customView = icon
            if(position == 2){
                tab.view.isClickable = false
            }
            if(position == 1){
                tab.view.isClickable = false
            }
            if(position == 0){
                val color = ContextCompat.getColor(requireContext(), R.color.hijau)
                icon.setColorFilter(color)
                tab.view.isClickable = false
            }
        }.attach()
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val icon = tab?.customView as ImageView
                val color = ContextCompat.getColor(requireContext(), R.color.hijau)
                icon.setColorFilter(color)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val icon = tab?.customView as ImageView
                icon.setColorFilter(Color.GRAY)
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }
        })
        binding.tabLayout.setSelectedTabIndicatorHeight(0)

        val handlerThread = HandlerThread("ImageSliderPromo")
        handlerThread.start()

        handler = Handler(handlerThread.looper)
        runnable = Runnable {
            if(currentView == imageList.size){
                currentView = 0
            }
            binding.viewPager.currentItem = currentView++
            handler.postDelayed(runnable,4000)
        }
        handler.postDelayed(runnable,4000)
        onAction()

        binding.saldo.text = "Rp. "+GlobalData.saldo.toString().reversed().chunked(3).joinToString(".").reversed()

        return binding.root
    }
    private fun onAction(){
        binding.apply {
            topUp.setOnClickListener {
                findNavController().navigate(R.id.action_berandaFragment_to_topupFragment)
            }
            wahana.setOnClickListener {
                findNavController().navigate(R.id.action_berandaFragment_to_wahanaFragment)
            }
            restaurant.setOnClickListener {
                findNavController().navigate(R.id.action_berandaFragment_to_restaurantFragment)
            }
        }
    }
    private fun getDataAkun(){
        LoadingDialogFrg.displayLoadingWithText(requireContext(),false)
        var queue: RequestQueue = Volley.newRequestQueue(requireContext())
        var reques = JsonArrayRequest(
            Request.Method.GET, LinkApi.link_get_data_akun+"?email="+GlobalData.email_user+"&password="+GlobalData.password,null,
            { response ->
                LoadingDialogFrg.hideLoading()
                if(response.length()==0){
                }else{
                    for (s in 0..response.length()-1){
                        val job = response.getJSONObject(s)
                        GlobalData.id_user = job.getInt("id")
                        GlobalData.nama_user = job.getString("nama")
                        GlobalData.email_user = job.getString("email")
                        GlobalData.password= job.getString("password")
                        GlobalData.no_hp= job.getString("no_hp")
                        GlobalData.id_riwayat_penyakit = job.getInt("riwayat_penyakit")
                        GlobalData.tinggi_badan = job.getInt("tinggi_badan")
                        GlobalData.berat_badan = job.getInt("berat_badan")
                        GlobalData.saldo = job.getInt("saldo")
                        GlobalData.detail_riwayat_penyakit = job.getString("nama_penyakit")
                        binding.saldo.text = "Rp. "+GlobalData.saldo.toString().reversed().chunked(3).joinToString(".").reversed()
                    }
                }
            },
            { error ->
                LoadingDialogFrg.hideLoading()
                Toast.makeText(requireContext(),error.toString(), Toast.LENGTH_SHORT).show()
            })
        queue.add(reques)
    }

    override fun onResume() {
        super.onResume()
        getDataAkun()

    }
}