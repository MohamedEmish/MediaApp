package com.amosh.pulse.ui.theme

sealed class NavigationDestinations(
    val name: String = "",
) {
    class HomeScreen(name: String = "MainScreen") : NavigationDestinations(name)
    class SearchScreen(name: String = "SearchScreen") : NavigationDestinations(name)
    class CommunityScreen(name: String = "CommunityScreen") : NavigationDestinations(name)
    class LibraryScreen(name: String = "LibraryScreen") : NavigationDestinations(name)
    class SettingsScreen(name: String = "SettingsScreen") : NavigationDestinations(name)

}