package de.sipgate.dachlatten.compose.color

import androidx.compose.ui.graphics.Color
import kotlin.test.Test
import kotlin.test.assertEquals

class ColorTest {
    @Test
    fun equalPartsOfRedAndBlueResultInPurple() {
        val red = Color.Red
        val blue = Color.Blue

        val result = blend(red, blue, 0.5f)
        assertEquals(Color(128, 0, 128), result)
    }
}
