package de.sipgate.dachlatten.datetime

import java.time.LocalDateTime
import java.util.Date
import java.util.TimeZone

fun Date.toLocalDateTime(zone: TimeZone): LocalDateTime = LocalDateTime.ofInstant(toInstant(), zone.toZoneId())
