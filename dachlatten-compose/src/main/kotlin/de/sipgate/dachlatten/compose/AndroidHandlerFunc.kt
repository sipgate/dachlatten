package de.sipgate.dachlatten.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// This  provides a parameterless handlerFunc that can be
// passed around and invoked from anywhere. It will keep a
// reference to the Context given during its creation.
// Warning:
// Depending on the situation this can easily leak the Context!

typealias AndroidClickHandler = context (ContextProvider) () -> Unit

@Composable
inline fun withContext(crossinline target: AndroidClickHandler): ClickHandler {
    val context = LocalContext.current
    return {
        with(ContextProviderImpl(context)) {
            target.invoke(this)
        }
    }
}

@Composable
inline fun <reified T> withContext(
    crossinline target: context (ContextProvider)
        (T) -> Unit,
): (T) -> Unit {
    val context = LocalContext.current
    return {
        with(ContextProviderImpl(context)) {
            target.invoke(this, it)
        }
    }
}

inline fun Context.withContext(
    crossinline target: context (ContextProvider)
        () -> Unit,
): HandlerFunc = {
    with(ContextProviderImpl(this)) {
        target(this)
    }
}
