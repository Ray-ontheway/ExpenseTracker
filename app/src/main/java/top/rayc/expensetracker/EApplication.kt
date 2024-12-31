package top.rayc.expensetracker

import android.app.Application

class EApplication : Application() {

    companion object {
        const val TAG = "EApplication"
        lateinit var instance: EApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}