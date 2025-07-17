package com.amosh.pulse.core.ui.components.dataViewer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.amosh.pulse.core.ui.theme.SimpleLabLabelMedium
import com.amosh.pulse.core.ui.theme.spacing

@Composable
fun SimpleLabTextViewCompose(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = SimpleLabLabelMedium,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    text: String,
    startIcon: ImageVector? = null,
    endIcon: ImageVector? = null,
    startIconColor: Color? = null,
    endIconColor: Color? = null,
    iconsSize: Dp = MaterialTheme.spacing.large24,
    alignEndIconToViewEnd: Boolean = false,
    textAlign: TextAlign = TextAlign.Center,
) {
    Row(
        modifier = Modifier.then(modifier),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        startIcon?.let {
            Image(
                startIcon,
                contentDescription = null,
                colorFilter = startIconColor?.let { ColorFilter.tint(color = it) },
                modifier = Modifier.size(iconsSize)
            )
            Spacer(Modifier.size(MaterialTheme.spacing.small8))
        }

        Text(
            text = text,
            color = textColor,
            style = textStyle,
            textAlign = textAlign,
        )

        endIcon?.let {
            when {
                alignEndIconToViewEnd -> Spacer(Modifier.weight(1f))
                else -> Spacer(Modifier.size(MaterialTheme.spacing.small8))
            }
            Image(
                endIcon,
                contentDescription = null,
                colorFilter = endIconColor?.let { ColorFilter.tint(color = it) },
                modifier = Modifier.size(iconsSize)
            )
        }
    }
}