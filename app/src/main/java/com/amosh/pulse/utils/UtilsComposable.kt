package com.amosh.pulse.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

val LocalNavHostController = staticCompositionLocalOf<NavHostController> {
    error("NavHostController not initialized")
}

fun NavController.navigateSafe(route: String) {
    val currentRoute = currentDestination?.route
    if (currentRoute != route) {
        navigate(route)
    }
}

fun NavController.popBackStackOrElse(
    destinationRoute: String,
    inclusive: Boolean,
    func: () -> Unit,
) {
    val isPopped = popBackStack(destinationRoute, inclusive)
    if (!isPopped) func()
}

fun showToastMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


const val SERVER_DATE_PATTERN: String = "yyyy-MM-dd'T'HH:mm:ssX"
const val DEFAULT_DATE_FORMAT: String = "dd/MM/yyyy"

fun String?.formatDate(
    fromPattern: String = SERVER_DATE_PATTERN,
    toPattern: String = DEFAULT_DATE_FORMAT,
): String? {
    return if (this.isNullOrEmpty()) null else {
        val format: DateFormat = SimpleDateFormat(fromPattern, Locale.ENGLISH)
        val time = format.parse(this)
        return if (time != null) SimpleDateFormat(toPattern, Locale.ENGLISH).format(time) else null
    }
}
