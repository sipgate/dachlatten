package de.sipgate.dachlatten.flow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertSame

class MutableStateFlowMutatorListTest {

    private val initialValue = listOf("one", "two")
    private val mutableStateFlow = MutableStateFlow(initialValue)

    @Test
    fun returnsListAsIsIfTransformerDoesNothing() {
        mutableStateFlow.mutateList {
            // do nothing
        }
        assertSame(initialValue, mutableStateFlow.value)
    }

    @Test
    fun setsMutatedListWhenEntryIsAdded() {
        mutableStateFlow.mutateList { list ->
            list.add("three")
        }

        assertNotEquals(initialValue, mutableStateFlow.value)
        assertEquals(3, mutableStateFlow.value.size)
        assertEquals(initialValue, mutableStateFlow.value.take(2))
        assertEquals("three", mutableStateFlow.value.last())
    }

    @Test
    fun setsMutatedListWhenEntryIsRemoved() {
        mutableStateFlow.mutateList { list ->
            list.remove("two")
        }

        assertNotEquals(initialValue, mutableStateFlow.value)
        assertEquals(1, mutableStateFlow.value.size)
        assertEquals("one", mutableStateFlow.value.last())
    }
}

