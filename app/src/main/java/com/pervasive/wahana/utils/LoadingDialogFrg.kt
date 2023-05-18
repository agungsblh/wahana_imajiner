package com.pervasive.wahana.utils

import android.app.Dialog
import android.content.Context
import com.pervasive.wahana.R

object LoadingDialogFrg {
    var dialog: Dialog? = null
    fun displayLoadingWithText(context: Context?, cancelable: Boolean) {
        dialog = Dialog(context!!)
        //dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.loading_layout)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setCancelable(cancelable)
        try {
            dialog!!.show()
        } catch (e: Exception) {
        }
    }

    fun hideLoading() {
        try {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }
}