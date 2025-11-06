package edu.apsu.repquest.dataclasses

data class Exercise(
    val id: String = "",
    val exerciseName: String,
    val sets: Int,
    val reps: Int,
    val weight: Double,
    val restTime: Int = 60,
    val notes: String = ""
)
