package com.pervasive.wahana.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


class ProdukVendingModel(
    var id: Int,
    var nama: String,
    var harga: Int,
    var gambar: String,
    var stok: Int,
    var jenis: String,
    var jumlah: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!! ,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nama)
        parcel.writeInt(harga)
        parcel.writeString(gambar)
        parcel.writeInt(stok)
        parcel.writeString(jenis)
        parcel.writeInt(jumlah)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProdukVendingModel> {
        override fun createFromParcel(parcel: Parcel): ProdukVendingModel {
            return ProdukVendingModel(parcel)
        }

        override fun newArray(size: Int): Array<ProdukVendingModel?> {
            return arrayOfNulls(size)
        }
    }
}