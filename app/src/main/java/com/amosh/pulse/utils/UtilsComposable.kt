package com.amosh.pulse.utils

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController

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