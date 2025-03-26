package de.sipgate.dachlatten.markdown

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.em
import androidx.compose.ui.util.fastForEach
import org.intellij.markdown.MarkdownElementType
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.flavours.gfm.GFMElementTypes
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMTokenTypes
import org.intellij.markdown.parser.MarkdownParser

internal val BoldSpanStyle = SpanStyle(fontWeight = FontWeight.Bold)
internal val ItalicSpanStyle = SpanStyle(fontStyle = FontStyle.Italic)
internal val MonospaceSpanStyle = SpanStyle(fontFamily = FontFamily.Monospace)
internal val StrikethroughSpanStyle = SpanStyle(textDecoration = TextDecoration.LineThrough)
internal val H1SPanStyle = SpanStyle(fontSize = 2.em, fontWeight = FontWeight.Bold)
internal val H2SPanStyle = SpanStyle(fontSize = 1.5.em, fontWeight = FontWeight.Bold)
internal val H3SPanStyle = SpanStyle(fontSize = 1.17.em, fontWeight = FontWeight.Bold)
internal val H4SPanStyle = SpanStyle(fontSize = 1.12.em, fontWeight = FontWeight.Bold)
internal val H5SPanStyle = SpanStyle(fontSize = 0.83.em, fontWeight = FontWeight.Bold)
internal val H6SPanStyle = SpanStyle(fontSize = 0.75.em, fontWeight = FontWeight.Bold)
internal val LinkSpanStyle = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)

data class MarkdownStyles(
    val boldSpanStyle: SpanStyle = BoldSpanStyle,
    val italicSpanStyle: SpanStyle = ItalicSpanStyle,
    val monospaceSpanStyle: SpanStyle = MonospaceSpanStyle,
    val strikethroughSpanStyle: SpanStyle = StrikethroughSpanStyle,
    val h1SpanStyle: SpanStyle = H1SPanStyle,
    val h2SpanStyle: SpanStyle = H2SPanStyle,
    val h3SpanStyle: SpanStyle = H3SPanStyle,
    val h4SpanStyle: SpanStyle = H4SPanStyle,
    val h5SpanStyle: SpanStyle = H5SPanStyle,
    val h6SpanStyle: SpanStyle = H6SPanStyle,
    val linkSpanStyle: SpanStyle = LinkSpanStyle,
)

private fun AnnotatedString.removeRange(start: Int, end: Int) =
    subSequence(0, start) + subSequence(end, length)

fun parseMarkdown(markdown: String, styles: MarkdownStyles = MarkdownStyles()): AnnotatedString {
    val tempNodesToRemoveAfter = mutableListOf<ASTNode>()
    var tempString = buildAnnotatedString {
        /*
         * Note: Because the AST is nested and has self-consistent offsets we have to apply all
         * relevant nodes to the tempString and then remove the Markdown control characters from
         * the formatted String from end to start. Otherwise the offsets won't match and everything
         * breaks!
         */
        MarkdownParser(GFMFlavourDescriptor())
            .buildMarkdownTreeFromString(markdown)
            .children
            .fastForEach { processNode(it, markdown, styles, tempNodesToRemoveAfter::add) }
    }

    tempNodesToRemoveAfter.reversed().fastForEach {
        tempString = try {
            tempString.removeRange(it.startOffset, it.endOffset)
        } catch (_: StringIndexOutOfBoundsException) {
            AnnotatedString("")
        }
    }

    return tempString
}

private fun AnnotatedString.Builder.processNode(
    node: ASTNode,
    markdown: String,
    colors: MarkdownStyles,
    tempNodesToRemoveAfter: (ASTNode) -> Unit
) {
    fun ASTNode.processOneCharMarkdown(spanStyle: SpanStyle) {
        tempNodesToRemoveAfter(children[0])
        children.fastForEach { processNode(it, markdown, colors, tempNodesToRemoveAfter) }
        tempNodesToRemoveAfter(children[children.lastIndex])
        addStyle(spanStyle, startOffset + 1, endOffset - 1)
    }

    fun ASTNode.processTwoCharMarkdown(spanStyle: SpanStyle) {
        tempNodesToRemoveAfter(children[0])
        tempNodesToRemoveAfter(children[1])
        children.fastForEach { processNode(it, markdown, colors, tempNodesToRemoveAfter) }
        tempNodesToRemoveAfter(children[children.lastIndex - 1])
        tempNodesToRemoveAfter(children[children.lastIndex])
        addStyle(spanStyle, startOffset + 2, endOffset - 2)
    }

    fun ASTNode.processHeadline(spanStyle: SpanStyle) {
        // process headline token
        val headlineToken = children[0]
        val childOffset = headlineToken.length
        tempNodesToRemoveAfter(headlineToken)

        val headlineContent = children[1]

        // remove whitespace
        val whitespaceOffset = headlineContent.children[0].length
        tempNodesToRemoveAfter(headlineContent.children[0])

        // process nodes and render headline text
        children.fastForEach { processNode(it, markdown, colors, tempNodesToRemoveAfter) }

        addStyle(spanStyle, startOffset + childOffset + whitespaceOffset, endOffset)
    }

    fun ASTNode.processInlineLink() {
        // process link text node
        val linkTextNode = children[0]
        processNode(linkTextNode, markdown, colors, tempNodesToRemoveAfter)

        // [
        tempNodesToRemoveAfter(linkTextNode.children[0])

        // link text
        //linkTextNode.children[1]

        // ]
        tempNodesToRemoveAfter(linkTextNode.children[2])

        // (
        processNode(children[1], markdown, colors, tempNodesToRemoveAfter)
        tempNodesToRemoveAfter(children[1])

        // process destination
        val linkDestinationNode = children[2]
        processNode(linkDestinationNode, markdown, colors, tempNodesToRemoveAfter)
        val linkDestination = linkDestinationNode.getTextInNode(markdown)
        tempNodesToRemoveAfter(children[2])

        // )
        processNode(children[3], markdown, colors, tempNodesToRemoveAfter)
        tempNodesToRemoveAfter(children[3])

        addLink(
            LinkAnnotation.Url(linkDestination.toString()),
            linkTextNode.startOffset + 1,
            linkTextNode.endOffset - 1
        )
        addStyle(colors.linkSpanStyle, linkTextNode.startOffset, linkTextNode.endOffset)
    }

    fun ASTNode.processHtmlTag() {
        append(getTextInNode(markdown))
        tempNodesToRemoveAfter(this)
    }

    when (node.type) {
        MarkdownTokenTypes.TEXT -> append(node.getTextInNode(markdown).toString())
        MarkdownTokenTypes.ATX_CONTENT -> append(node.getTextInNode(markdown).toString())
        MarkdownTokenTypes.WHITE_SPACE -> append(" ".repeat(node.length))
        MarkdownTokenTypes.SINGLE_QUOTE -> append("'")
        MarkdownTokenTypes.DOUBLE_QUOTE -> append("\"")
        MarkdownTokenTypes.LPAREN -> append("(")
        MarkdownTokenTypes.RPAREN -> append(")")
        MarkdownTokenTypes.LBRACKET -> append("[")
        MarkdownTokenTypes.RBRACKET -> append("]")
        MarkdownTokenTypes.LT -> append("<")
        MarkdownTokenTypes.GT -> append(">")
        MarkdownTokenTypes.COLON -> append(":")
        MarkdownTokenTypes.EXCLAMATION_MARK -> append("!")
        MarkdownTokenTypes.EMPH -> append("*")
        GFMTokenTypes.TILDE -> append("~")
        MarkdownTokenTypes.EOL -> append("\n")
        MarkdownTokenTypes.BACKTICK -> append("`")
        MarkdownTokenTypes.ATX_HEADER -> append("#".repeat(node.length))
        MarkdownElementTypes.ATX_1 -> node.processHeadline(colors.h1SpanStyle)
        MarkdownElementTypes.ATX_2 -> node.processHeadline(colors.h2SpanStyle)
        MarkdownElementTypes.ATX_3 -> node.processHeadline(colors.h3SpanStyle)
        MarkdownElementTypes.ATX_4 -> node.processHeadline(colors.h4SpanStyle)
        MarkdownElementTypes.ATX_5 -> node.processHeadline(colors.h5SpanStyle)
        MarkdownElementTypes.ATX_6 -> node.processHeadline(colors.h6SpanStyle)
        MarkdownElementTypes.LINK_DESTINATION -> append(node.getTextInNode(markdown).toString())
        MarkdownTokenTypes.HTML_TAG -> node.processHtmlTag()
        MarkdownElementTypes.INLINE_LINK -> node.processInlineLink()
        else -> {
            val nodeType = node.type
            if (nodeType is MarkdownElementType && !nodeType.isToken) {
                when (nodeType) {
                    MarkdownElementTypes.STRONG -> node.processTwoCharMarkdown(colors.boldSpanStyle)
                    MarkdownElementTypes.EMPH -> node.processOneCharMarkdown(colors.italicSpanStyle)
                    GFMElementTypes.STRIKETHROUGH -> node.processTwoCharMarkdown(colors.strikethroughSpanStyle)
                }
            } else {
                node.children.fastForEach { child ->
                    when (child.type) {
                        MarkdownElementTypes.STRONG -> child.processTwoCharMarkdown(colors.boldSpanStyle)
                        GFMElementTypes.STRIKETHROUGH ->
                            child.processTwoCharMarkdown(colors.strikethroughSpanStyle)

                        MarkdownElementTypes.EMPH -> child.processOneCharMarkdown(colors.italicSpanStyle)
                        MarkdownElementTypes.CODE_SPAN ->
                            child.processOneCharMarkdown(colors.monospaceSpanStyle)

                        else -> processNode(child, markdown, colors, tempNodesToRemoveAfter)
                    }
                }
            }
        }
    }
}

private val ASTNode.length: Int
    get() = endOffset - startOffset
