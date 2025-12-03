package edu.apsu.repquest.dataclasses

data class Workout(
    val id: String = "",
    val workoutName: String,
    val exercises: List<Exercise>,
    val date: Long? = null,
    val notes: String = ""
)
{
    fun makeExerciseList(): String{
        var combinedExercises: String = "[\n"
        for (exercise in exercises){
            combinedExercises += "${exercise.id},${exercise.exerciseName},${exercise.sets},${exercise.reps},${exercise.weight},${exercise.restTime},${exercise.notes}\n"
        }
        combinedExercises += "]\n"
        return combinedExercises
    }


    override fun toString(): String {
        return "${id}, ${workoutName},${makeExerciseList()},${notes}"
    }
}
