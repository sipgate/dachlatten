package de.sipgate.dachlatten.primitives.filter

import kotlin.text.filterNot

public fun String.withoutEmojisAndOtherSymbols(): String = filterNot {
    val type = Character.getType(it)
    type == Character.SURROGATE.toInt() ||
        type == Character.OTHER_SYMBOL.toInt() ||
        type == Character.NON_SPACING_MARK.toInt()
}
