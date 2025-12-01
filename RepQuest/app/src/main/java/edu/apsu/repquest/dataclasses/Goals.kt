package edu.apsu.repquest.dataclasses

import java.util.UUID

data class Goals(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val targetValue: String,
    val currentValue: String,
    val isCompleted: Boolean = false,
    val xpAwarded: Int = 0

)
