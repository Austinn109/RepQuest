package edu.apsu.repquest.dataclasses

data class Exercise(
    val id: String = "",
    val exerciseName: String,
    val sets: Int,
    val reps: Int,
    var weight: Double,
    val increment: Double = 0.0,
    val restTime: Int = 60,
    val notes: String = "",
    val goal: String = ""
)
