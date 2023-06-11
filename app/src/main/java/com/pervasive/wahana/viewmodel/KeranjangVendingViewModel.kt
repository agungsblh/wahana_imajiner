package com.pervasive.wahana.viewmodel

import androidx.lifecycle.ViewModel
import com.pervasive.wahana.model.ProdukVendingModel

class KeranjangVendingViewModel:ViewModel() {
    val keranjangList = mutableListOf<ProdukVendingModel>()
}