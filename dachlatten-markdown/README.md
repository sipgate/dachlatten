This library provides [Swift UI](https://developer.apple.com/documentation/swiftui/text)-like Markdown support for Compose UI. All Compose UI components that can work with [AnnotatedString](https://developer.android.com/reference/kotlin/androidx/compose/ui/text/AnnotatedString)s are supported.

```kotlin
val annotatedString = parseMarkdown(stringWithMarkdown)

setContent {
    BasicText(text = parsedText)
}
```

At the moment only basic formatting is supported. If you need more complex things, like tables and images, use Compose UI.
