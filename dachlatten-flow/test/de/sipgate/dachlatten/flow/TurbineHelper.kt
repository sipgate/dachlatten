package de.sipgate.dachlatten.flow

import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import kotlin.test.assertEquals

internal suspend fun <T> assertFinalValueEquals(expected: T, actual: Flow<T>) = actual.test {
    assertEquals(expected, awaitItem())
    awaitComplete()
}
