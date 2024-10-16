package de.sipgate.dachlatten.compose.filter

import androidx.compose.ui.text.input.TextFieldValue
import de.sipgate.dachlatten.primitives.filter.withoutEmojisAndOtherSymbols

fun TextFieldValue.withoutEmojisAndOtherSymbols(): TextFieldValue {
    return copy(text = text.withoutEmojisAndOtherSymbols())
}
