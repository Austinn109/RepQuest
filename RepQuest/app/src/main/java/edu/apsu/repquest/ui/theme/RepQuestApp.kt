package edu.apsu.repquest.ui.theme

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.apsu.repquest.dataclasses.Exercise
import edu.apsu.repquest.dataclasses.UserConfig
import edu.apsu.repquest.dataclasses.Workout
import edu.apsu.repquest.navigation.NavigationDestination
import edu.apsu.repquest.screens.CreateExercise
import edu.apsu.repquest.screens.CreateWorkout
import edu.apsu.repquest.screens.GoalsScreen
import edu.apsu.repquest.screens.HistoryScreen
import edu.apsu.repquest.screens.SettingsScreen
import edu.apsu.repquest.screens.StatsScreen
import edu.apsu.repquest.screens.WorkoutDetailScreen
import edu.apsu.repquest.screens.WorkoutScreen
import java.time.LocalDateTime


@Composable
fun RepQuestApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var pendingExercises by remember { mutableStateOf<List<Exercise>>(emptyList()) }
    var savedWorkouts by remember { mutableStateOf<List<Workout>>(emptyList())}
    var savedUserConfig by remember { mutableStateOf<UserConfig>(UserConfig())}

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
                    workouts = savedWorkouts,
                    onCreateWorkoutClick = {
                        pendingExercises = emptyList()
                        navController.navigate("createWorkout")
                    },
                    onWorkoutClick = { workoutId ->
                        navController.navigate("workoutDetail/$workoutId")
                    },
                    onDeleteWorkout = { workout ->
                        savedWorkouts = savedWorkouts.filter { it != workout }
                    }
                )
            }
            composable("createWorkout") {
                CreateWorkout(
                    exercises = pendingExercises,
                    onNavigateBack = { navController.popBackStack() },
                    onCreateExerciseClick = { navController.navigate("createExercise") },
                    onDeleteExercise = { exercise ->
                        pendingExercises = pendingExercises.filter { it != exercise }
                    },
                    onSaveWorkout = { workoutName, exercises ->
                        val newWorkout = Workout(
                            id = LocalDateTime.now().toString(),
                            workoutName = workoutName,
                            exercises = exercises,
                        )
                        savedWorkouts = savedWorkouts + newWorkout
                        pendingExercises = emptyList()
                        navController.popBackStack()
                    }
                )
            }
            composable("createExercise") {
                CreateExercise(
                    onNavigateBack = { navController.popBackStack() },
                    onExerciseCreated = { exercise ->
                        pendingExercises = pendingExercises + exercise
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = "workoutDetail/{workoutId}",
                arguments = listOf(navArgument("workoutId") { type = NavType.StringType})
            ) {
                backStackEntry ->
                val workoutId = backStackEntry.arguments?.getString("workoutId") ?: ""
                val workout = savedWorkouts.find { it.id == workoutId }
                WorkoutDetailScreen(
                    workout = workout,
                    onNavigateBack = { navController.popBackStack() },
                    onFinishedWorkout = { navController.popBackStack() },
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
                SettingsScreen(
                    onSaveClick = { userConfig ->
                        savedUserConfig.distanceUnit = userConfig.distanceUnit
                        savedUserConfig.timeUnit = userConfig.timeUnit
                        savedUserConfig.vibrate = userConfig.vibrate
                        savedUserConfig.chime = userConfig.chime
                    }
                )
            }
        }
    }
}