package de.sipgate.dachlatten.primitives

inline fun <reified T : Enum<T>> T.isAnyOf(vararg values: T) = values.any { this == it }

inline fun <reified T : Enum<T>> T.isNoneOf(vararg values: T) = values.none { this == it }
