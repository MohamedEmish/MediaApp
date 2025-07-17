package com.amosh.pulse.core.ui.components.action

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.amosh.pulse.core.domain.model.SimpleLabMessage
import com.amosh.pulse.core.domain.model.SimpleLabMessage.Companion.INFO
import com.amosh.pulse.core.domain.model.SimpleLabMessage.Companion.PRIMARY
import com.amosh.pulse.core.ui.extension.getMessageColor
import com.amosh.pulse.core.ui.extension.getMessageIcon
import com.amosh.pulse.core.ui.theme.SimpleLabLabelMedium
import com.amosh.pulse.core.ui.theme.spacing
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SimpleLabMessageComponent(
    message: SimpleLabMessage,
    duration: SnackbarDuration = SnackbarDuration.Short,
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    scope.launch {
        snackBarHostState.showSnackbar(message = message.message, duration = duration)
    }

    SnackbarHost(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.medium16),
        hostState = snackBarHostState,
        snackbar = { snackBarData: SnackbarData ->
            Snackbar(
                modifier = Modifier,
                containerColor = if (message.id in listOf(
                        PRIMARY,
                        INFO
                    )
                ) MaterialTheme.colorScheme.primary else message.getMessageColor()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(MaterialTheme.spacing.large24),
                        imageVector = message.getMessageIcon(),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )

                    Spacer(Modifier.size(MaterialTheme.spacing.small8))

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = snackBarData.visuals.message,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = SimpleLabLabelMedium,
                    )
                }
            }
        }
    )
}