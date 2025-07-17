package com.amosh.pulse.core.ui.components.button

import com.amosh.pulse.core.ui.theme.SimpleLabTitleLarge
import com.amosh.pulse.core.ui.theme.SimpleLabTitleMedium
import com.amosh.pulse.core.ui.theme.SimpleLabTitleSmall
import com.amosh.pulse.core.ui.theme.Spacing

enum class ButtonSize {
  LARGE,
  MEDIUM,
  SMALL;

  private val spacing get() = Spacing()

  val textStyle
    get() = when (this) {
      LARGE -> SimpleLabTitleLarge
      MEDIUM -> SimpleLabTitleMedium
      SMALL -> SimpleLabTitleSmall
    }

  val buttonHeight
    get() = when (this) {
      LARGE -> spacing.xXXLarge48
      MEDIUM -> spacing.special44
      SMALL -> spacing.xXLarge40
    }

  val cornerRadius
    get() = when (this) {
      LARGE -> spacing.special12
      MEDIUM -> spacing.special10
      SMALL -> spacing.small8
    }

  val progressSize
    get() = when (this) {
      LARGE -> spacing.medium16
      MEDIUM -> spacing.special14
      SMALL -> spacing.special12
    }
}