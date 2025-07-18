package com.amosh.pulse

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.text.layoutDirection
import androidx.navigation.compose.rememberNavController
import com.amosh.pulse.common.BottomNavItem
import com.amosh.pulse.common.KeyboardHelper
import com.amosh.pulse.common.onBackPressed
import com.amosh.pulse.core.domain.model.SimpleLabMessage
import com.amosh.pulse.core.domain.model.enums.SupportedTheme.DARK
import com.amosh.pulse.core.domain.model.enums.SupportedTheme.LIGHT
import com.amosh.pulse.core.domain.model.enums.SupportedTheme.SYSTEM
import com.amosh.pulse.core.domain.utils.isNull
import com.amosh.pulse.core.ui.components.action.SimpleLabMessageComponent
import com.amosh.pulse.ui.elements.BottomNavigationBar
import com.amosh.pulse.ui.elements.Navigation
import com.amosh.pulse.ui.ext.locale
import com.amosh.pulse.ui.screens.MainViewModel
import com.amosh.pulse.ui.theme.NavigationDestinations
import com.amosh.pulse.ui.theme.PulseTheme
import com.amosh.pulse.utils.LocalNavHostController
import com.amosh.pulse.utils.LocaleUtils.setLocale
import com.amosh.pulse.utils.navigateSafe
import com.amosh.pulse.utils.popBackStackOrElse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private val notificationsRequestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { _: Boolean -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestNotificationPermission()
        setContent {
            val currentLanguage by mainViewModel.currentLanguage.collectAsState()
            setLocale(LocalContext.current, currentLanguage.locale.language)

            val currentTheme by mainViewModel.currentTheme.collectAsState()

            if (currentLanguage.isNull().not()) {
                val navController = rememberNavController()
                var showBottomNav by remember { mutableStateOf(true) }

                LaunchedEffect(navController) {
                    navController.currentBackStackEntryFlow.collect { backStackEntry ->
                        showBottomNav = backStackEntry.destination.route in listOf(
                            NavigationDestinations.HomeScreen().name,
                            NavigationDestinations.SearchScreen().name,
                            NavigationDestinations.CommunityScreen().name,
                            NavigationDestinations.LibraryScreen().name,
                            NavigationDestinations.SettingsScreen().name,
                        )
                    }
                }

                CompositionLocalProvider(
                    LocalLayoutDirection provides
                            when (currentLanguage.locale.layoutDirection) {
                                LayoutDirection.Rtl.ordinal -> LayoutDirection.Rtl
                                else -> LayoutDirection.Ltr
                            },
                    LocalNavHostController provides navController
                ) {
                    PulseTheme(
                        darkTheme = when(currentTheme) {
                            LIGHT -> false
                            DARK -> true
                            SYSTEM -> null
                        }
                    ) {
                        LocalNavHostController.current.addOnDestinationChangedListener { _, _, _ ->
                            KeyboardHelper.hide(this@MainActivity)
                        }

                        Scaffold(
                            containerColor = MaterialTheme.colorScheme.primary,
                            bottomBar = {
                                if (showBottomNav) {
                                    BottomNavigationBar(
                                        items = listOf(
                                            BottomNavItem(
                                                name = getString(R.string.home),
                                                route = NavigationDestinations.HomeScreen().name,
                                                icon = Icons.Default.Home
                                            ),
                                            BottomNavItem(
                                                name = getString(R.string.search),
                                                route = NavigationDestinations.SearchScreen().name,
                                                icon = Icons.Default.Search
                                            ),
                                            BottomNavItem(
                                                name = getString(R.string.community),
                                                route = NavigationDestinations.CommunityScreen().name,
                                                icon = Icons.Default.PeopleAlt
                                            ),
                                            BottomNavItem(
                                                name = getString(R.string.library),
                                                route = NavigationDestinations.LibraryScreen().name,
                                                icon = Icons.Default.AccountBalance
                                            ),
                                            BottomNavItem(
                                                name = getString(R.string.settings),
                                                route = NavigationDestinations.SettingsScreen().name,
                                                icon = Icons.Default.Settings
                                            ),
                                        ),
                                        onItemClick = {
                                            navController.navigateSafe(it.route)
                                        })
                                }
                            }
                        ) { innerPadding ->
                            Navigation(Modifier.padding(innerPadding))
                        }

                        val doubleBackToExitPressedOnce = remember { mutableStateOf(false) }
                        onBackPressed {
                            KeyboardHelper.hide(this)
                            when (navController.currentDestination?.route) {
                                NavigationDestinations.HomeScreen().name -> {
                                    if (doubleBackToExitPressedOnce.value) {
                                        finish()
                                        return@onBackPressed
                                    }
                                    doubleBackToExitPressedOnce.value = true

                                    Handler(Looper.getMainLooper())
                                        .postDelayed({
                                            doubleBackToExitPressedOnce.value = false
                                        }, 3000)
                                }

                                in listOf(
                                    NavigationDestinations.SearchScreen().name,
                                    NavigationDestinations.CommunityScreen().name,
                                    NavigationDestinations.LibraryScreen().name,
                                    NavigationDestinations.SettingsScreen().name,
                                ),
                                    -> navController.popBackStackOrElse(
                                    destinationRoute = NavigationDestinations.HomeScreen().name,
                                    inclusive = false
                                ) {
                                    navController.navigateSafe(
                                        route = NavigationDestinations.HomeScreen().name,
                                    )
                                }

                                else -> navController.navigateUp()
                            }
                        }

                        if (doubleBackToExitPressedOnce.value) {
                            SimpleLabMessageComponent(
                                message = SimpleLabMessage(
                                    SimpleLabMessage.PRIMARY,
                                    stringResource(id = R.string.back_btn_message)
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationsRequestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}