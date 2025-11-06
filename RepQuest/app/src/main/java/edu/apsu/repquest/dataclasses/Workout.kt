package edu.apsu.repquest.dataclasses

data class Workout(
    val id: String = "",
    val workoutName: String,
    val exercises: List<Exercise>,
    val date: Long? = null,
    val notes: String = ""
)
