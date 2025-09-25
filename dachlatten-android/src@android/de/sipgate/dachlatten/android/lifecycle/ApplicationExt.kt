package de.sipgate.dachlatten.android.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn

private val lifecycleEventFlow =
    callbackFlow {
        val observer = LifecycleEventObserver { _, event -> trySend(event) }
        val lifecycle = ProcessLifecycleOwner
            .get()
            .lifecycle

        lifecycle.addObserver(observer)

        awaitClose {
            lifecycle.removeObserver(observer)
        }
    }

public val isInForeground: SharedFlow<Boolean> =
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
    }.shareIn(CoroutineScope(Dispatchers.Unconfined), SharingStarted.Eagerly, 1)
