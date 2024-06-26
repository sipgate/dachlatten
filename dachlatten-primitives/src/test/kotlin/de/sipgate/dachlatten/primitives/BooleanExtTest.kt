package de.sipgate.dachlatten.primitives

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BooleanExtTest {
    @Test
    fun arrayOfAllTruesReturnsTrue() {
        val allTrues = arrayOf(true, true, true)
        assertTrue(allTrues.allTrue)
    }

    @Test
    fun arrayOfNotAllTruesReturnsFalse() {
        val someTrues = arrayOf(true, false, true)
        assertFalse(someTrues.allTrue)
    }

    @Test
    fun arrayOfNonTruesReturnsFalse() {
        val noTrues = arrayOf(false, false, false)
        assertFalse(noTrues.allTrue)
    }


    /**
     * This is needed to be compatible with the all operators
     *  "Vacuous truth"  behaviour.
     */
    @Test
    fun emptyArrayReturnsTrue() {
        val emptyArray = arrayOf<Boolean>()
        assertTrue(emptyArray.allTrue)
    }
}
