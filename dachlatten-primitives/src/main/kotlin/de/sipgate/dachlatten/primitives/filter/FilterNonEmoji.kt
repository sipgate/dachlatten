package de.sipgate.dachlatten.primitives.filter

import kotlin.text.filterNot

fun String.withoutEmojisAndOtherSymbols() = filterNot {
    val type = Character.getType(it)
    type == Character.SURROGATE.toInt() ||
        type == Character.OTHER_SYMBOL.toInt() ||
        type == Character.NON_SPACING_MARK.toInt()
}
