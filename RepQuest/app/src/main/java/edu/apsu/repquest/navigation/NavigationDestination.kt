package edu.apsu.repquest.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationDestination(
    val route: String,
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
) {
    data object Workout: NavigationDestination(
        route = "workout",
        title = "Workout",
        unselectedIcon = Icons.Outlined.FitnessCenter,
        selectedIcon = Icons.Filled.FitnessCenter
    )

    data object History: NavigationDestination(
        route = "history",
        title = "History",
        unselectedIcon = Icons.Outlined.CalendarMonth,
        selectedIcon = Icons.Filled.CalendarMonth
    )

    data object Stats: NavigationDestination(
        route = "stats",
        title = "Stats",
        unselectedIcon = Icons.Outlined.BarChart,
        selectedIcon = Icons.Filled.BarChart
    )

    data object Goals: NavigationDestination(
        route = "goals",
        title = "Goals",
        unselectedIcon = Icons.Outlined.Flag,
        selectedIcon = Icons.Filled.Flag
    )

    data object Settings: NavigationDestination(
        route = "settings",
        title = "Settings",
        unselectedIcon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings
    )

    companion object {
        val items = listOf(Workout, History, Stats, Goals, Settings)
    }
}