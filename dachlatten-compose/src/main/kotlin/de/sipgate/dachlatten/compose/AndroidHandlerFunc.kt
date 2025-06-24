package de.sipgate.dachlatten.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// This  provides a parameterless handlerFunc that can be
// passed around and invoked from anywhere. It will keep a
// reference to the Context given during its creation.
// Warning:
// Depending on the situation this can easily leak the Context!

@Composable
inline fun <reified R> withContext(
    crossinline target: context (Context) () -> R
): () -> R = LocalContext.current.withContext(target)

@Composable
inline fun <reified T, R> withContext(
    crossinline target: context (Context) (T) -> R,
): (T) -> R = LocalContext.current.withContext(target)

inline fun <reified T, R> Context.withContext(
    crossinline target: context (Context) (T) -> R,
): (T) -> R = { param ->
    target(this, param)
}

inline fun <R> Context.withContext(
    crossinline target: context (Context) () -> R,
): () -> R = {
    target.invoke(this)
}
