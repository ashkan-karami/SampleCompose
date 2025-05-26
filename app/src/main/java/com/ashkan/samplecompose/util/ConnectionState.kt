package com.ashkan.samplecompose.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers
import android.net.Network
import android.net.NetworkRequest
import androidx.compose.runtime.getValue
import kotlinx.coroutines.channels.awaitClose

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}

fun getCurrentConnectionState(context: Context): ConnectionState {
    val connectivityManager = getSystemService(context, ConnectivityManager::class.java)
    val network = connectivityManager?.activeNetwork ?: return ConnectionState.Unavailable
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return ConnectionState.Unavailable

    return if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
        capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
        ConnectionState.Available
    } else {
        ConnectionState.Unavailable
    }
}

@ExperimentalCoroutinesApi
fun observeConnectivityAsFlow(context: Context): Flow<ConnectionState> {
    val connectivityManager = getSystemService(context, ConnectivityManager::class.java)

    return callbackFlow {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(ConnectionState.Available)
            }

            override fun onLost(network: Network) {
                trySend(ConnectionState.Unavailable)
            }

            override fun onUnavailable() {
                trySend(ConnectionState.Unavailable)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    trySend(ConnectionState.Available)
                } else {
                    trySend(ConnectionState.Unavailable)
                }
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager?.registerNetworkCallback(request, networkCallback)

        val currentState = getCurrentConnectionState(context)
        trySend(currentState)

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(networkCallback)
        }
    }.distinctUntilChanged().flowOn(Dispatchers.IO)
}

@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current
    return produceState(initialValue = getCurrentConnectionState(context)) {
        observeConnectivityAsFlow(context).collect { value = it }
    }
}

//@ExperimentalCoroutinesApi
//@Composable
//fun NetworkStatusDisplay() {
//    val connectionState by connectivityState()
//    val isConnected = connectionState is ConnectionState.Available
//
//    if (isConnected) {
        //  Connected to the Internet
//    } else {
        // No Internet Connection
//    }
//}