package de.sipgate.dachlatten.flow

import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import org.junit.jupiter.api.Assertions

internal suspend fun <T> assertFinalValueEquals(expected: T, actual: Flow<T>) = actual.test {
    Assertions.assertEquals(expected, awaitItem())
    awaitComplete()
}
