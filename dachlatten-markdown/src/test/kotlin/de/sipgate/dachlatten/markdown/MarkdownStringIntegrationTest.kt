package de.sipgate.dachlatten.markdown

import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class MarkdownStringIntegrationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    @Config(qualifiers = "en")
    fun complexLinkWithUrlEncodedEncodedLink() {
        val complexMdWithEncodedUrl =
            "[Why does the chicken cross the road?](https://help.satellite.me/hc/de/articles/360000449825-K%C3%B6nnt-ihr-den-Verifizierungscode-auch-per-E-Mail-schicken)"
        val parsedText = parseMarkdown(complexMdWithEncodedUrl)

        composeTestRule.setContent {
            BasicText(text = parsedText)
        }
    }
}
