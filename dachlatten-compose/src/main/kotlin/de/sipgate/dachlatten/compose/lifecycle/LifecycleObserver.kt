package de.sipgate.dachlatten.compose.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleObserver

@Composable
fun LifecycleObserver(lifecycleObserver: LifecycleObserver) {
    val lifecycle = LocalLifecycleOwner.current
    DisposableEffect(lifecycle) {
        lifecycle.lifecycle.addObserver(lifecycleObserver)
        onDispose { lifecycle.lifecycle.removeObserver(lifecycleObserver) }
    }
}
