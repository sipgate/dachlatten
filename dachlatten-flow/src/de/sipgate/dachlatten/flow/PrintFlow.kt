package de.sipgate.dachlatten.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

public inline fun <T> Flow<T>.printFlow(
    tag: String = "from flow",
    crossinline printFunc: (String) -> Unit = { content -> println("$tag: $content") },
    crossinline stringFunc: (T?) -> String? = { it.toString() }
): Flow<T> = onEach {
    printFunc(stringFunc(it) ?: "null")
}
