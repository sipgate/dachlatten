package de.sipgate.dachlatten.debug

public val stacktrace: String
    get() = Thread.currentThread()
        .stackTrace
        .drop(2)
        .joinToString(separator = "\n")
