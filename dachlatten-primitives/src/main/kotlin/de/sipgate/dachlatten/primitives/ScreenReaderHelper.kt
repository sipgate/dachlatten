package de.sipgate.dachlatten.primitives

import kotlin.collections.joinToString
import kotlin.text.drop
import kotlin.text.map

/**
 * Forces the single characters or digits in this string
 * to be read as a sequence: E.g. Hello becomes H e l l o.
 */
fun String.readAsSingles(): String =
    map { " $it" }
        .joinToString(separator = "")
        .drop(1)
