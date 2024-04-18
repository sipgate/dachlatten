package de.sipgate.dachlatten.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FixedClockTest {
    @Test
    fun fixedClockReturnsFixedInstance() {
        val instant = Instant.fromEpochSeconds(1713453869)
        val fixedClock = Clock.Fixed(instant)

        val instantFromClock = fixedClock.now()

        assertEquals(instant, instantFromClock)
    }

    @Test
    fun fixedClockReturnsFixedInstanceWhenCalledMultipleTimes() {
        val instant = Instant.fromEpochSeconds(1713453869)
        val fixedClock = Clock.Fixed(instant)

        val firstInstantFromClock = fixedClock.now()
        val secondInstantFromClock = fixedClock.now()

        assertEquals(instant, firstInstantFromClock)
        assertEquals(firstInstantFromClock, secondInstantFromClock)
    }
}
