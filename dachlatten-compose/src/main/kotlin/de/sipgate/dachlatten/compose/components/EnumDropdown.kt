package de.sipgate.dachlatten.compose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import de.sipgate.dachlatten.compose.ComposableFunc
import kotlin.enums.enumEntries

private typealias ItemLabelFunc<T> = @Composable (T) -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public inline fun <reified T: Enum<T>>  EnumDropdown(
    modifier: Modifier = Modifier,
    items: List<T>,
    initial: T = enumEntries<T>().first(),
    noinline label: ComposableFunc? = null,
    crossinline itemLabelFunc: ItemLabelFunc<T> = { item: T ->
        Text(text = item.name)
    },
    crossinline onSelectionChanged: (T) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(initial) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            @Suppress("AssignedValueIsNeverRead")
            expanded = it
        }
    ) {
        val trailingIcon = @Composable { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }

        TextField(
            modifier = Modifier.fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
            value = selectedItem.name,
            onValueChange = {},
            readOnly = true,
            trailingIcon = trailingIcon,
            label = label,
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { itemLabelFunc(item) },
                    onClick = {
                        selectedItem = item
                        onSelectionChanged(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
