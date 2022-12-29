package com.example.kotlin_ccavenue_payment_gatway

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_ccavenue_payment_gatway.Utility.AvenuesParams
import com.example.kotlin_ccavenue_payment_gatway.Utility.Constants
import com.example.kotlin_ccavenue_payment_gatway.Utility.ServiceUtility

class MainActivity : AppCompatActivity() {

    var amount: String? = null
    var order_ID:kotlin.String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editamount = findViewById<EditText>(R.id.enter_amount)
        val paybutton = findViewById<Button>(R.id.hellobutton)

        paybutton.setOnClickListener {
            amount = editamount.text.toString()
            val vAccessCode: String = ServiceUtility.chkNull(Constants.access_code).toString().trim()
            Log.d("Access_code Jitendra ", vAccessCode)
            val vMerchantId: String = ServiceUtility.chkNull(Constants.merchantId).toString().trim()
            val vCurrency: String = ServiceUtility.chkNull(Constants.currency).toString().trim()
            val vAmount: String = ServiceUtility.chkNull(amount).toString().trim()

            Log.d("sharmaaa ", vAccessCode)
            if (vAccessCode != "" && vMerchantId != "" && vCurrency != "" && vAmount != "") {
                val intent = Intent(this@MainActivity, WebViewActivity::class.java)
                intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(Constants.access_code).toString().trim())
                intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(Constants.merchantId).toString().trim())
                intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(order_ID).toString().trim())
                intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(Constants.currency).toString().trim())
                intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(amount).toString().trim())
                intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull(Constants.redirectUrl).toString().trim())
                intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull(Constants.cancelUrl).toString().trim())
                intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull(Constants.rsaKeyUrl).toString().trim())
                Log.e("showing...","Tag"+amount)
                startActivity(intent)
                Log.e("showing...","TAG"+vAccessCode)
            } else {
                showToast("All parameters are mandatory.")
            }
        }

    }

    override fun onStart() {
        super.onStart()
        //generating new order number for every transaction
        val randomNum: Int = ServiceUtility.randInt(0, 9999999)
        order_ID = randomNum.toString()
    }

    fun showToast(msg: String) {
        Toast.makeText(this, "Toast: $msg", Toast.LENGTH_LONG).show()
    }
}