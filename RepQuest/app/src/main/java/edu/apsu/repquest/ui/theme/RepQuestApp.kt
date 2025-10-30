package edu.apsu.repquest.ui.theme

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.apsu.repquest.navigation.NavigationDestination
import edu.apsu.repquest.screens.GoalsScreen
import edu.apsu.repquest.screens.HistoryScreen
import edu.apsu.repquest.screens.SettingsScreen
import edu.apsu.repquest.screens.StatsScreen
import edu.apsu.repquest.screens.WorkoutScreen

@Composable
fun RepQuestApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            NavigationDestination.items.forEach {destination ->
                item(
                    selected = currentRoute == destination.route,
                    onClick = {
                        navController.navigate(destination.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (currentRoute == destination.route){
                                destination.selectedIcon
                            } else destination.unselectedIcon,
                            contentDescription = destination.title
                        )
                    },
                    label = { Text(destination.title) }
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = NavigationDestination.Workout.route
        ) {
            composable(NavigationDestination.Workout.route) {
                WorkoutScreen()
            }
            composable(NavigationDestination.History.route) {
                HistoryScreen()
            }
            composable(NavigationDestination.Stats.route) {
                StatsScreen()
            }
            composable(NavigationDestination.Goals.route) {
                GoalsScreen()
            }
            composable(NavigationDestination.Settings.route) {
                SettingsScreen()
            }
        }
    }
}