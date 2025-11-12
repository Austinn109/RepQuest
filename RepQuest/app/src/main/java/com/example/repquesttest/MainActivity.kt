package com.example.repquesttest

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.repquesttest.ui.theme.RepQuestTestTheme
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = Firebase.database

        // Initialize test data first
        initializeTestData(database)

        logWorkout(
            database = database,
            userId = "U1",
            goalId = "G1",
            type = "Running",
            duration = 45,
            date = "2025-10-07"
        )

        addExp(
            database = database,
            userId = "U1",
            expGained = 50
        )

        getGoals(database)

        setContent {
            RepQuestTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Button(onClick = {
                            logWorkout(database, "U1", "G1", "Running", 45, "2025-10-07")
                        }) {
                            Text("Log Workout")
                        }

                        Button(onClick = {
                            addExp(database, "U1", 50)
                        }) {
                            Text("Add 50 EXP")
                        }
                    }
                }
            }
        }
    }

    // Initialize test data with proper structure
    private fun initializeTestData(database: com.google.firebase.database.FirebaseDatabase) {
        // Create user with all required fields
        val userData = hashMapOf(
            "displayName" to "Test User",
            "exp" to 0,
            "totalWorkouts" to 0
        )
        database.getReference("users").child("U1").setValue(userData)

        // Create goal with all required fields
        val goalData = hashMapOf(
            "userId" to "U1",
            "title" to "Run 30 times",
            "target" to 30,
            "progress" to 0,
            "status" to "active"
        )
        database.getReference("goals").child("G1").setValue(goalData)
    }

    private fun logWorkout(
        database: com.google.firebase.database.FirebaseDatabase,
        userId: String,
        goalId: String,
        type: String,
        duration: Int,
        date: String
    ) {
        // Generate unique workout ID under workouts
        val workoutRef = database.getReference("workouts").push()
        val workoutId = workoutRef.key ?: return

        // Create workout data with all required fields
        val workoutData = hashMapOf(
            "userId" to userId,
            "goalId" to goalId,
            "type" to type,
            "duration" to duration,
            "date" to date
        )

        // Save to Firebase under workouts/{workoutId}
        workoutRef.setValue(workoutData)
            .addOnSuccessListener {
                Log.d(TAG, "Workout logged: $workoutId")

                // Update goal progress
                updateGoalProgress(database, goalId)

                // Update total workouts for user
                updateTotalWorkouts(database, userId)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to log workout", e)
            }
    }

    // Helper to update goal progress
    private fun updateGoalProgress(
        database: com.google.firebase.database.FirebaseDatabase,
        goalId: String
    ) {
        val goalRef = database.getReference("goals").child(goalId)

        goalRef.get().addOnSuccessListener { snapshot ->
            val currentProgress = snapshot.child("progress").getValue(Int::class.java) ?: 0
            goalRef.child("progress").setValue(currentProgress + 1)
        }
    }

    // Helper to update total workouts
    private fun updateTotalWorkouts(
        database: com.google.firebase.database.FirebaseDatabase,
        userId: String
    ) {
        val userRef = database.getReference("users").child(userId)

        userRef.get().addOnSuccessListener { snapshot ->
            val currentTotal = snapshot.child("totalWorkouts").getValue(Int::class.java) ?: 0
            userRef.child("totalWorkouts").setValue(currentTotal + 1)
        }
    }

    private fun addExp(
        database: com.google.firebase.database.FirebaseDatabase,
        userId: String,
        expGained: Int
    ) {
        val userRef = database.getReference("users").child(userId)

        userRef.get().addOnSuccessListener { snapshot ->
            val currentExp = snapshot.child("exp").getValue(Int::class.java) ?: 0
            val totalExp = currentExp + expGained

            userRef.child("exp").setValue(totalExp)
                .addOnSuccessListener {
                    Log.d(TAG, "EXP added. Total: $totalExp")
                }
        }
    }

    private fun getGoals(database: com.google.firebase.database.FirebaseDatabase) {
        val goalsRef = database.getReference("goals")

        goalsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (goalSnapshot in snapshot.children) {
                    val userId = goalSnapshot.child("userId").getValue(String::class.java)
                    val title = goalSnapshot.child("title").getValue(String::class.java)
                    val progress = goalSnapshot.child("progress").getValue(Int::class.java)
                    val target = goalSnapshot.child("target").getValue(Int::class.java)
                    val status = goalSnapshot.child("status").getValue(String::class.java)

                    Log.d(TAG, "Goal: $title - $progress/$target ($status) [User: $userId]")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read goals", error.toException())
            }
        })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RepQuestTestTheme {
        Greeting("Android")
    }
}