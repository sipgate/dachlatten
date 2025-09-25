package de.sipgate.dachlatten.primitives

public inline fun <reified T : Enum<T>> T.isAnyOf(vararg values: T): Boolean = values.any { this == it }

public inline fun <reified T : Enum<T>> T.isNoneOf(vararg values: T): Boolean = values.none { this == it }
