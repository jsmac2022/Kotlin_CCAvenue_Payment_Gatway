package com.example.kotlin_ccavenue_payment_gatway

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotlin_ccavenue_payment_gatway.Utility.*
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


class WebViewActivity : AppCompatActivity() {
    var mainIntent: Intent? = null
    var encVal: String? = null
    var vResponse: String? = null

    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_webview)
        mainIntent = intent

        //get rsa key method
        get_RSA_key(
            mainIntent!!.getStringExtra(AvenuesParams.ACCESS_CODE),
            mainIntent!!.getStringExtra(AvenuesParams.ORDER_ID)
        )
    }

    private inner class RenderView :
        AsyncTask<Void?, Void?, Void?>() {
        override fun onPreExecute() {
            super.onPreExecute()
            LoadingDialog.showLoadingDialog(this@WebViewActivity, "Loading...")      // Showing progress dialog
        }

        protected override fun doInBackground(vararg p0: Void?): Void? {
            if (!ServiceUtility.chkNull(vResponse)!!.equals("")
                && ServiceUtility.chkNull(vResponse).toString().indexOf("ERROR") === -1
            ) {
                val vEncVal = StringBuffer("")
                vEncVal.append(
                    ServiceUtility.addToPostParams(
                        AvenuesParams.AMOUNT,
                        mainIntent!!.getStringExtra(AvenuesParams.AMOUNT)
                    )
                )
                vEncVal.append(
                    ServiceUtility.addToPostParams(
                        AvenuesParams.CURRENCY,
                        mainIntent!!.getStringExtra(AvenuesParams.CURRENCY)
                    )
                )
                encVal = RSAUtility.encrypt(
                    vEncVal.substring(0, vEncVal.length - 1),
                    vResponse
                ) //encrypt amount and currency
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            LoadingDialog.cancelLoading()       // Dismiss the progress dialog
            class MyJavaScriptInterface {
                @JavascriptInterface
                fun processHTML(html: String) {
                    // process the html source code to get final status of transaction
                    var status: String? = null
                    status = if (html.indexOf("Failure") != -1) {
                        "Transaction Declined!"
                    } else if (html.indexOf("Success") != -1) {
                        "Transaction Successful!"
                    } else if (html.indexOf("Aborted") != -1) {
                        "Transaction Cancelled!"
                    } else {
                        "Status Not Known!"
                    }
                    Toast.makeText(applicationContext, status, Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, StatusActivity::class.java)
                    intent.putExtra("transStatus", status)
                    startActivity(intent)
                }
            }

            val webview = findViewById<View>(R.id.webview) as WebView
            webview.settings.javaScriptEnabled = true
            webview.addJavascriptInterface(MyJavaScriptInterface(), "HTMLOUT")
            webview.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(webview, url)
                    LoadingDialog.cancelLoading()
                    if (url.indexOf("/ccavResponseHandler.jsp") != -1) {
                        webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');")
                    }
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    LoadingDialog.showLoadingDialog(this@WebViewActivity, "Loading...")
                }
            }
            try {
                val postData = AvenuesParams.ACCESS_CODE + "=" + URLEncoder.encode(
                    mainIntent!!.getStringExtra(AvenuesParams.ACCESS_CODE), "UTF-8"
                ) + "&" + AvenuesParams.MERCHANT_ID + "=" + URLEncoder.encode(
                    mainIntent!!.getStringExtra(AvenuesParams.MERCHANT_ID), "UTF-8"
                ) + "&" + AvenuesParams.ORDER_ID + "=" + URLEncoder.encode(
                    mainIntent!!.getStringExtra(AvenuesParams.ORDER_ID), "UTF-8"
                ) + "&" + AvenuesParams.REDIRECT_URL + "=" + URLEncoder.encode(
                    mainIntent!!.getStringExtra(AvenuesParams.REDIRECT_URL), "UTF-8"
                ) + "&" + AvenuesParams.CANCEL_URL + "=" + URLEncoder.encode(
                    mainIntent!!.getStringExtra(AvenuesParams.CANCEL_URL), "UTF-8"
                ) + "&" + AvenuesParams.ENC_VAL + "=" + URLEncoder.encode(encVal, "UTF-8")
                webview.postUrl(Constants.TRANS_URL, postData.toByteArray())
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }

    }

    fun get_RSA_key(ac: String?, od: String?) {
        LoadingDialog.showLoadingDialog(this@WebViewActivity, "Loading...")
        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, mainIntent!!.getStringExtra(AvenuesParams.RSA_KEY_URL),
            Response.Listener { response: String? ->
                //Toast.makeText(WebViewActivity.this,response,Toast.LENGTH_LONG).show();
                LoadingDialog.cancelLoading()
                if (response != null && response != "") {
                    vResponse = response ///save retrived rsa key
                    if (vResponse!!.contains("!ERROR!")) {
                        show_alert(vResponse!!)
                    } else {
                        RenderView()
                            .execute() // Calling async task to get display content
                    }
                } else {
                    show_alert("No response")
                }
            },
            Response.ErrorListener { error: VolleyError? -> LoadingDialog.cancelLoading() }) {
            override fun getParams(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params[AvenuesParams.ACCESS_CODE] = ac!!
                params[AvenuesParams.ORDER_ID] = od!!
                return params
            }
        }
        stringRequest.retryPolicy = DefaultRetryPolicy(
            30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }

    fun show_alert(msg: String) {
        var msg = msg
        val alertDialog = AlertDialog.Builder(
            this@WebViewActivity
        ).create()
        alertDialog.setTitle("Error!!!")
        if (msg.contains("\n")) msg = msg.replace("\\\n".toRegex(), "")
        alertDialog.setMessage(msg)
        alertDialog.setButton(
            Dialog.BUTTON_POSITIVE, "OK"
        ) { dialog, which -> finish() }
        alertDialog.show()
    }

}