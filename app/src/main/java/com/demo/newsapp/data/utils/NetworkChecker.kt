package com.demo.newsapp.data.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkChecker(private val connectivityManager: ConnectivityManager) {

    fun hasInternetConnection(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }

}