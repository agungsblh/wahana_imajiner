package com.pervasive.wahana.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pervasive.wahana.R
import com.pervasive.wahana.databinding.FragmentBerandaBinding
import com.pervasive.wahana.databinding.FragmentTopupBinding
import com.pervasive.wahana.utils.Converter
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialogFrg
import java.util.Date


class TopupFragment : Fragment() {
    private var _binding : FragmentTopupBinding?= null
    private val binding get() = _binding!!
    private var tp_lv1 = 0
    private var tp_lv2 = 0
    private var tp_lv3 = 0
    private var tp_lv4 = 0
    private var tp_lv5 = 0
    private var tp_lv6 = 0
    private var pilihan_topup = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTopupBinding.inflate(inflater, container, false)
        onAction()

        topup_custombox()

        return binding.root
    }
    private fun onAction(){
        binding.apply {
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            lanjut.setOnClickListener {
                showDialogChoice()
            }
        }
    }
    private fun showDialogChoice(){
        val view = View.inflate(requireContext(), R.layout.dialog_anim_ok_no,null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        val dialog = builder.create()

        val judul = view.findViewById<TextView>(R.id.judul)
        val isi = view.findViewById<TextView>(R.id.isi)
        val btn_yes = view.findViewById<Button>(R.id.yes)
        val btn_no = view.findViewById<Button>(R.id.no)

        judul.text = "Topup"
        isi.text = "Apakah Anda yakin pilihannya sudah benar?"

        dialog.show()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        btn_yes.setOnClickListener {
            //kirim data ke db
            dialog.dismiss()
            topup()
            //wait for verif
        }
        btn_no.setOnClickListener {
            dialog.dismiss()
        }
    }
    private fun showDialogComplete(judulnya:String,isinya:String){
        val view = View.inflate(requireContext(), R.layout.dialog_anim_ok,null)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        val dialog = builder.create()

        val judul = view.findViewById<TextView>(R.id.judul)
        val isi = view.findViewById<TextView>(R.id.isi)
        val btn_yes = view.findViewById<Button>(R.id.btn_ok)
        val anim = view.findViewById<LottieAnimationView>(R.id.anim)

        anim.setAnimation(R.raw.anim_complete)
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
    private fun topup(){
        if (tp_lv1==1){
            pilihan_topup = 1
        }else if(tp_lv2==1){
            pilihan_topup = 2
        }else if(tp_lv3==1){
            pilihan_topup = 3
        }else if(tp_lv4==1){
            pilihan_topup = 4
        }else if(tp_lv5==1){
            pilihan_topup = 5
        }else{
            pilihan_topup = 6
        }
        var tanggal =Converter.timeInMillisToDate(System.currentTimeMillis())
        LoadingDialogFrg.displayLoadingWithText(requireContext(),false)
        var url:String = LinkApi.link_topup
        var request: RequestQueue = Volley.newRequestQueue(requireContext())
        var stringRequest = StringRequest(
            Request.Method.GET,url+"?pilihan_topup="+pilihan_topup.toString()+"&email="+GlobalData.email_user+"&password="+GlobalData.password+"&date="+tanggal,
            { response ->
                LoadingDialogFrg.hideLoading()
                if(response.equals("Topup sukses")){
                    try {
                        showDialogComplete("Top up sukses","Saldo kamu telah bertambah sesuai jumlah topup")
                        getDataAkun()
                    }catch (ex:Exception){

                    }

                }else{
                    showDialogComplete(response.toString(),"Terjadi kesalahan sistem, coba lagi")
                }
            },
            { error ->
                LoadingDialogFrg.hideLoading()
                showDialogComplete("Error",error.toString())
            })
        request.add(stringRequest)
    }
    private fun topup_custombox(){
        binding.apply {
            lv1.setOnClickListener {
                tp_lv1 = 1
                tp_lv2 = 0
                tp_lv3 = 0
                tp_lv4 = 0
                tp_lv5 = 0
                tp_lv6 = 0
                lv1.setCardBackgroundColor(resources.getColor(R.color.hijau_soft))
                lv2.setCardBackgroundColor(resources.getColor(R.color.white))
                lv3.setCardBackgroundColor(resources.getColor(R.color.white))
                lv4.setCardBackgroundColor(resources.getColor(R.color.white))
                lv5.setCardBackgroundColor(resources.getColor(R.color.white))
                lv6.setCardBackgroundColor(resources.getColor(R.color.white))
            }
            lv2.setOnClickListener {
                tp_lv1 = 0
                tp_lv2 = 1
                tp_lv3 = 0
                tp_lv4 = 0
                tp_lv5 = 0
                tp_lv6 = 0
                lv1.setCardBackgroundColor(resources.getColor(R.color.white))
                lv2.setCardBackgroundColor(resources.getColor(R.color.hijau_soft))
                lv3.setCardBackgroundColor(resources.getColor(R.color.white))
                lv4.setCardBackgroundColor(resources.getColor(R.color.white))
                lv5.setCardBackgroundColor(resources.getColor(R.color.white))
                lv6.setCardBackgroundColor(resources.getColor(R.color.white))
            }
            lv3.setOnClickListener {
                tp_lv1 = 0
                tp_lv2 = 0
                tp_lv3 = 1
                tp_lv4 = 0
                tp_lv5 = 0
                tp_lv6 = 0
                lv1.setCardBackgroundColor(resources.getColor(R.color.white))
                lv2.setCardBackgroundColor(resources.getColor(R.color.white))
                lv3.setCardBackgroundColor(resources.getColor(R.color.hijau_soft))
                lv4.setCardBackgroundColor(resources.getColor(R.color.white))
                lv5.setCardBackgroundColor(resources.getColor(R.color.white))
                lv6.setCardBackgroundColor(resources.getColor(R.color.white))
            }
            lv4.setOnClickListener {
                tp_lv1 = 0
                tp_lv2 = 0
                tp_lv3 = 0
                tp_lv4 = 1
                tp_lv5 = 0
                tp_lv6 = 0
                lv1.setCardBackgroundColor(resources.getColor(R.color.white))
                lv2.setCardBackgroundColor(resources.getColor(R.color.white))
                lv3.setCardBackgroundColor(resources.getColor(R.color.white))
                lv4.setCardBackgroundColor(resources.getColor(R.color.hijau_soft))
                lv5.setCardBackgroundColor(resources.getColor(R.color.white))
                lv6.setCardBackgroundColor(resources.getColor(R.color.white))
            }
            lv5.setOnClickListener {
                tp_lv1 = 0
                tp_lv2 = 0
                tp_lv3 = 0
                tp_lv4 = 0
                tp_lv5 = 1
                tp_lv6 = 0
                lv1.setCardBackgroundColor(resources.getColor(R.color.white))
                lv2.setCardBackgroundColor(resources.getColor(R.color.white))
                lv3.setCardBackgroundColor(resources.getColor(R.color.white))
                lv4.setCardBackgroundColor(resources.getColor(R.color.white))
                lv5.setCardBackgroundColor(resources.getColor(R.color.hijau_soft))
                lv6.setCardBackgroundColor(resources.getColor(R.color.white))
            }
            lv6.setOnClickListener {
                tp_lv1 = 0
                tp_lv2 = 0
                tp_lv3 = 0
                tp_lv4 = 0
                tp_lv5 = 0
                tp_lv6 = 1
                lv1.setCardBackgroundColor(resources.getColor(R.color.white))
                lv2.setCardBackgroundColor(resources.getColor(R.color.white))
                lv3.setCardBackgroundColor(resources.getColor(R.color.white))
                lv4.setCardBackgroundColor(resources.getColor(R.color.white))
                lv5.setCardBackgroundColor(resources.getColor(R.color.white))
                lv6.setCardBackgroundColor(resources.getColor(R.color.hijau_soft))
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