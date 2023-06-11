package com.pervasive.wahana.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.pervasive.wahana.R
import com.pervasive.wahana.adapter.RiwayatTiketAdapter
import com.pervasive.wahana.adapter.RiwayatTransaksiAdapter
import com.pervasive.wahana.databinding.FragmentRiwayatTiketBinding
import com.pervasive.wahana.databinding.FragmentRiwayatTransaksiBinding
import com.pervasive.wahana.model.RiwayatTiketModel
import com.pervasive.wahana.model.RiwayatTransaksiModel
import com.pervasive.wahana.utils.GlobalData
import com.pervasive.wahana.utils.LinkApi
import com.pervasive.wahana.utils.LoadingDialogFrg

class RiwayatTiketFragment : Fragment() {
    private var _binding : FragmentRiwayatTiketBinding?= null
    private val binding get() = _binding!!
    var listRiwayatTransaksi = ArrayList<RiwayatTiketModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRiwayatTiketBinding.inflate(inflater, container, false)

        getRiwayatTransaksiData()
        return binding.root
    }

    private fun getRiwayatTransaksiData(){
        LoadingDialogFrg.displayLoadingWithText(requireContext(),false)
        var queue: RequestQueue = Volley.newRequestQueue(requireContext())
        var reques = JsonArrayRequest(
            Request.Method.GET, LinkApi.link_get_list_riwayat_tiket+"?email="+ GlobalData.email_user+"&password="+ GlobalData.password,null,
            { response ->
                LoadingDialogFrg.hideLoading()
                if(response.length()==0){

                }else{

                    for (s in 0..response.length()-1){
                        try {
                            val job = response.getJSONObject(s)
                            var id = job.getInt("id")
                            var id_user = job.getInt("id_user")
                            var no_tiket = job.getString("no_tiket")
                            var harga = job.getInt("harga")
                            var date = job.getString("date")
                            listRiwayatTransaksi.add(RiwayatTiketModel(id, id_user,no_tiket, harga, date))
                        }catch (e:Exception){

                        }

                    }
                    try {
                        var adapterku = RiwayatTiketAdapter(requireContext(),listRiwayatTransaksi)
                        binding.recyclerRiwayatTransaksi.layoutManager = LinearLayoutManager(requireContext(),
                            LinearLayoutManager.VERTICAL,false)
                        binding.recyclerRiwayatTransaksi.setHasFixedSize(true)
                        binding.recyclerRiwayatTransaksi.adapter = adapterku
                        //
                        adapterku.notifyDataSetChanged()
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