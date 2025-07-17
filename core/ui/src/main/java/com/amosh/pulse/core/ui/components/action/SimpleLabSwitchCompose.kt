package com.amosh.pulse.core.ui.components.action

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amosh.pulse.core.ui.theme.platinum_400
import com.amosh.pulse.core.ui.theme.platinum_600

@Composable
fun SimpleLabSwitchCompose(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    isEnabled: Boolean = true,
    colors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
        uncheckedThumbColor = MaterialTheme.colorScheme.onPrimary,
        checkedTrackColor = MaterialTheme.colorScheme.primary,
        uncheckedTrackColor = platinum_600,
        checkedBorderColor = MaterialTheme.colorScheme.primary,
        uncheckedBorderColor = platinum_400,
    ),
    onCheckChange: ((isChecked: Boolean) -> Unit),
) {
    Switch(
        checked = isChecked,
        onCheckedChange = {
            onCheckChange.invoke(it)
        },
        modifier = modifier,
        colors = colors,
        enabled = isEnabled
    )
}