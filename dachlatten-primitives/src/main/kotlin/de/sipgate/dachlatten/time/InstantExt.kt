package de.sipgate.dachlatten.time

import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@ExperimentalTime
infix fun Duration.ago(now: Instant = Clock.System.now()): Instant {
    return now - this
}

@ExperimentalTime
val Duration.ago: Instant
    get() = Clock.System.now() - this
