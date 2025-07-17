package com.amosh.pulse.core.ui.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.amosh.pulse.core.ui.theme.spacing

internal object SimpleLabButtonComposeConfigurations {
    @Composable
    fun ButtonLoadingContent(
        buttonType: ButtonType,
        buttonSize: ButtonSize,
        buttonText: String,
        buttonTextColor: Color,
        buttonTextDisabledColor: Color,
        isButtonEnabled: Boolean,
        progressColor: Color,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = progressColor,
                strokeWidth = MaterialTheme.spacing.special1,
                modifier = Modifier.size(buttonSize.progressSize),
            )
            if (buttonType == ButtonType.GHOST) {
                Spacer(
                    modifier = Modifier.size(MaterialTheme.spacing.special4)
                )
                Text(
                    text = buttonText,
                    style = buttonSize.textStyle,
                    color = when {
                        isButtonEnabled -> buttonTextColor
                        else -> buttonTextDisabledColor
                    },
                )
            }
        }
    }

    @Composable
    fun ButtonTextContent(
        buttonText: String,
        buttonSize: ButtonSize,
        buttonTextColor: Color,
        buttonTextDisabledColor: Color,
        isButtonEnabled: Boolean,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement =
            Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buttonText,
                style = buttonSize.textStyle,
                color = when {
                    isButtonEnabled -> buttonTextColor
                    else -> buttonTextDisabledColor
                },
            )
        }
    }

    fun getBorderColor(
        buttonType: ButtonType,
        isButtonEnabled: Boolean,
        buttonStrokeColor: Color,
        buttonStrokeDisabledColor: Color,
    ): Color? {
        return when {
            buttonType == ButtonType.SECONDARY && isButtonEnabled -> buttonStrokeColor
            buttonType == ButtonType.SECONDARY -> buttonStrokeDisabledColor
            else -> null
        }
    }
}