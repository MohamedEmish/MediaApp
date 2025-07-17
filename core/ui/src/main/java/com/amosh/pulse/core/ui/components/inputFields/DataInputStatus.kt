package com.amosh.pulse.core.ui.components.inputFields

import com.amosh.pulse.core.ui.theme.error_600
import com.amosh.pulse.core.ui.theme.information_600
import com.amosh.pulse.core.ui.theme.success_600
import com.amosh.pulse.core.ui.theme.warning_600

enum class DataInputStatus {
  ERROR,
  SUCCESS,
  WARNING,
  NORMAL;

  val statusColor get() = when(this) {
    ERROR -> error_600
    SUCCESS -> success_600
    WARNING -> warning_600
    NORMAL -> information_600
  }
}