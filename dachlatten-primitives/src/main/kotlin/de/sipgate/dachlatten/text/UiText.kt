package de.sipgate.dachlatten.text

import androidx.annotation.StringRes

typealias TranslatedText = Map<String, String>

sealed interface UiText {
    data class DynamicString(val value: () -> String) : UiText {
        constructor(fixed: String) : this({ fixed })
    }

    data class StringResource(
        @StringRes val resId: Int,
        val args: List<Any>,
    ) : UiText {
        constructor(
            @StringRes resId: Int,
            vararg args: Any
        ) : this(resId, args.asList())
    }

    data class MultiLangString(
        val language: TranslatedText,
        @StringRes val fallbackResource: Int? = null,
        val args: List<Any>,
    ) : UiText {
        constructor(
            language: TranslatedText,
            @StringRes fallbackResource: Int? = null,
            vararg args: Any,
        ) : this(language, fallbackResource, args.asList())
    }
}
