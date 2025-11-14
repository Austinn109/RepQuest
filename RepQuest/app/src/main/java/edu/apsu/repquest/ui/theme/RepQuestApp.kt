package edu.apsu.repquest.ui.theme

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.apsu.repquest.navigation.NavigationDestination
import edu.apsu.repquest.screens.CreateWorkout
import edu.apsu.repquest.screens.GoalsScreen
import edu.apsu.repquest.screens.HistoryScreen
import edu.apsu.repquest.screens.SettingsScreen
import edu.apsu.repquest.screens.StatsScreen
import edu.apsu.repquest.screens.WorkoutDetailScreen
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
                            popUpTo(0) {
                                saveState = true
                                inclusive = true
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
                WorkoutScreen(
                    onCreateWorkoutClick = {
                        navController.navigate("createWorkout")
                    },
                    onWorkoutClick = { workoutId ->
                        navController.navigate("workoutDetail/$workoutId")
                    }
                )
            }
            composable("createWorkout") {
                CreateWorkout(onNavigateBack = {
                    navController.popBackStack()
                })
            }
            composable(
                route = "workoutDetail/{workoutId}",
                arguments = listOf(navArgument("workoutId") { type = NavType.StringType})
            ) {
                backStackEntry ->
                val workoutId = backStackEntry.arguments?.getString("workoutId") ?: ""
                WorkoutDetailScreen(
                    workoutId = workoutId,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
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