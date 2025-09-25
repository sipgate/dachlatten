package de.sipgate.dachlatten.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.chunked
import kotlinx.coroutines.flow.flow
import kotlin.math.max
import kotlin.math.min

/**
 * copied from https://github.com/Kotlin/kotlinx.coroutines/pull/2378/files
 */

@ExperimentalCoroutinesApi
public fun <T> Flow<T>.windowed(size: Int, step: Int): Flow<List<T>> = when {
    size == step -> chunked(2)
    else -> flow {
        // check that size and step are > 0
        val queue = ArrayDeque<T>(size)
        val toSkip = max(
            step - size,
            0
        ) // if sbd would like to skip some elements before getting another window, by serving step greater than size, then why not?
        val toRemove = min(step, size)
        var skipped = 0
        collect { element ->
            if (queue.size < size && skipped == toSkip) {
                queue.add(element)
            } else if (queue.size < size && skipped < toSkip) {
                skipped++
            }

            if (queue.size == size) {
                emit(queue.toList())
                repeat(toRemove) { queue.removeFirst() }
                skipped = 0
            }
        }
    }
}
