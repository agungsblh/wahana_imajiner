package com.pervasive.wahana.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.pervasive.wahana.MainActivity
import com.pervasive.wahana.R
import com.pervasive.wahana.activities.LoginActivity
import com.pervasive.wahana.databinding.FragmentAkunBinding
import com.pervasive.wahana.databinding.FragmentBerandaBinding
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialogFrg


class AkunFragment : Fragment() {
    private var _binding : FragmentAkunBinding?= null
    private val binding get() = _binding!!
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        onAction()

//        sharedPreferences = getS("SHARED_PREF", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        initData()
        getDataAkun()

        return binding.root
    }
    private fun onAction(){
        binding.apply {
            logout.setOnClickListener {
                val i = Intent(requireContext(), LoginActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(i)
            }
        }
    }
    private fun initData(){
        binding.apply {
            namaUser.text = GlobalData.nama_user
            emailUser.text = GlobalData.email_user
            noHp.text = GlobalData.no_hp
            jenisKelamin.text = GlobalData.jenis_kelamin
            riwayatPenyakit.text = GlobalData.detail_riwayat_penyakit
            tinggiBadan.text = GlobalData.tinggi_badan.toString()
            beratBadan.text = GlobalData.berat_badan.toString()
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
                    }
                    try {
                        initData()
                    }catch (e:Exception){
                        Toast.makeText(requireContext(),"Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            { error ->
                LoadingDialogFrg.hideLoading()
                Toast.makeText(requireContext(),error.toString(), Toast.LENGTH_SHORT).show()
            })
        queue.add(reques)
    }

}