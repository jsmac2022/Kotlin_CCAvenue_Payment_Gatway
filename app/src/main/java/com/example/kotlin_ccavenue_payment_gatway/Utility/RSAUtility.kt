package com.example.kotlin_ccavenue_payment_gatway.Utility

import android.util.Base64
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

object RSAUtility {
    fun encrypt(plainText: String, key: String?): String? {
        try {
            val publicKey = KeyFactory.getInstance("RSA")
                .generatePublic(X509EncodedKeySpec(Base64.decode(key, Base64.DEFAULT)))
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            return Base64.encodeToString(
                cipher.doFinal(plainText.toByteArray(charset("UTF-8"))),
                Base64.DEFAULT
            )
         } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}