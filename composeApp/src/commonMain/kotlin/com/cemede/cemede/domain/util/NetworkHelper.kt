package com.cemede.cemede.domain.util

import io.mockative.Mockable
import kotlinx.coroutines.flow.Flow

@Mockable
interface NetworkHelper {
    fun isNetworkAvailable(): Boolean

    fun observeNetworkStatus(): Flow<Boolean>
}

expect fun getNetworkHelper(): NetworkHelper
