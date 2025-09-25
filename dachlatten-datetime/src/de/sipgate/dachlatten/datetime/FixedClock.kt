package de.sipgate.dachlatten.datetime

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Helper function to provide Clock instances providing fixed Instants
 *
 * This is useful for unit tests to inject Clocks that will always return a known value for `now()`.
 */
@ExperimentalTime
@Suppress("FunctionName")
public fun Clock.Companion.Fixed(instant: Instant): Clock = object : Clock {
    override fun now(): Instant = instant
}
