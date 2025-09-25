package de.sipgate.dachlatten.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration

public fun tickEvery(
    interval: Duration,
    initialDelay: Duration = Duration.ZERO
): Flow<Unit> = flow {
    delay(initialDelay)
    while (true) {
        emit(Unit)
        delay(interval)
    }
}

public fun <T> cycleBetween(
    interval: Duration,
    flow1: Flow<T>,
    flow2: Flow<T>,
    initialDelay: Duration = Duration.ZERO
): Flow<T> = cycleBetween(tickEvery(interval, initialDelay), flow1, flow2)

public fun <I, T> cycleBetween(
    ticker: Flow<I?>,
    flow1: Flow<T>,
    flow2: Flow<T>,
): Flow<T> {
    var first = false
    return combine(ticker, flow1, flow2) { _, a, b ->
        first = !first
        return@combine if (first) a else b
    }
}

public fun <T> cycleBetweenNonNull(
    tickerInterval: Duration,
    flow1: Flow<T>,
    flow2: Flow<T>,
): Flow<T> = cycleBetween(tickEvery(tickerInterval), flow1, flow2)

public fun <I, T> cycleBetweenNonNull(ticker: Flow<I?>, flow1: Flow<T>, flow2: Flow<T>): Flow<T> {
    var first = false
    return combine(ticker, flow1, flow2) { _, a, b ->
        first = !first
        when {
            first -> a ?: b
            else -> b ?: a
        }
    }
}
