package de.sipgate.dachlatten.compose.markdown

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
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
internal val CodeSpanStyle = SpanStyle(fontFamily = FontFamily.Monospace)
internal val StrikethroughSpanStyle = SpanStyle(textDecoration = TextDecoration.LineThrough)
internal val H1SPanStyle = SpanStyle(fontSize = 2.em, fontWeight = FontWeight.Bold)
internal val H2SPanStyle = SpanStyle(fontSize = 1.5.em, fontWeight = FontWeight.Bold)
internal val H3SPanStyle = SpanStyle(fontSize = 1.17.em, fontWeight = FontWeight.Bold)
internal val H4SPanStyle = SpanStyle(fontSize = 1.12.em, fontWeight = FontWeight.Bold)
internal val H5SPanStyle = SpanStyle(fontSize = 0.83.em, fontWeight = FontWeight.Bold)
internal val H6SPanStyle = SpanStyle(fontSize = 0.75.em, fontWeight = FontWeight.Bold)
internal val LinkStyle = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)

private fun AnnotatedString.removeRange(start: Int, end: Int) =
    subSequence(0, start) + subSequence(end, length)

fun parseMarkdown(markdown: String): AnnotatedString {
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
            .fastForEach { processNode(it, markdown, tempNodesToRemoveAfter) }
    }

    tempNodesToRemoveAfter.sortedByDescending(ASTNode::endOffset).fastForEach {
        tempString = tempString.removeRange(it.startOffset, it.endOffset)
    }

    return tempString
}

@OptIn(ExperimentalTextApi::class)
private fun AnnotatedString.Builder.processNode(
    node: ASTNode,
    markdown: String,
    tempNodesToRemoveAfter: MutableList<ASTNode>
) {
    fun ASTNode.processOneCharMarkdown(spanStyle: SpanStyle) {
        children.fastForEach { processNode(it, markdown, tempNodesToRemoveAfter) }
        tempNodesToRemoveAfter.add(children[0])
        tempNodesToRemoveAfter.add(children[children.lastIndex])
        addStyle(spanStyle, startOffset, endOffset)
    }

    fun ASTNode.processTwoCharMarkdown(spanStyle: SpanStyle) {
        children.fastForEach { processNode(it, markdown, tempNodesToRemoveAfter) }
        tempNodesToRemoveAfter.add(children[0])
        tempNodesToRemoveAfter.add(children[1])
        tempNodesToRemoveAfter.add(children[children.lastIndex-1])
        tempNodesToRemoveAfter.add(children[children.lastIndex])
        addStyle(spanStyle, startOffset, endOffset)
    }

    fun ASTNode.processHeadline(spanStyle: SpanStyle) {
        children.fastForEach { processNode(it, markdown, tempNodesToRemoveAfter) }
        tempNodesToRemoveAfter.add(children[0])
        if (children[1].children[0].type == MarkdownTokenTypes.WHITE_SPACE) {
            tempNodesToRemoveAfter.add(children[1].children[0])
        }
        addStyle(spanStyle, startOffset, endOffset)
    }

    fun ASTNode.processInlineLink() {
        children.fastForEach { processNode(it, markdown, tempNodesToRemoveAfter) }
        tempNodesToRemoveAfter.addAll(children.filterIndexed { i, _ -> i != 0 })
        val labelParentNode = children[0]

        tempNodesToRemoveAfter.addAll(labelParentNode.children.filterIndexed { i, _ -> i != 1 })
        val labelNode = labelParentNode.children[1]

        addUrlAnnotation(
            UrlAnnotation(children[2].getTextInNode(markdown).toString()),
            labelNode.startOffset,
            labelNode.endOffset
        )
        addStyle(LinkStyle, labelNode.startOffset, labelNode.endOffset)
    }

    when (node.type) {
        MarkdownTokenTypes.TEXT -> append(node.getTextInNode(markdown).toString())
        MarkdownTokenTypes.ATX_CONTENT -> append(node.getTextInNode(markdown).toString())
        MarkdownTokenTypes.WHITE_SPACE -> append(" ".repeat(node.endOffset - node.startOffset))
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
        MarkdownTokenTypes.ATX_HEADER -> append("#".repeat(node.endOffset - node.startOffset))
        MarkdownElementTypes.ATX_1 -> node.processHeadline(H1SPanStyle)
        MarkdownElementTypes.ATX_2 -> node.processHeadline(H2SPanStyle)
        MarkdownElementTypes.ATX_3 -> node.processHeadline(H3SPanStyle)
        MarkdownElementTypes.ATX_4 -> node.processHeadline(H4SPanStyle)
        MarkdownElementTypes.ATX_5 -> node.processHeadline(H5SPanStyle)
        MarkdownElementTypes.ATX_6 -> node.processHeadline(H6SPanStyle)
        MarkdownElementTypes.LINK_DESTINATION -> append(node.getTextInNode(markdown).toString())
        else -> {
            val nodeType = node.type
            if (nodeType is MarkdownElementType && !nodeType.isToken) {
                when (nodeType) {
                    MarkdownElementTypes.STRONG -> node.processTwoCharMarkdown(BoldSpanStyle)
                }
            } else {
                node.children.fastForEach { child ->
                    when (child.type) {
                        MarkdownElementTypes.STRONG -> child.processTwoCharMarkdown(BoldSpanStyle)
                        GFMElementTypes.STRIKETHROUGH ->
                            child.processTwoCharMarkdown(StrikethroughSpanStyle)

                        MarkdownElementTypes.EMPH -> child.processOneCharMarkdown(ItalicSpanStyle)
                        MarkdownElementTypes.INLINE_LINK -> child.processInlineLink()
                        MarkdownElementTypes.CODE_SPAN ->
                            child.processOneCharMarkdown(CodeSpanStyle)

                        else -> processNode(child, markdown, tempNodesToRemoveAfter)
                    }
                }
            }
        }
    }
}
