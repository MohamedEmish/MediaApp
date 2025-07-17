package com.amosh.pulse.core.ui.components.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.amosh.pulse.core.ui.components.button.SimpleLabButtonComposeConfigurations.ButtonLoadingContent
import com.amosh.pulse.core.ui.components.button.SimpleLabButtonComposeConfigurations.ButtonTextContent
import com.amosh.pulse.core.ui.components.button.SimpleLabButtonComposeConfigurations.getBorderColor
import com.amosh.pulse.core.ui.theme.bounceClick

@Composable
fun SimpleLabButtonCompose(
    modifier: Modifier = Modifier,
    buttonType: ButtonType = ButtonType.PRIMARY,
    buttonSize: ButtonSize = ButtonSize.LARGE,
    buttonColor: Color = MaterialTheme.colorScheme.primary,
    buttonDisabledColor: Color = MaterialTheme.colorScheme.surfaceDim,
    buttonStrokeColor: Color = MaterialTheme.colorScheme.primary,
    buttonStrokeDisabledColor: Color = MaterialTheme.colorScheme.surfaceDim,
    buttonText: String,
    buttonTextColor: Color = when (buttonType) {
        ButtonType.PRIMARY -> MaterialTheme.colorScheme.onPrimary
        else -> MaterialTheme.colorScheme.primary
    },
    buttonTextDisabledColor: Color = MaterialTheme.colorScheme.onPrimary,
    progressColor: Color = when (buttonType) {
        ButtonType.PRIMARY -> MaterialTheme.colorScheme.onPrimary
        else -> MaterialTheme.colorScheme.primary
    },
    isButtonEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClicked: (() -> Unit),
) {
    Button(
        modifier = modifier
            .height(buttonSize.buttonHeight)
            .bounceClick(),
        onClick = {
            when {
                !isLoading && isButtonEnabled -> onClicked.invoke()
            }
        },
        shape = RoundedCornerShape(buttonSize.cornerRadius),
        border = BorderStroke(
            width = buttonType.borderWidth,
            color = getBorderColor(
                buttonType,
                isButtonEnabled,
                buttonStrokeColor,
                buttonStrokeDisabledColor
            ) ?: Color.Transparent

        ),
        enabled = isButtonEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = when (buttonType) {
                ButtonType.PRIMARY -> buttonColor
                else -> Color.Transparent
            },
            disabledContainerColor = when (buttonType) {
                ButtonType.PRIMARY -> buttonDisabledColor
                else -> Color.Transparent
            },
        )
    ) {
        if (isLoading) {
            ButtonLoadingContent(
                buttonType = buttonType,
                buttonSize = buttonSize,
                buttonText = buttonText,
                buttonTextColor = buttonTextColor,
                buttonTextDisabledColor = buttonTextDisabledColor,
                isButtonEnabled = isButtonEnabled,
                progressColor = progressColor
            )
        } else {
            ButtonTextContent(
                buttonText = buttonText,
                buttonSize = buttonSize,
                buttonTextColor = buttonTextColor,
                buttonTextDisabledColor = buttonTextDisabledColor,
                isButtonEnabled = isButtonEnabled
            )
        }
    }
}