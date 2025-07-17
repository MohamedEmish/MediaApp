package com.amosh.pulse.core.ui.components.action

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import com.amosh.pulse.core.ui.components.dataViewer.SimpleLabTextViewCompose
import com.amosh.pulse.core.ui.theme.SimpleLabLabelSmall
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.core.ui.theme.white

@Composable
fun SimpleLabCheckBoxCompose(
    modifier: Modifier = Modifier,
    isCheckedState: MutableState<Boolean>,
    text: String = "",
    textColor: Color = MaterialTheme.colorScheme.primary,
    textColorDisabled: Color = MaterialTheme.colorScheme.inversePrimary,
    isEnabled: Boolean = true,
    colors: CheckboxColors = CheckboxDefaults.colors(
        checkedColor = MaterialTheme.colorScheme.primary,
        uncheckedColor = MaterialTheme.colorScheme.primary,
        checkmarkColor = white,
        disabledCheckedColor = MaterialTheme.colorScheme.inversePrimary,
        disabledUncheckedColor = MaterialTheme.colorScheme.inversePrimary,
    ),
    onCheckChange: ((checked: Boolean) -> Unit),
) {
    Row(
        modifier = modifier
            .height(MaterialTheme.spacing.large24)
            .toggleable(
                value = isCheckedState.value,
                onValueChange = {
                    isCheckedState.value = it
                },
                role = Role.Checkbox
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isCheckedState.value,
            colors = colors,
            onCheckedChange = { onCheckChange.invoke(it) },
            enabled = isEnabled,
        )
        SimpleLabTextViewCompose(
            text = text,
            textStyle = SimpleLabLabelSmall,
            textColor = when {
                isEnabled -> textColor
                else -> textColorDisabled
            },
        )
    }
}