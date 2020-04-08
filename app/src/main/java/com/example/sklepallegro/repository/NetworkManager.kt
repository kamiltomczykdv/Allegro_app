package com.example.sklepallegro.repository

import android.content.Context
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork

class NetworkManager {
    fun getReactiveNetwork(context: Context) = ReactiveNetwork
        .observeNetworkConnectivity(context)
}