package de.sipgate.dachlatten.primitives

import de.sipgate.dachlatten.primitives.predicates.Predicate

@Deprecated("Use the Kotlin StdLib any function instead", replaceWith = ReplaceWith("any(predicate)"))
public fun <T> Collection<T>.contains(predicate: Predicate<T>): Boolean {
    return firstOrNull { predicate(it) } != null
}
