package edu.apsu.repquest

import androidx.compose.runtime.mutableStateListOf
import edu.apsu.repquest.dataclasses.Exercise
import edu.apsu.repquest.dataclasses.ExerciseStats
import edu.apsu.repquest.dataclasses.Workout
import java.util.UUID



class WorkoutRepository {
    val completedWorkouts = mutableStateListOf<Workout>()

    fun addWorkout(workoutName: String, exercises: List<Exercise>) {
        completedWorkouts.add(
            Workout(
                id = UUID.randomUUID().toString(),
                workoutName = workoutName,
                exercises = exercises,
                date = System.currentTimeMillis()
            )
        )
    }

    fun getStats(): List<ExerciseStats> {
        if (completedWorkouts.isEmpty()) return emptyList()

        val exerciseMap = mutableMapOf<String, MutableList<String>>()

        completedWorkouts.forEach { workout ->
            workout.exercises.forEach { exercise ->
                exerciseMap
                    .getOrPut(exercise.exerciseName) { mutableListOf() }
                    .add(exercise.goal)
            }
        }

        return exerciseMap.map { (name, goals) ->
            val mostUsed = goals.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key ?: "N/A"
            ExerciseStats(
                exerciseName = name,
                mostUsedGoal = mostUsed,
                highestAchieved = mostUsed
            )
        }
    }
}
