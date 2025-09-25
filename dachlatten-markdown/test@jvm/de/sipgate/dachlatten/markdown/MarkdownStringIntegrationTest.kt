package de.sipgate.dachlatten.markdown

import androidx.compose.foundation.text.BasicText
import kotlin.test.Test
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi

@OptIn(ExperimentalTestApi::class)
class MarkdownStringIntegrationTest {

    @Test
    fun complexLinkWithUrlEncodedEncodedLink() = runComposeUiTest {
        val complexMdWithEncodedUrl =
            "[Why does the chicken cross the road?](https://help.satellite.me/hc/de/articles/360000449825-K%C3%B6nnt-ihr-den-Verifizierungscode-auch-per-E-Mail-schicken)"
        val parsedText = parseMarkdown(complexMdWithEncodedUrl)

        setContent {
            BasicText(text = parsedText)
        }
    }
}
