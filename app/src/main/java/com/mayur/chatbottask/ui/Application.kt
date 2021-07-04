package com.mayur.chatbottask.ui

import android.app.Application
import com.rommansabbir.networkx.NetworkX
import com.rommansabbir.networkx.NetworkXObservingStrategy
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkX.startObserving(this, NetworkXObservingStrategy.REALTIME)
    }
}