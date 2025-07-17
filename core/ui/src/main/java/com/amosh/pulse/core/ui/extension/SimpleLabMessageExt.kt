package com.amosh.pulse.core.ui.extension

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.Color
import com.amosh.pulse.core.domain.model.SimpleLabMessage
import com.amosh.pulse.core.domain.model.SimpleLabMessage.Companion.FAILURE
import com.amosh.pulse.core.domain.model.SimpleLabMessage.Companion.INFO
import com.amosh.pulse.core.domain.model.SimpleLabMessage.Companion.PRIMARY
import com.amosh.pulse.core.domain.model.SimpleLabMessage.Companion.SUCCESS
import com.amosh.pulse.core.domain.model.SimpleLabMessage.Companion.WARNING
import com.amosh.pulse.core.ui.theme.colorAccent
import com.amosh.pulse.core.ui.theme.colorPrimary
import com.amosh.pulse.core.ui.theme.error_600
import com.amosh.pulse.core.ui.theme.success_600

fun SimpleLabMessage.getMessageColor(): Color = when (id) {
    FAILURE -> error_600
    WARNING -> colorAccent
    SUCCESS -> success_600
    PRIMARY, INFO -> colorPrimary
    else -> colorPrimary
}

fun SimpleLabMessage.getMessageIcon() = when (id) {
    FAILURE -> Icons.Filled.Cancel
    WARNING -> Icons.Filled.Error
    SUCCESS -> Icons.Filled.CheckCircle
    else -> Icons.Filled.Info
}