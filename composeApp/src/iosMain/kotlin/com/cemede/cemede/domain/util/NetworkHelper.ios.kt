package com.cemede.cemede.domain.util

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_satisfied
import platform.darwin.dispatch_get_main_queue

class IOSNetworkHelper : NetworkHelper {
    override fun isNetworkAvailable(): Boolean {
        // NWPathMonitor usually requires a callback to get the status reliably.
        // For a simple check, we might need a different approach or just rely on the flow.
        // However, we can try to create a temporary monitor and check its current path if possible,
        // but NWPathMonitor is designed to be asynchronous.
        // For now, we'll return true as default or implement a sync check if available.
        return true
    }

    override fun observeNetworkStatus(): Flow<Boolean> =
        callbackFlow {
            val monitor = nw_path_monitor_create()
            val queue = dispatch_get_main_queue()

            nw_path_monitor_set_update_handler(monitor) { path ->
                val status = nw_path_get_status(path)
                trySend(status == nw_path_status_satisfied)
            }

            nw_path_monitor_set_queue(monitor, queue)
            nw_path_monitor_start(monitor)

            awaitClose {
                nw_path_monitor_cancel(monitor)
            }
        }.distinctUntilChanged().onStart { emit(isNetworkAvailable()) }
}

actual fun getNetworkHelper(): NetworkHelper = IOSNetworkHelper()
