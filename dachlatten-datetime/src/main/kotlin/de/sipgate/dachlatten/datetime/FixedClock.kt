package de.sipgate.dachlatten.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

fun Clock.Companion.Fixed(instant: Instant) = object : Clock {
    override fun now(): Instant = instant
}
