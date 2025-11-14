package edu.apsu.repquest.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.apsu.repquest.dataclasses.Exercise
import edu.apsu.repquest.dataclasses.Workout
import edu.apsu.repquest.navigation.NavigationDestination

@Composable
fun WorkoutScreen(
    onCreateWorkoutClick: () -> Unit,
    onWorkoutClick: (String) -> Unit
) {
    // Sample Workout Data. DELETE Later
    val sampleWorkouts = remember {
        listOf(
            Workout(
                id = "push_day",
                workoutName = "Push Day",
                exercises = listOf(
                    Exercise(exerciseName = "Bench Press", sets = 3, reps = 10, weight = 135.0),
                    Exercise(exerciseName = "Overhead Press", sets = 3, reps = 8, weight = 95.0),
                    Exercise(exerciseName = "Dumbbell Flyes", sets = 3, reps = 12, weight = 50.0),
                    Exercise(exerciseName = "Dips", sets = 3, reps = 10, weight = 35.0),
                )
            ),
            Workout(
                id = "pull_day",
                workoutName = "Pull Day",
                exercises = listOf(
                    Exercise(exerciseName = "Deadlift", sets = 1, reps = 5, weight = 225.0),
                    Exercise(exerciseName = "Pull-ups", sets = 4, reps = 10, weight = 15.0),
                    Exercise(exerciseName = "Barbell Row", sets = 5, reps = 5, weight = 135.0),
                    Exercise(exerciseName = "Curls", sets = 3, reps = 12, weight = 95.0),
                )
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(64.dp))
        Text(
            text = "RepQuest",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = {
                onCreateWorkoutClick()
            }
        ) {
            Text(
                text = "Create A Workout"
            )
        }

        Spacer(modifier = Modifier.size(8.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(sampleWorkouts) { workout ->
                WorkoutCard(
                    workout = workout,
                    onClick = { onWorkoutClick(workout.id) }
                )
            }
        }

    }
}

@Composable
fun WorkoutCard(
    workout: Workout,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
       Column(
           modifier = Modifier
               .padding(16.dp)
       ) {
           Text(
               text = workout.workoutName,
               fontSize = 20.sp,
               fontWeight = FontWeight.Bold,
               modifier = Modifier.padding(bottom = 12.dp)
           )

           workout.exercises.forEach { exercise ->
               Row(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(vertical = 4.dp),
                   horizontalArrangement = Arrangement.SpaceBetween,
               ) {
                   Text(
                       text = exercise.exerciseName,
                       fontSize = 14.sp,
                       modifier = Modifier.weight(1f)
                   )
                   Text(
                       text = "${exercise.sets}×${exercise.reps} @ ${exercise.weight}lbs",
                       fontSize = 14.sp,
                       color = MaterialTheme.colorScheme.onSurfaceVariant
                   )
               }
           }
       }
    }
}