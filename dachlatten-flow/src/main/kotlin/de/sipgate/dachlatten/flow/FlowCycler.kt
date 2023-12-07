package de.sipgate.dachlatten.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration

fun tickEvery(time: Duration) = flow {
    while (true) {
        emit(Unit)
        delay(time)
    }
}

fun <T> cycleBetween(
    tickerInterval: Duration,
    flow1: Flow<T>,
    flow2: Flow<T>,
): Flow<T> = cycleBetween(tickEvery(tickerInterval), flow1, flow2)

fun <I,T> cycleBetween(
    ticker: Flow<I>,
    flow1: Flow<T>,
    flow2: Flow<T>,
): Flow<T> {
    var first = false
    return combine(ticker, flow1, flow2) { _, a, b ->
        first = !first
        return@combine when {
            first -> a
            else -> b
        }
    }
}
