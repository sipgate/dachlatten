package de.sipgate.dachlatten.compose.text

import android.content.res.Resources
import android.os.LocaleList
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource

sealed interface UiText {
    data class DynamicString(val value: String) : UiText

    data class StringResource(
        @StringRes val resId: Int,
        val args: List<Any>,
    ) : UiText {
        constructor(
            @StringRes resId: Int,
            vararg args: Any) : this(resId, args.asList())
    }

    data class MultiLangString(
        private val language: Map<String, String>,
        @StringRes val fallbackResource: Int? = null,
        val args: List<Any>,
    ) : UiText {
        constructor(
            language: Map<String, String>,
            @StringRes fallbackResource: Int? = null,
            vararg args: Any,
        ) : this(language, fallbackResource, args.asList())

        fun LocaleList.getStringForLocales(): String? {
            val locale = getFirstMatch(language.keys.toTypedArray())
            return language[locale?.language]
        }
    }

    fun asString(resources: Resources) = when (this) {
        is DynamicString -> value
        is StringResource -> resources.getString(resId, *(args.toTypedArray()))
        is MultiLangString -> {
            val arguments = args.toTypedArray()
            resources.configuration.locales.getStringForLocales()?.format(*arguments)
                ?: fallbackResource?.let { resources.getString(it, *arguments) }
                ?: throw Resources.NotFoundException("Could not find multilang string")
        }
    }

    @Composable
    fun asString() = when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(id = resId, formatArgs = args.toTypedArray())
            is MultiLangString -> {
                val arguments = args.toTypedArray()
                LocalConfiguration.current.locales.getStringForLocales()?.format(*arguments)
                    ?: fallbackResource?.let { stringResource(id = it, formatArgs = arguments) }
                    ?: throw Resources.NotFoundException("Could not find multilang string")
            }
        }
}
