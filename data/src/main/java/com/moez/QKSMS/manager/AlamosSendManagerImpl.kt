package com.moez.QKSMS.manager

import android.content.Context
import com.moez.QKSMS.util.Preferences
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlamosSendManagerImpl @Inject constructor(
        private val context: Context,
        private val prefs: Preferences
) : AlamosSendManager {

    override fun sendToApager(msg: String) {
        Timber.i("Received new SMS: $msg")
    }
}