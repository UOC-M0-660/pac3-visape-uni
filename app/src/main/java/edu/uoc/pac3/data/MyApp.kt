package edu.uoc.pac3.data

import android.app.Application
import android.content.Context

public class MyApp : Application() {
    companion object {
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        MyApp.context = applicationContext
    }
}