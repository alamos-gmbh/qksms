package com.moez.QKSMS.manager

interface AlamosSendManager {

    fun handleMessageBody(msg: String, sender: String): String

}