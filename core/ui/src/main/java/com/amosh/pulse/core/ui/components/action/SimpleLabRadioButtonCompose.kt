package com.amosh.pulse.core.ui.components.action

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.amosh.pulse.core.ui.theme.SimpleLabTitleMedium
import com.amosh.pulse.core.ui.theme.spacing

@Composable
fun SimpleLabRadioButtonCompose(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = MaterialTheme.colorScheme.primary,
    textColorDisabled: Color = MaterialTheme.colorScheme.inversePrimary,
    isSelected: Boolean = false,
    isEnabled: Boolean = true,
    colors: RadioButtonColors = RadioButtonDefaults.colors(
        selectedColor = MaterialTheme.colorScheme.primary,
        unselectedColor = MaterialTheme.colorScheme.primary,
        disabledSelectedColor = MaterialTheme.colorScheme.inversePrimary,
        disabledUnselectedColor = MaterialTheme.colorScheme.inversePrimary,
    ),
    onCheckChange: (() -> Unit),
) {
    val (selectedState, onStateChange) = remember { mutableStateOf(isSelected) }
    Row(
        modifier = modifier
            .height(MaterialTheme.spacing.large24)
            .toggleable(
                value = selectedState,
                onValueChange = { onStateChange(!selectedState) },
                role = Role.Checkbox
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedState,
            colors = colors,
            onClick = { onCheckChange.invoke() },
            enabled = isEnabled,
        )
        Text(
            text = text,
            color = when {
                isEnabled -> textColor
                else -> textColorDisabled
            },
            style = SimpleLabTitleMedium,
        )
    }
}