package com.amosh.pulse.core.ui.components.dataViewer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.amosh.pulse.core.ui.theme.SimpleLabBodyMedium
import com.amosh.pulse.core.ui.theme.SimpleLabTitleMedium
import com.amosh.pulse.core.ui.theme.spacing

@Composable
fun SimpleLabDataViewerFieldCompose(
    modifier: Modifier = Modifier,
    orientation: DataViewerOrientation,
    labelText: String,
    labelColor: Color = MaterialTheme.colorScheme.secondary,
    valueText: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    when (orientation) {
        DataViewerOrientation.VERTICAL -> Column(
            modifier = modifier,
            horizontalAlignment = Alignment.Start,
        ) {
            AnatDataViewerFieldContent(
                orientation,
                labelText,
                labelColor,
                valueText,
                valueColor
            )
        }

        DataViewerOrientation.HORIZONTAL -> Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnatDataViewerFieldContent(
                orientation,
                labelText,
                labelColor,
                valueText,
                valueColor
            )
        }
    }
}

@Composable
private fun AnatDataViewerFieldContent(
    orientation: DataViewerOrientation,
    labelText: String,
    labelColor: Color,
    valueText: String,
    valueColor: Color,
) {
    Text(text = labelText, color = labelColor, style = SimpleLabBodyMedium)
    if (orientation == DataViewerOrientation.HORIZONTAL) {
        Spacer(modifier = Modifier.size(MaterialTheme.spacing.special4))
    }
    Text(text = valueText, color = valueColor, style = SimpleLabTitleMedium)
}
