package com.cemede.cemede.domain.util

import kotlinx.coroutines.flow.Flow

interface NetworkHelper {
    fun isNetworkAvailable(): Boolean

    fun observeNetworkStatus(): Flow<Boolean>
}

expect fun getNetworkHelper(): NetworkHelper
