package com.neflis.neflis.ui.config

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.drawee.backends.pipeline.Fresco

class NeflisApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        Fresco.initialize(this)
    }
}