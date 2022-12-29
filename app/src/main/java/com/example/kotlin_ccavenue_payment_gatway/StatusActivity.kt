package com.example.kotlin_ccavenue_payment_gatway

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import de.hdodenhof.circleimageview.CircleImageView

class StatusActivity: AppCompatActivity(){

    var circleImageView: CircleImageView? = null
    var payment_status: String? = null
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_status)
        circleImageView = findViewById(R.id.status_image)
        val mainIntent = intent
        payment_status = mainIntent.getStringExtra("transStatus")
        Log.d("inMain", payment_status!!)
        val tv4 = findViewById<View>(R.id.textView) as TextView
        tv4.text = mainIntent.getStringExtra("transStatus")
    }

    fun showToast(msg: String) {
        Toast.makeText(this, "Toast: $msg", Toast.LENGTH_LONG).show()
    }

}

