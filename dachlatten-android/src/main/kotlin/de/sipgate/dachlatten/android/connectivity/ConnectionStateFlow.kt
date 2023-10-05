package de.sipgate.dachlatten.android.connectivity

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

val ConnectivityManager.isOffline: Flow<Boolean>
    get() = observeConnectivityAsFlow()
        .map { it == ConnectionState.Unavailable }

val ConnectivityManager.isOnline: Flow<Boolean>
    get() = observeConnectivityAsFlow()
        .map { it == ConnectionState.Available }

private fun ConnectivityManager.observeConnectivityAsFlow() = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(ConnectionState.Available)
            }

            override fun onLost(network: Network) {
                trySend(ConnectionState.Unavailable)
            }
        }

        registerDefaultNetworkCallback(callback)

        awaitClose {
            unregisterNetworkCallback(callback)
        }
    }.onStart { emit(ConnectionState.Unavailable) }
