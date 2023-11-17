package de.sipgate.dachlatten.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PrintFlowTest {

    @Test
    fun printFlowPassesThroughValuesTransparently() = runTest {
        val flow = flowOf("first", "second", "third")

        val result = flow
            .printFlow(printFunc = { println(it) })
            .collectAsList()

        assertEquals(listOf("first", "second", "third"), result)
    }

    @Test
    fun printFlowExecutesPrintFuncOnceForEachEmittedValue() = runTest {
        val flow = flowOf("first", "second", "third")

        val result = mutableListOf<String>()
        flow.printFlow(printFunc = { result.add(it) }).collect()

        assertEquals(listOf("first", "second", "third"), result.toList())
    }

    @Test
    fun printFlowExecutesStringFuncForEachEmittedValue() = runTest {
        val flow = flowOf("first", "second", "third")

        val result = mutableListOf<String>()
        flow.printFlow(
            printFunc = { /* dummy */ },
            stringFunc = { result.add(it!!); it }
        ).collect()

        assertEquals(listOf("first", "second", "third"), result.toList())
    }

    @Test
    fun printFlowCallsToStringByDefault() = runTest {
        data class SomeData(
            val aProperty: String,
            val bProperty: Boolean
        )

        val flow = flowOf(
            SomeData("asdf", true),
            SomeData("wah", false)
        )

        val stringBuffer = StringBuffer()
        flow.printFlow(printFunc = { stringBuffer.append(it).append("\n") })
            .collect()

        val expected = """
            SomeData(aProperty=asdf, bProperty=true)
            SomeData(aProperty=wah, bProperty=false)

        """.trimIndent()

        assertEquals(expected, stringBuffer.toString())
    }

    @Test
    fun printFlowWorksWithNullableValues() = runTest {
        val flow = flowOf("first", null, "third")

        val resultPrinted = mutableListOf<String>()
        val resultPassed = flow.printFlow(
            printFunc = { resultPrinted.add(it) }
        ).collectAsList()

        assertEquals(listOf("first", null, "third"), resultPassed)
        assertEquals(listOf("first", "null", "third"), resultPrinted)
    }

    private suspend fun <T> Flow<T>.collectAsList() =
        mutableListOf<T>().apply {
            this@collectAsList.map(this::add).collect()
        }
}
