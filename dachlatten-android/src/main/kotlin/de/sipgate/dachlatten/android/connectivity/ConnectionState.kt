package de.sipgate.dachlatten.android.connectivity

sealed class ConnectionState {
    data object Available : ConnectionState()
    data object Unavailable : ConnectionState()
}