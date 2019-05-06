package com.example.storylist

import android.content.Context
import android.preference.PreferenceManager
import com.example.storylist.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins

class StoryApplication : DaggerApplication() {

    init {
        instance = this
    }


    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler({ _ -> })
    }


    protected override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    companion object {
        const val SERVER_FETCH_TIME = "server_last_fetch_time"
        private var instance: StoryApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun shouldFromServer(): Boolean {
            val lastFetchTime: Long = this.getPreference(SERVER_FETCH_TIME)
            val diff: Long = (System.currentTimeMillis() - lastFetchTime)
            val days = ((diff / (1000 * 60 * 60)) / 24)
            return days != 0L
        }

        fun storePreference(preferenceKey: String, value: Long) {
            try {
                val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext())
                prefs.edit().putLong(preferenceKey, value).commit()
            } catch (ex: Throwable) {
                ex.printStackTrace()
            }

        }

        fun getPreference(preferenceKey: String): Long {
            val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext())
            return prefs.getLong(preferenceKey, 0)
        }
    }
}
