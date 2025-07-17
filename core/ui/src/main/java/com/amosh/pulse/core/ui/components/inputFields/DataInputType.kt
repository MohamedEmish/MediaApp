package com.amosh.pulse.core.ui.components.inputFields

import android.text.InputType
import androidx.compose.ui.text.input.KeyboardType

enum class DataInputType {
  TEXT,
  PASSWORD,
  DATE,
  EMAIL,
  NUMBER,
  PHONE_NUMBER;

  val keyBoardInputType
    get() = when (this) {
      TEXT -> KeyboardType.Text
      PASSWORD -> KeyboardType.Password
      DATE -> KeyboardType.Text
      EMAIL -> KeyboardType.Email
      NUMBER -> KeyboardType.Number
      PHONE_NUMBER -> KeyboardType.Phone
    }

  val inputType
    get() = when (this) {
      TEXT -> InputType.TYPE_CLASS_TEXT
      PASSWORD -> InputType.TYPE_TEXT_VARIATION_PASSWORD
      DATE -> InputType.TYPE_CLASS_DATETIME
      EMAIL -> InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
      NUMBER -> InputType.TYPE_CLASS_NUMBER
      PHONE_NUMBER -> InputType.TYPE_CLASS_PHONE
    }

}