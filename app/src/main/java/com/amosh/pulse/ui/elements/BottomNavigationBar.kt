package com.amosh.pulse.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.currentBackStackEntryAsState
import com.amosh.pulse.common.BottomNavItem
import com.amosh.pulse.core.ui.theme.SimpleLabBodySmall
import com.amosh.pulse.core.ui.theme.error_600
import com.amosh.pulse.core.ui.theme.spacing
import com.amosh.pulse.ui.theme.white
import com.amosh.pulse.utils.LocalNavHostController

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit,
) {
    val backStackEntry = LocalNavHostController.current.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        tonalElevation = MaterialTheme.spacing.small8
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                alwaysShowLabel = true,
                label = {
                    Text(
                        text = item.name,
                        textAlign = TextAlign.Center,
                        style = SimpleLabBodySmall,
                        color = when {
                            selected -> MaterialTheme.colorScheme.secondary
                            else -> MaterialTheme.colorScheme.onPrimary
                        }
                    )
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = MaterialTheme.spacing.special4)
                    ) {
                        when {
                            item.badgeCount > 0 -> {
                                BadgedBox(
                                    badge = {
                                        Text(
                                            text = item.badgeCount.toString(),
                                            textAlign = TextAlign.Center,
                                            color = Color.White,
                                            style = SimpleLabBodySmall,
                                            modifier = Modifier
                                                .background(
                                                    error_600,
                                                    RoundedCornerShape(MaterialTheme.spacing.small8)
                                                )
                                                .padding(
                                                    top = MaterialTheme.spacing.special4 / 2,
                                                    bottom = MaterialTheme.spacing.special4 / 2,
                                                    start = MaterialTheme.spacing.special4,
                                                    end = MaterialTheme.spacing.special4
                                                )
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.name
                                    )
                                }
                            }

                            else -> {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    indicatorColor = MaterialTheme.colorScheme.inversePrimary
                )
            )
        }
    }
}