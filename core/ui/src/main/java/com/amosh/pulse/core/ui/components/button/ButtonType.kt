package com.amosh.pulse.core.ui.components.button

import androidx.compose.ui.unit.dp
import com.amosh.pulse.core.ui.theme.Spacing

enum class ButtonType {
  PRIMARY,
  SECONDARY,
  GHOST;

  val borderWidth
    get() = when (this) {
      SECONDARY -> Spacing().special1
      else -> 0.dp
    }
}