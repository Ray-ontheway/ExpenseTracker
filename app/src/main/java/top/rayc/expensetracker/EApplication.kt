package top.rayc.expensetracker

import android.app.Application
import top.rayc.expensetracker.data.ObjectBox
import top.rayc.expensetracker.data.preferences.AppPreferenceDataStore

class EApplication : Application() {

    companion object {
        const val TAG = "EApplication"
        lateinit var instance: EApplication
            private set

        val appPreferenceDataStore: AppPreferenceDataStore by lazy {
            AppPreferenceDataStore(instance)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ObjectBox.init(instance)

    }

}