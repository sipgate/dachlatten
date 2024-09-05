package de.sipgate.dachlatten.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

/**
 * Helper function to provide Clock instances providing fixed Instants
 *
 * This is useful for unit tests to inject Clocks that will always return a known value for `now()`.
 */
@Suppress("FunctionName")
fun Clock.Companion.Fixed(instant: Instant) = object : Clock {
    override fun now(): Instant = instant
}
