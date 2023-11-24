package com.thex.bookshare.app

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class TimberInitializerTree : Timber.Tree() {

    init {
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.ERROR) { // only for error level
                /**
                 * THROW FIREBASE CRASHLYTICS EXCEPTION
                 * **/
                val exception = t ?: Exception(message)
                Firebase.crashlytics.log(message)
                Firebase.crashlytics.setCustomKey(tag.toString(), message)
                Firebase.crashlytics.recordException(exception)
            }
    }
}