package de.sipgate.dachlatten.time

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class InstantExtTest {

    @Test
    fun testDurationAgoWorks() {
        val expected = Clock.System.now() - 2.hours

        val result = 2.hours.ago

        assertEquals(expected.epochSeconds, result.epochSeconds)
    }

    @Test
    fun testRelativeDurationAgoWorks() {
        val now = Instant.parse("2024-01-15T10:30:00Z")

        val result = 2.hours ago(now)

        assertEquals(Instant.parse("2024-01-15T08:30:00Z"), result)
    }
}
