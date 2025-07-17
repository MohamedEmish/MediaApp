package com.amosh.pulse.core.ui.components.inputFields

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.amosh.pulse.core.ui.theme.SimpleLabLabelMedium
import com.amosh.pulse.core.ui.theme.SimpleLabLabelSmall
import com.amosh.pulse.core.ui.theme.SimpleLabTitleSmall
import com.amosh.pulse.core.ui.theme.spacing

@Composable
fun SimpleLabDataInputFieldCompose(
    modifier: Modifier = Modifier,
    dataInputType: DataInputType = DataInputType.TEXT,
    dataInputStatus: DataInputStatus = DataInputStatus.NORMAL,
    hintText: String = "",
    labelText: String = "",
    value: String = "",
    isEnabled: Boolean = true,
    isClickableOnly: Boolean = false,
    isLoading: Boolean = false,
    startIcon: ImageVector? = null,
    startIconTint: Color? = MaterialTheme.colorScheme.secondary,
    startIconBackground: ImageVector? = null,
    startIconBackgroundTint: Color? = null,
    endIcon: ImageVector? = null,
    endIconTint: Color? = MaterialTheme.colorScheme.secondary,
    endIconBackground: ImageVector? = null,
    endIconBackgroundTint: Color? = null,
    helperText: String = "",
    outLineColors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = when {
            isClickableOnly -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.secondary
        },
        unfocusedTextColor = MaterialTheme.colorScheme.primary,
        disabledTextColor = MaterialTheme.colorScheme.inversePrimary,
        cursorColor = MaterialTheme.colorScheme.primary,
        focusedBorderColor = when {
            isClickableOnly -> MaterialTheme.colorScheme.secondary
            else -> MaterialTheme.colorScheme.primary
        },
        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        disabledBorderColor = MaterialTheme.colorScheme.inversePrimary,
    ),
    onClick: (s: String) -> Unit = { },
    onTextChange: (s: String) -> Unit,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            textStyle = SimpleLabLabelMedium.copy(
                color = if (isFocused) outLineColors.focusedTextColor
                else outLineColors.unfocusedTextColor
            ),
            value = value,
            label = {
                Text(
                    text = labelText,
                    style = SimpleLabLabelSmall,
                    color = if (isFocused) outLineColors.focusedTextColor
                    else outLineColors.unfocusedTextColor
                )
            },
            onValueChange = onTextChange,
            readOnly = isClickableOnly,
            interactionSource = if (isClickableOnly) interactionSource else null,
            enabled = isEnabled,
            keyboardOptions = KeyboardOptions(
                keyboardType = dataInputType.keyBoardInputType
            ),
            placeholder = {
                Text(
                    text = hintText,
                    style = SimpleLabTitleSmall,
                    color = MaterialTheme.colorScheme.inversePrimary
                )
            },
            visualTransformation = when (dataInputType) {
                DataInputType.PASSWORD -> when {
                    passwordVisible -> VisualTransformation.None
                    else -> PasswordVisualTransformation()
                }

                else -> VisualTransformation.None
            },
            leadingIcon = when {
                startIcon != null -> {
                    {
                        ImageWithBackground(
                            modifier = modifier.size(MaterialTheme.spacing.large24),
                            background = startIconBackground,
                            backgroundTint = startIconBackgroundTint,
                            icon = startIcon,
                            iconTint = startIconTint
                        )
                    }
                }

                else -> null
            },
            trailingIcon = when {
                dataInputType == DataInputType.PASSWORD -> {
                    {
                        PasswordToggleIndicator(
                            passwordVisible
                        ) { passwordVisible = it }
                    }
                }


                isLoading -> {
                    {
                        CircularProgressIndicator(
                            modifier = modifier.size(MaterialTheme.spacing.large24),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = MaterialTheme.spacing.special1
                        )
                    }
                }

                endIcon != null -> {
                    {
                        ImageWithBackground(
                            modifier = modifier.size(MaterialTheme.spacing.large24),
                            background = endIconBackground,
                            backgroundTint = endIconBackgroundTint,
                            icon = endIcon,
                            iconTint = endIconTint
                        )
                    }
                }

                else -> null
            },
            singleLine = true,
            colors = outLineColors
        )

        if (interactionSource.collectIsPressedAsState().value) {
            if (isClickableOnly) onClick.invoke(value)
        }

        if (helperText.isNotEmpty()) {
            Spacer(modifier = Modifier.size(MaterialTheme.spacing.special4))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium16)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = helperText,
                    style = SimpleLabLabelMedium,
                    color = when (dataInputStatus) {
                        DataInputStatus.NORMAL -> MaterialTheme.colorScheme.primary
                        else -> dataInputStatus.statusColor
                    }
                )
            }
        }
    }
}