package edu.apsu.repquest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.apsu.repquest.WorkoutRepository
import edu.apsu.repquest.dataclasses.ExerciseEntry
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StatsScreen(
    repository: WorkoutRepository,
    onExerciseClick: (String) -> Unit
) {
    val exerciseStats = remember(repository.completedWorkouts.size) {
        repository.getStats()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(64.dp))
        Text(
            text = "Statistics",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.size(32.dp))

        if (exerciseStats.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "No statistics yet",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Complete the workouts to see your progress",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(exerciseStats) { stats ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        onClick = { onExerciseClick(stats.exerciseName) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = stats.exerciseName,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = "Most Used",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = stats.mostUsedGoal,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "Highest",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = stats.highestAchieved,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseTrendsScreen(
    exerciseName: String,
    repository: WorkoutRepository,
    onNavigateBack: () -> Unit
) {
    val exerciseHistory = remember(repository.completedWorkouts.size) {
        repository.completedWorkouts
            .flatMap { workout ->
                workout.exercises
                    .filter { it.exerciseName == exerciseName }
                    .map { exercise ->
                        ExerciseEntry(
                            date = workout.date ?: System.currentTimeMillis(),
                            sets = exercise.sets,
                            reps = exercise.reps,
                            weight = exercise.weight,
                            goal = exercise.goal
                        )
                    }
            }
            .sortedByDescending { it.date }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onNavigateBack) {
            Text("← Back", fontSize = 16.sp)
        }
        
        Text(exerciseName, fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Simple stats
        Text("Sessions: ${exerciseHistory.size}", fontSize = 16.sp)
        Text("Max Weight: ${exerciseHistory.maxOfOrNull { it.weight } ?: 0.0} lbs", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        //bar chart
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            exerciseHistory.take(5).reversed().forEach { entry ->
                Box(
                    modifier = Modifier
                        .width(30.dp)
                        .height((entry.weight * 2).coerceAtMost(100.0).dp)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("History", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(exerciseHistory) { entry ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(entry.date)))
                        Text("${entry.sets}×${entry.reps} @ ${entry.weight} lbs")
                    }
                }
            }
        }
    }
}
