package com.tide.barsaround.di.activity

import android.app.Activity

interface HasActivitySubcomponentBuilders {
    fun getActivityComponentBuilder(activityClass: Class<out Activity>): ActivityComponentBuilder<*, *>?
}
