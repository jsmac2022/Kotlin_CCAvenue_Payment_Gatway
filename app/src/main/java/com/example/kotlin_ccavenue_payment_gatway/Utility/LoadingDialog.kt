package com.example.kotlin_ccavenue_payment_gatway.Utility

import android.app.ProgressDialog
import android.content.Context

object LoadingDialog {
    var progressDialog: ProgressDialog? = null
    fun showLoadingDialog(context: Context?, message: String?) {
        if (!(progressDialog != null && progressDialog!!.isShowing)) {
            progressDialog = ProgressDialog(context)
            progressDialog!!.setMessage(message)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
            progressDialog!!.show()
        }
    }

    fun cancelLoading() {
        if (progressDialog != null && progressDialog!!.isShowing) progressDialog!!.cancel()
    }
}