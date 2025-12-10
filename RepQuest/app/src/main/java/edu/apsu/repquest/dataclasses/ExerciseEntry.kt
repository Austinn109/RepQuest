package edu.apsu.repquest.dataclasses

data class ExerciseEntry(
    val date: Long,
    val sets: Int,
    val reps: Int,
    val weight: Double,
    val goal: String
)
