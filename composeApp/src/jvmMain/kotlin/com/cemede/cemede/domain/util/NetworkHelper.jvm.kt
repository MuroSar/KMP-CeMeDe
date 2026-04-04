package com.cemede.cemede.domain.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import java.net.InetAddress

class JVMNetworkHelper : NetworkHelper {
    override fun isNetworkAvailable(): Boolean =
        try {
            val address = InetAddress.getByName("8.8.8.8")
            address.isReachable(3000)
        } catch (e: Exception) {
            false
        }

    override fun observeNetworkStatus(): Flow<Boolean> =
        flow {
            while (true) {
                emit(isNetworkAvailable())
                delay(5000) // Poll every 5 seconds
            }
        }.distinctUntilChanged()
}

actual fun getNetworkHelper(): NetworkHelper = JVMNetworkHelper()
