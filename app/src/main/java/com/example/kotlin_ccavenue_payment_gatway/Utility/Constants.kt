package com.example.kotlin_ccavenue_payment_gatway.Utility

object Constants {
    const val PARAMETER_SEP = "&"
    const val PARAMETER_EQUALS = "="
    const val TRANS_URL = "https://secure.ccavenue.com/transaction/initTrans"
    const val access_code = "AVVS90JF12BR25SVRB" //4YRUXLSRO20O8NIH   add your access_code
    const val merchantId = "1004089" //2  add your merchant_id
    const val currency = "INR"

    //Test Url
    //	public static final String redirectUrl="http://122.182.6.216/merchant/ccavResponseHandler.jsp"; //this is test url you can modify with your on url, you can use php or jsp,
    //	public static final String cancelUrl="http://122.182.6.216/merchant/ccavResponseHandler.jsp";//this is test url you can modify with your on url
    //	public static final String rsaKeyUrl="https://secure.ccavenue.com/transaction/jsp/GetRSA.jsp"; //this is test url you can modify with your on url

    //Live Url
    const val redirectUrl = "https://schoolforme.in:5000/api/students/PaymentResponse1" //this is test url you can modify with your on url, you can use php or jsp,
    const val cancelUrl = "https://schoolforme.in:5000/api/students/PaymentResponse1" //this is test url you can modify with your on url
    const val rsaKeyUrl = "https://secure.ccavenue.com/transaction/jsp/GetRSA.jsp" //this is test url you can modify with your on url

}