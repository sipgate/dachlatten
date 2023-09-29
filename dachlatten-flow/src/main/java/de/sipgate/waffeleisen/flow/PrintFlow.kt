package de.sipgate.waffeleisen.flow

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <T> Flow<T?>.printFlow(
    tag: String = "from flow",
    crossinline printFunc: (String) -> Unit = { content -> Log.d(tag, content) },
    crossinline stringFunc: (T?) -> String? = { it.toString() }
) = map {
    printFunc(stringFunc(it) ?: "null")
    it
}
