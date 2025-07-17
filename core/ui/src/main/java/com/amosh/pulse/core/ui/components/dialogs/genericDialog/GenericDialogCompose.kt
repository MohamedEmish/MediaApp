package com.amosh.pulse.core.ui.components.dialogs.genericDialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.amosh.pulse.core.ui.components.button.ButtonSize
import com.amosh.pulse.core.ui.components.button.ButtonType
import com.amosh.pulse.core.ui.components.button.SimpleLabButtonCompose
import com.amosh.pulse.core.ui.theme.SimpleLabLabelSmall
import com.amosh.pulse.core.ui.theme.SimpleLabTitleMedium
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.core.ui.theme.white

@Composable
fun GenericDialogCompose(
    openDialogState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    messageType: MessageType = MessageType.INFO,
    title: String = "",
    subTitle: String = "",
    message: String = "",
    positiveButtonText: String = "",
    negativeButtonText: String = "",
    isPositiveLoading: MutableState<Boolean>? = null,
    onPositive: () -> Unit,
    onNegative: () -> Unit,
) {
    if (openDialogState.value) {
        Dialog(onDismissRequest = { openDialogState.value = false }) {
            GenericDialogUICompose(
                modifier = modifier,
                messageType = messageType,
                title = title,
                subTitle = subTitle,
                message = message,
                positiveButtonText = positiveButtonText,
                negativeButtonText = negativeButtonText,
                isPositiveLoading = isPositiveLoading,
                onPositive = { onPositive.invoke() },
                onNegative = { onNegative.invoke() }
            )
        }
    }
}

@Composable
fun GenericDialogUICompose(
    modifier: Modifier = Modifier,
    messageType: MessageType = MessageType.INFO,
    title: String = "",
    subTitle: String = "",
    message: String = "",
    positiveButtonText: String = "",
    negativeButtonText: String = "",
    isPositiveLoading: MutableState<Boolean>? = null,
    onPositive: () -> Unit,
    onNegative: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.95f),
        shape = RoundedCornerShape(MaterialTheme.spacing.small8),
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.large24)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = RoundedCornerShape(MaterialTheme.spacing.small8),
                color = when (messageType) {
                    MessageType.INFO -> MaterialTheme.colorScheme.primary
                    else -> messageType.color
                },
                modifier = Modifier.size(MaterialTheme.spacing.xXXLarge48),
            ) {
                Image(
                    imageVector = messageType.getIcon,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.special4),
                    colorFilter = ColorFilter.tint(white)
                )
            }

            Spacer(modifier = Modifier.size(MaterialTheme.spacing.special12))

            if (title.isNotEmpty()) {
                Text(
                    text = title,
                    style = SimpleLabTitleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.size(MaterialTheme.spacing.special12))
            }

            if (subTitle.isNotEmpty()) {
                Text(
                    text = subTitle,
                    style = SimpleLabLabelSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.size(MaterialTheme.spacing.special12))
            }

            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    style = SimpleLabLabelSmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.size(MaterialTheme.spacing.special12))
            }

            if (positiveButtonText.isNotEmpty()) {
                SimpleLabButtonCompose(
                    buttonText = positiveButtonText,
                    buttonType = ButtonType.PRIMARY,
                    buttonSize = ButtonSize.SMALL,
                    isLoading = isPositiveLoading?.value ?: false,
                    buttonColor = when (messageType) {
                        MessageType.INFO -> MaterialTheme.colorScheme.primary
                        else -> messageType.color
                    }
                ) {
                    onPositive.invoke()
                }

                Spacer(modifier = Modifier.size(MaterialTheme.spacing.special4))
            }

            if (negativeButtonText.isNotEmpty()) {
                SimpleLabButtonCompose(
                    buttonText = negativeButtonText,
                    buttonTextColor = when (messageType) {
                        MessageType.INFO -> MaterialTheme.colorScheme.primary
                        else -> messageType.color
                    },
                    buttonType = ButtonType.SECONDARY,
                    buttonSize = ButtonSize.SMALL,
                    buttonStrokeColor = when (messageType) {
                        MessageType.INFO -> MaterialTheme.colorScheme.primary
                        else -> messageType.color
                    }
                ) {
                    onNegative.invoke()
                }

                Spacer(modifier = Modifier.size(MaterialTheme.spacing.special4))
            }
        }
    }
}