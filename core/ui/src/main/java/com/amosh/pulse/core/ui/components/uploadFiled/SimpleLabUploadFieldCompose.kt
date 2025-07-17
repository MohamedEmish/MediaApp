package com.amosh.pulse.core.ui.components.uploadFiled

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RoundedCorner
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.amosh.pulse.core.ui.components.inputFields.SimpleLabDataInputFieldCompose
import com.amosh.pulse.core.ui.theme.colorPrimary
import com.amosh.pulse.core.ui.theme.error_600

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun SimpleLabUploadFieldCompose(
    modifier: Modifier = Modifier,
    hint: String = "",
    label: String = "",
    startIcon: ImageVector? = null,
    startIconTint: Color? = MaterialTheme.colorScheme.secondary,
    startIconBackground: ImageVector? = null,
    startIconBackgroundTint: Color? = null,
    endIcon: ImageVector? = null,
    endIconTint: Color? = MaterialTheme.colorScheme.secondary,
    endIconBackground: ImageVector? = null,
    endIconBackgroundTint: Color? = null,
    hasData: Boolean = false,
    fileDetails: String = "",

    ) {

  Box(
    modifier,
    Alignment.BottomStart
  ) {

    SimpleLabDataInputFieldCompose(
      onTextChange = {},
      hintText = when (hasData) {
        true -> fileDetails
        else -> hint
      },
      isClickableOnly = true,
      labelText = label,
      startIcon = startIcon,
      startIconTint = startIconTint,
      startIconBackground = startIconBackground,
      startIconBackgroundTint = startIconBackgroundTint,
      endIcon = endIcon ?: when (hasData) {
        true -> Icons.Filled.Close
        else -> Icons.Filled.Add
      },
      endIconTint = endIconTint,
      endIconBackground = endIconBackground ?: Icons.Filled.RoundedCorner,
      endIconBackgroundTint = endIconBackgroundTint ?: when (hasData) {
        true -> error_600
        else -> MaterialTheme.colorScheme.primary
      },
    )
  }
}