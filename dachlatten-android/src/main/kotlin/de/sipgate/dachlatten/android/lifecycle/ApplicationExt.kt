package de.sipgate.dachlatten.android.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

private val lifecycleEventFlow =
    callbackFlow {
        val lifecycleListener = LifecycleEventObserver { _, event -> trySend(event) }

        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(lifecycleListener)

        awaitClose {
            ProcessLifecycleOwner
                .get()
                .lifecycle
                .removeObserver(lifecycleListener)
        }
    }

val isInForeground =
    lifecycleEventFlow.map { event ->
        when (event) {
            Lifecycle.Event.ON_CREATE,
            Lifecycle.Event.ON_START,
            Lifecycle.Event.ON_RESUME,
            -> true

            Lifecycle.Event.ON_PAUSE,
            Lifecycle.Event.ON_STOP,
            Lifecycle.Event.ON_DESTROY,
            Lifecycle.Event.ON_ANY,
            -> false
        }.also {
            Log.d("dachlatten-android", "Event $event set Application is in foreground to: $it")
        }
    }.stateIn(CoroutineScope(Dispatchers.Unconfined), SharingStarted.Eagerly, false)