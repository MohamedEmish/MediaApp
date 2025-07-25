package com.amosh.pulse.core.ui.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

enum class ButtonState { Pressed, Idle }

fun Modifier.bounceClick() = composed {
  var buttonState by remember { mutableStateOf(ButtonState.Idle) }
  val scale by animateFloatAsState(
    targetValue = when (buttonState) {
      ButtonState.Pressed -> 0.96f
      else -> 1f
    },
    label = "BounceClick",
  )

  this
    .graphicsLayer {
      scaleX = scale
      scaleY = scale
    }
    .clickable(
      interactionSource = remember { MutableInteractionSource() },
      indication = null,
      onClick = { }
    )
    .pointerInput(buttonState) {
      awaitPointerEventScope {
        buttonState = if (buttonState == ButtonState.Pressed) {
          waitForUpOrCancellation()
          ButtonState.Idle
        } else {
          awaitFirstDown(false)
          ButtonState.Pressed
        }
      }
    }
}

fun Modifier.shakeClickEffect() = composed {
  var buttonState by remember { mutableStateOf(ButtonState.Idle) }

  val tx by animateFloatAsState(
    targetValue = when (buttonState) {
      ButtonState.Pressed -> 0f
      else -> -50f
    },
    animationSpec = repeatable(
      iterations = 2,
      animation = tween(durationMillis = 50, easing = LinearEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "shakeClickEffect"
  )
  this
    .graphicsLayer {
      translationX = tx
    }
    .clickable(
      interactionSource = remember { MutableInteractionSource() },
      indication = null,
      onClick = { }
    )
    .pointerInput(buttonState) {
      awaitPointerEventScope {
        buttonState = if (buttonState == ButtonState.Pressed) {
          waitForUpOrCancellation()
          ButtonState.Idle
        } else {
          awaitFirstDown(false)
          ButtonState.Pressed
        }
      }
    }
}

fun Modifier.pressClickEffect() = composed {
  var buttonState by remember { mutableStateOf(ButtonState.Idle) }
  val ty by animateFloatAsState(
    targetValue = when (buttonState) {
      ButtonState.Pressed -> 0f
      else -> -20f
    },
    label = "pressClickEffect"
  )

  this
    .graphicsLayer {
      translationY = ty
    }
    .clickable(
      interactionSource = remember { MutableInteractionSource() },
      indication = null,
      onClick = { }
    )
    .pointerInput(buttonState) {
      awaitPointerEventScope {
        buttonState = if (buttonState == ButtonState.Pressed) {
          waitForUpOrCancellation()
          ButtonState.Idle
        } else {
          awaitFirstDown(false)
          ButtonState.Pressed
        }
      }
    }
}