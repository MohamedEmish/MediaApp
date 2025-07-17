package com.amosh.mediaapp.common

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager

object KeyboardHelper {
    private const val DELAY_MILLIS = 250
    fun showKeyboard(context: Context?, view: View?) {
        if (context == null || view == null) {
            return
        }
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }, DELAY_MILLIS.toLong())
    }

    fun hide(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}