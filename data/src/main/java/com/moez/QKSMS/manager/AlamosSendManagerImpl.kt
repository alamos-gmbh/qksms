package com.moez.QKSMS.manager

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AlamosSendManagerImpl @Inject constructor(
        private val context: Context
) : AlamosSendManager {

    companion object {
        const val ACTION_PROCESS_SMS = "org.xcrypt.apager.android2.processSMS"
    }

    override fun handleMessageBody(msg: String, sender: String): String {
        var modified = msg

        try {
            if (isApagerPROMessage(msg)) {
                sendToApager(msg, sender)
                modified = modified.replace("apgr", "")
                modified = JSONObject(modified).getString("m")
                modified = "\uD83D\uDD34 aPager PRO: $modified"
            } else {
                Timber.i("Message is not an aPager PRO alert. Won't process")
            }
        } catch (e: Exception) {
            Timber.e(e, "Not a valid aPager PRO SMS (JSON invalid)")
        } finally {
            return modified
        }
    }

    private fun sendToApager(msg: String, sender: String) {

        val intent = Intent()
        intent.putExtra("body", msg)
        intent.putExtra("sender", sender)
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        intent.action = ACTION_PROCESS_SMS
        intent.component = ComponentName("org.xcrypt.apager.android2", "org.xcrypt.apager.android2.services.SMSReceiver")
        context.sendBroadcast(intent)
    }

    private fun isApagerPROMessage(msg: String): Boolean {
        return msg.startsWith("apgr{")
    }
}