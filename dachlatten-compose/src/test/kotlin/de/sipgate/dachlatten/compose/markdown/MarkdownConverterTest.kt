package de.sipgate.dachlatten.compose.markdown

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.util.fastJoinToString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MarkdownConverterTest {

    @Test
    fun simpleMarkdownTest() {
        val markdownWithSpans = "some *text* with **bold** parts"
        val parsedText = parseMarkdown(markdownWithSpans)
        assertEquals("some text with bold parts", parsedText.text)
    }

    @Test
    fun checkItalicTextIsItalic() {
        val markdownWithSpans = "some *italic* text"
        val parsedText = parseMarkdown(markdownWithSpans)
        val italicSpan = parsedText.spanStyles.first { it.item.fontStyle == FontStyle.Italic }
        assertEquals("italic", parsedText.slice(italicSpan))
    }

    @Test
    fun checkBoldTextIsBold() {
        val markdownWithSpans = "some **bold** text"
        val parsedText = parseMarkdown(markdownWithSpans)
        val boldSpan = parsedText.spanStyles.first { it.item.fontWeight == FontWeight.Bold }
        assertEquals("bold", parsedText.slice(boldSpan))
    }

    @Test
    fun checkRegressionWhereDoubleSpaceBeforeBoldBreaksOffsetCalculations() {
        val markdownWithSpans = "some  **bold** text"
        val parsedText = parseMarkdown(markdownWithSpans)
        val boldSpan = parsedText.spanStyles.first { it.item.fontWeight == FontWeight.Bold }
        assertEquals("bold", parsedText.slice(boldSpan))
    }

    @Test
    fun checkRegressionWhereDashesBreakParsing() {
        val markdownWithSpans = "some **bold text with- dashes** inside"
        val parsedText = parseMarkdown(markdownWithSpans)
        val boldSpan = parsedText.spanStyles.first { it.item.fontWeight == FontWeight.Bold }
        assertEquals("bold text with- dashes", parsedText.slice(boldSpan))
    }

    @Test
    fun checkBoldAndItalicTextIsBoldAndItalic() {
        val markdownWithSpans = "some ***bold and italic*** text"
        val parsedText = parseMarkdown(markdownWithSpans)
        val italicSpan = parsedText.spanStyles.first { it.item.fontStyle == FontStyle.Italic }
        val boldSpan = parsedText.spanStyles.first { it.item.fontWeight == FontWeight.Bold }
        assertEquals("bold and italic", parsedText.slice(italicSpan))
        assertEquals("bold and italic", parsedText.slice(boldSpan))
    }

    @Test
    fun checkBoldAndStrikeThroughTextIsBoldAndStrikethrough() {
        val markdownWithSpans = "some **bold and ~~strikethrough~~** text"
        val parsedText = parseMarkdown(markdownWithSpans)
        val strikethroughSpan = parsedText.spanStyles.first { it.item.textDecoration == TextDecoration.LineThrough }
        val boldSpans = parsedText.spanStyles.filter { it.item.fontWeight == FontWeight.Bold }
        assertEquals("bold and ", parsedText.slice(boldSpans[0]))
        assertEquals("strikethrough", parsedText.slice(boldSpans[1]))
        assertEquals("strikethrough", parsedText.slice(strikethroughSpan))
    }

    @Test
    fun checkBoldAndItalicMixedIsParsedCorrectly() {
        val markdownWithSpans = "some **bold and *inner italic* text** works"
        val parsedText = parseMarkdown(markdownWithSpans)
        val italicSpan = parsedText.spanStyles.first { it.item.fontStyle == FontStyle.Italic }
        val boldSpans = parsedText.spanStyles.filter { it.item.fontWeight == FontWeight.Bold }
        assertEquals("bold and ", parsedText.slice(boldSpans[0]))
        assertEquals("inner italic", parsedText.slice(boldSpans[1]))
        assertEquals(" text", parsedText.slice(boldSpans[2]))
        assertEquals("inner italic", parsedText.slice(italicSpan))
    }

    @Test
    fun checkParagraphs() {
        val markdownWithParagraph = "some asdf\n\na"

        val parsedText = parseMarkdown(markdownWithParagraph)

        assertEquals("some asdf\n\na", parsedText.text)
    }

    @Test
    fun checkH1HeadlinesWork() {
        val markdownWithH1Headline = "some asdf\n\n# headline"

        val parsedText = parseMarkdown(markdownWithH1Headline)

        assertEquals("some asdf\n\nheadline", parsedText.text)
    }

    @Test
    fun checkH2HeadlinesWork() {
        val markdownWithH2Headline = "some asdf\n\n## headline"

        val parsedText = parseMarkdown(markdownWithH2Headline)

        assertEquals("some asdf\n\nheadline", parsedText.text)
    }

    @Test
    fun checkH3HeadlinesWork() {
        val markdownWithH3Headline = "some asdf\n\n### headline"

        val parsedText = parseMarkdown(markdownWithH3Headline)

        assertEquals("some asdf\n\nheadline", parsedText.text)
    }

    @Test
    fun checkH4HeadlinesWork() {
        val markdownWithH4Headline = "some asdf\n\n#### headline"

        val parsedText = parseMarkdown(markdownWithH4Headline)

        assertEquals("some asdf\n\nheadline", parsedText.text)
    }

    @Test
    fun checkH5HeadlinesWork() {
        val markdownWithH5Headline = "some asdf\n\n##### headline"

        val parsedText = parseMarkdown(markdownWithH5Headline)

        assertEquals("some asdf\n\nheadline", parsedText.text)
    }

    @OptIn(ExperimentalTextApi::class)
    @Test
    fun checkInlineLinksWork() {
        val markdownWithLink = "phone is [sipgate](https://sipgate.de) is phone"

        val parsedText = parseMarkdown(markdownWithLink)

        assertEquals("phone is sipgate is phone", parsedText.text)
        val urls = parsedText.getUrlAnnotations(0, parsedText.text.length)
        assertEquals("https://sipgate.de", urls.first().item.url)
    }

    @Test
    fun checkInlineMonospaceWorks() {
        val markdown = "Es ist eine `monospaced` Welt"

        val parsedText = parseMarkdown(markdown)
        val monospaceSpan =
            parsedText.spanStyles.first { it.item.fontFamily == FontFamily.Monospace }
        assertEquals("monospaced", parsedText.slice(monospaceSpan))
    }

    @Test
    fun smokeTestWithSeveralFeaturesCombined() {
        val markdown =
            "Es geht *rund* und **rund** und ***rund***. Es ist eine `monospaced` Welt, " +
                "in der ~~wir leben~~ und wir kaufen Sachen von [Apple](https://apple.com)."

        parseMarkdown(markdown)
    }
}


private fun AnnotatedString.slice(span: AnnotatedString.Range<SpanStyle>): String =
    text.substring(span.start, span.end)

@Suppress("unused")
private val AnnotatedString.debugContents: String
    get() = "text: $text\n" + "spans: ${debugSpannables.fastJoinToString("\n")}"

private val AnnotatedString.debugSpannables: List<String>
    get() {
        return spanStyles.mapIndexed { i, spanStyle ->
            val substring = text.substring(spanStyle.start, spanStyle.end)
            val style = spanStyle.item
            "$i: \"$substring\" with style: $style"
        }
    }

