package de.sipgate.dachlatten.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

fun either(
    flowA: Flow<Boolean>,
    flowB: Flow<Boolean>,
): Flow<Boolean> = combine(flowA, flowB) { a, b -> a || b }

infix fun Flow<Boolean>.or(other: Flow<Boolean>) = either(this, other)

fun both(
    flowA: Flow<Boolean>,
    flowB: Flow<Boolean>,
): Flow<Boolean> = combine(flowA, flowB) { a, b -> a && b }

infix fun Flow<Boolean>.and(other: Flow<Boolean>) = both(this, other)

fun Flow<Boolean>.not(): Flow<Boolean> = map { it.not() }
