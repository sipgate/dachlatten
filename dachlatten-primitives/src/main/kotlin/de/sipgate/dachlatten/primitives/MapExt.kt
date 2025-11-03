package de.sipgate.dachlatten.primitives

public fun <K, V> Map<K, V>.take(n: Int): Map<K, V> {
    return entries.take(n).associate { it.toPair() }
}
