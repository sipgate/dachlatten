package de.sipgate.dachlatten.debug

val stacktrace: String
    get() = Thread.currentThread()
        .stackTrace
        .drop(2)
        .joinToString(separator = "\n")