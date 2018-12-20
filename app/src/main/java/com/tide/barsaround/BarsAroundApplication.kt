package com.tide.barsaround

import android.app.Activity
import android.app.Application
import android.content.Context
import com.tide.barsaround.di.activity.ActivityComponentBuilder
import com.tide.barsaround.di.activity.HasActivitySubcomponentBuilders
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class BarsAroundApplication : Application(), HasActivitySubcomponentBuilders {

    companion object {
        fun get(context: Context) = context.applicationContext as HasActivitySubcomponentBuilders
    }

    @Inject
    lateinit var activityComponentBuilders: Map<Class<out Activity>, @JvmSuppressWildcards Provider<ActivityComponentBuilder<*, *>>>

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
        appComponent.inject(this)
    }

    override fun getActivityComponentBuilder(activityClass: Class<out Activity>) =
        activityComponentBuilders[activityClass]?.get()
}
