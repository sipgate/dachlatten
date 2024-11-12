package de.sipgate.dachlatten.primitives

import de.sipgate.dachlatten.primitives.predicates.Predicate

fun <T> Collection<T>.contains(predicate: Predicate<T>): Boolean {
    return firstOrNull { predicate(it) } != null
}
