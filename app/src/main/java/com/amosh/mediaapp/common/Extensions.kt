package com.amosh.mediaapp.common

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun ComponentActivity.onBackPressed(callback: () -> Unit) {
    onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            callback()
        }
    })
}