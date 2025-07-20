package com.amosh.pulse.ui.elements

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.amosh.pulse.ui.screens.communityScreen.CommunityScreen
import com.amosh.pulse.media.ui.screens.homeScreen.HomeScreen
import com.amosh.pulse.media.ui.screens.homeScreen.HomeViewModel
import com.amosh.pulse.ui.screens.libraryScreen.LibraryScreen
import com.amosh.pulse.media.ui.screens.searchScreen.SearchScreen
import com.amosh.pulse.ui.screens.settingsScreen.SettingsScreen
import com.amosh.pulse.ui.theme.NavigationDestinations
import com.amosh.pulse.utils.LocalNavHostController

const val NAVIGATION_ANIMATION_DURATION = 300

@Composable
fun Navigation(
    modifier: Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val layoutDirection = LocalLayoutDirection.current
    val startOffset = if (layoutDirection == LayoutDirection.Rtl) -1000 else 1000
    val endOffset = -startOffset

    Box(modifier = modifier.fillMaxSize()) {
        // Your navigation content
        NavHost(
            navController = LocalNavHostController.current,
            startDestination = NavigationDestinations.HomeScreen().name,
            enterTransition = {
                slideIn(
                    initialOffset = { IntOffset(startOffset, 0) },
                    animationSpec = tween(NAVIGATION_ANIMATION_DURATION)
                )
            },
            exitTransition = {
                slideOut(
                    targetOffset = { IntOffset(endOffset, 0) },
                    animationSpec = tween(NAVIGATION_ANIMATION_DURATION)
                )
            },
            popEnterTransition = {
                slideIn(
                    initialOffset = { IntOffset(endOffset, 0) },
                    animationSpec = tween(NAVIGATION_ANIMATION_DURATION)
                )
            },
            popExitTransition = {
                slideOut(
                    targetOffset = { IntOffset(startOffset, 0) },
                    animationSpec = tween(NAVIGATION_ANIMATION_DURATION)
                )
            },

            ) {

            composable(NavigationDestinations.HomeScreen().name) {
                HomeScreen(homeViewModel)
            }

            composable(NavigationDestinations.SearchScreen().name) {
                SearchScreen()
            }

            composable(NavigationDestinations.CommunityScreen().name) {
                CommunityScreen()
            }

            composable(NavigationDestinations.LibraryScreen().name) {
                LibraryScreen()
            }

            composable(NavigationDestinations.SettingsScreen().name) {
                SettingsScreen()
            }

        }
    }
}