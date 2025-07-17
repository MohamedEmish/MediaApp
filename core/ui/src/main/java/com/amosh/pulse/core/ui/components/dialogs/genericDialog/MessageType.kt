package com.amosh.pulse.core.ui.components.dialogs.genericDialog

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.WarningAmber
import com.amosh.pulse.core.ui.theme.colorPrimary
import com.amosh.pulse.core.ui.theme.error_600
import com.amosh.pulse.core.ui.theme.success_600
import com.amosh.pulse.core.ui.theme.warning_600

enum class MessageType {
  INFO,
  WARNING,
  SUCCESS,
  ERROR;

  val getIcon
    get() =
      when (this) {
        INFO -> Icons.Filled.QuestionMark
        WARNING -> Icons.Filled.WarningAmber
        SUCCESS -> Icons.Filled.Check
        ERROR -> Icons.Filled.Close
      }

  val color
    get() = when (this) {
      INFO -> colorPrimary
      WARNING -> warning_600
      SUCCESS -> success_600
      ERROR -> error_600
    }
}