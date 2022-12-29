package com.example.kotlin_ccavenue_payment_gatway.Utility

import java.util.*

object ServiceUtility {

    /*@Throws(IOException::class)
    fun readProperties(pFilePath: String?): Map<*, *>? {
        var vPropertyMap: MutableMap<*, *> = LinkedHashMap<Any?, Any?>()
        var vTckey: Set<*>?
        var vTcPropItr: Iterator<String>?
        var vTCProperties: Properties? = null
        try {
            vTCProperties = Properties()
            vTCProperties.load(ServiceUtility::class.java.getResourceAsStream(pFilePath))
            vTckey = vTCProperties.keys
            vTcPropItr = vTckey.iterator()
            vPropertyMap = LinkedHashMap<Any?, Any?>()
            while (vTcPropItr.hasNext()) {
                val vKey = vTcPropItr.next()
                vPropertyMap[vKey] = vTCProperties[vKey]
            }
        } finally {
            vTcPropItr = null
            vTckey = null
            vTCProperties = null
        }
        return vPropertyMap
    }*/


    fun chkNull(pData: Any?): Any? {
        return pData ?: ""
    }

    /*@Throws(Exception::class)
    fun tokenizeToHashMap(msg: String?, delimPairValue: String, delimKeyPair: String?): Map<*, *>? {
        val keyPair: MutableMap<*, *> = HashMap<Any?, Any?>()
        var respList: ArrayList<*> = ArrayList<Any?>()
        var part: String? = ""
        val strTkn = StringTokenizer(msg, delimPairValue, true)
        while (strTkn.hasMoreTokens()) {
            part = strTkn.nextElement() as String
            if (part == delimPairValue) {
                part = null
            } else {
                respList = ServiceUtility.tokenizeToArrayList(part, delimKeyPair)
                if (respList.size == 2) keyPair[respList[0]] =
                    respList[1] else if (respList.size == 1) keyPair[respList[0]] =
                    null
            }
            if (part == null) continue
            if (strTkn.hasMoreTokens()) strTkn.nextElement()
        }
        return if (keyPair.size > 0) keyPair else null
    }


    @Throws(java.lang.Exception::class)
    fun tokenizeToArrayList(msg: String, delim: String?): ArrayList<*>? {
        val respList: ArrayList<*> = ArrayList<Any?>()
        var varName: String? = null
        var varVal: String? = null
        val index = msg.indexOf(delim!!)
        varName = msg.substring(0, index)
        if (index + 1 != msg.length) varVal = msg.substring(index + 1, msg.length)
        respList.add(varName)
        respList.add(varVal)
        return if (respList.size > 0) respList else null
    }*/


    fun addToPostParams(paramKey: String, paramValue: String?): String? {
        return if (paramValue != null) paramKey + Constants.PARAMETER_EQUALS + paramValue + Constants.PARAMETER_SEP else ""
    }

    fun randInt(min: Int, max: Int): Int {
        val rand = Random()
        return rand.nextInt(max - min + 1) + min
    }
}
