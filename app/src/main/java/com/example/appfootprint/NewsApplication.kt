package com.example.appfootprint

import android.app.Application
import com.google.firebase.database.FirebaseDatabase


class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}