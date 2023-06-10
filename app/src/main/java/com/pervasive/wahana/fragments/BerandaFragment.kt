package com.pervasive.wahana.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pervasive.wahana.R
import com.pervasive.wahana.activities.VendingMachineActivity
import com.pervasive.wahana.activities.WahanaScanningActivity
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
    lateinit var sharedPreferences: SharedPreferences
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

        var currentView = 0
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
            beliTiket.setOnClickListener{
                findNavController().navigate(R.id.action_berandaFragment_to_beliTiketFragment)
            }
            vendingMachine.setOnClickListener{
                val intent = Intent(requireContext(),VendingMachineActivity::class.java)
                startActivity(intent)
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
                        cekStatus()
                    }
                }
            },
            { error ->
                LoadingDialogFrg.hideLoading()
                Toast.makeText(requireContext(),error.toString(), Toast.LENGTH_SHORT).show()
            })
        queue.add(reques)
    }
    private fun cekStatus(){
        var url:String = LinkApi.link_cek_status_user
        var request: RequestQueue = Volley.newRequestQueue(requireContext())
        var stringRequest = StringRequest(
            Request.Method.GET,url+"?email="+GlobalData.email_user+"&password="+GlobalData.password,
            { response ->
                if(response.equals("Menunggu Antrian Wahana")){
                    try {
                        GlobalData.status_available = 1
                        binding.frameStatusUser.visibility = View.VISIBLE
                        binding.status.text = response.toString()
                        binding.imageStatus.setAnimation(R.raw.menunggu_antrian)
                        binding.imageStatus.playAnimation()
                        binding.klikStatus.setOnClickListener {
                            val i = Intent(requireContext(),WahanaScanningActivity::class.java)
                            i.putExtra("state","CHECK")
                            sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
                            var kode_barcode = sharedPreferences.getString("barcode_antrian","-")
                            i.putExtra("KODE",kode_barcode)
                            startActivity(i)
                        }
                    }catch (ex:Exception){

                    }
                }else if(response.equals("Menunggu Pembayaran")){
                    GlobalData.status_available = 1
                    binding.frameStatusUser.visibility = View.VISIBLE
                    binding.status.text = response.toString()
                    binding.imageStatus.setAnimation(R.raw.food_carousel)
                    binding.imageStatus.playAnimation()
                    binding.klikStatus.setOnClickListener {
                        showDialogBayarOrderResto()
                    }

                }else{
                    binding.frameStatusUser.visibility = View.GONE
                }
            },
            { error ->
                Toast.makeText(requireContext(),"Terjadi kesalahan sistem, coba lagi",Toast.LENGTH_SHORT).show()
            })
        request.add(stringRequest)
    }
    private fun showDialogBayarOrderResto(){
        val view = View.inflate(requireContext(), R.layout.dialog_anim_ok,null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        val dialog = builder.create()

        val judul = view.findViewById<TextView>(R.id.judul)
        val isi = view.findViewById<TextView>(R.id.isi)
        val btn_yes = view.findViewById<Button>(R.id.btn_ok)
        val anim = view.findViewById<LottieAnimationView>(R.id.anim)

        anim.setAnimation(R.raw.food_carousel)
        anim.loop(true)
        judul.text = "Bayar"
        isi.text = "Tekan ok untuk membayar tagihan pesanan makanan Anda"

        try {
            dialog.show()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            btn_yes.setOnClickListener {
                dialog.dismiss()
                bayarOrder()

            }
        }catch (e:java.lang.Exception){

        }
    }
    private fun bayarOrder(){
        LoadingDialogFrg.displayLoadingWithText(requireContext(),false)
        var url:String = LinkApi.link_bayar_order_resto
        var request: RequestQueue = Volley.newRequestQueue(requireContext())
        var stringRequest = StringRequest(
            Request.Method.GET,url+"?id_user="+GlobalData.id_user.toString()+"&email="+GlobalData.email_user+"&password="+GlobalData.password,
            { response ->
                LoadingDialogFrg.hideLoading()
                if(response.equals("Sukses")){
                    try {
                        showDialogComplete("Sukses","Saldo kamu telah dikurangi untuk membayar makanan resto",R.raw.anim_complete)
                        getDataAkun()
                        GlobalData.status_available = 0
                    }catch (ex:Exception){

                    }
                }else if(response.equals("Saldo tidak cukup")){
                    showDialogComplete("Saldo Kurang","Saldo kamu tidak cukup untuk melakukan transaksi ini, harap segera isi ulang saldo agar dapat melakukan transaksi",R.raw.oops)
                }else{
                    showDialogComplete(response.toString(),"Terjadi kesalahan sistem, coba lagi",R.raw.oops)
                }
            },
            { error ->
                LoadingDialogFrg.hideLoading()
                showDialogComplete("Error",error.toString(),R.raw.oops)
            })
        request.add(stringRequest)
    }
    private fun showDialogComplete(judulnya:String,isinya:String,loti:Int){
        val view = View.inflate(requireContext(), R.layout.dialog_anim_ok,null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        val dialog = builder.create()

        val judul = view.findViewById<TextView>(R.id.judul)
        val isi = view.findViewById<TextView>(R.id.isi)
        val btn_yes = view.findViewById<Button>(R.id.btn_ok)
        val anim = view.findViewById<LottieAnimationView>(R.id.anim)

        anim.setAnimation(loti)
        anim.loop(false)
        judul.text = judulnya
        isi.text = isinya

        try {
            dialog.show()
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            btn_yes.setOnClickListener {
                dialog.dismiss()
            }
        }catch (e:java.lang.Exception){

        }
    }
    override fun onResume() {
        super.onResume()
        getDataAkun()

    }

    private fun stopRunnable(){
        handler.removeCallbacks(runnable)
    }

    override fun onPause() {
        super.onPause()
        stopRunnable()
    }
}