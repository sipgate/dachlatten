package de.sipgate.dachlatten.primitives

public val Array<Boolean>.allTrue: Boolean
    get() = all { it }
