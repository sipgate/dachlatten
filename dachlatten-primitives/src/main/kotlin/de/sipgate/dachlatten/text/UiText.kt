package de.sipgate.dachlatten.text

import androidx.annotation.StringRes

public typealias TranslatedText = Map<String, String>

public sealed interface UiText {
    @JvmInline
    public value class DynamicString(public val value: () -> String) : UiText {
        public constructor(fixed: String) : this({ fixed })
    }

    public data class StringResource(
        @StringRes val resId: Int,
        val args: List<Any>,
    ) : UiText {
        public constructor(
            @StringRes resId: Int,
            vararg args: Any
        ) : this(resId, args.asList())
    }

    public data class MultiLangString(
        val language: TranslatedText,
        @StringRes val fallbackResource: Int? = null,
        val args: List<Any>,
    ) : UiText {
        public constructor(
            language: TranslatedText,
            @StringRes fallbackResource: Int? = null,
            vararg args: Any,
        ) : this(language, fallbackResource, args.asList())
    }
}
