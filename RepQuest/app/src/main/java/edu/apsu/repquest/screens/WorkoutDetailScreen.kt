package edu.apsu.repquest.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.apsu.repquest.dataclasses.Exercise
import edu.apsu.repquest.dataclasses.Workout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    workout: Workout?,
    onNavigateBack: () -> Unit,
    onFinishedWorkout: () -> Unit,
) {
    val openAlertDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout Detail") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openAlertDialog.value = true
                },
            ) {
                Text(text = "Finished")
            }
        }
    ) { paddingValues ->

        if (openAlertDialog.value) {
            CustomAlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                dialogTitle = "Are you sure?",
                dialogText = "Confirm to finish, dismiss to continue workout.",
                onConfirmation = {
                    openAlertDialog.value = false
                    onFinishedWorkout()
                }
            )
        }

        if (workout == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text(text = "Workout not found")
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            ) {
                items(workout.exercises) { exercise ->
                    exerciseTracker(exercise = exercise)
                }
            }
        }
    }
}

@Composable
fun exerciseTracker(
    exercise: Exercise,
    modifier: Modifier = Modifier,
    activeColor: Color = Color.Blue,
    inactiveColor: Color = Color.White,
    textColor: Color = Color.Black,
    activeTextColor: Color = Color.White,
) {
    // Placeholder values for all exercise data. Replace to calls to DB using workoutId
    var initialCount = exercise.reps
    var weight by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp),
        ) {
            // Exercise name and weight completed
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = exercise.exerciseName,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                Text(
                    text = exercise.weight.toString() + " lbs",
                )

//                OutlinedTextField(
//                    modifier = Modifier
//                        .width(100.dp),
//                    value = weight,
//                    onValueChange = { weight = it },
//                    singleLine = true,
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    shape = RoundedCornerShape(8.dp)
//                )

            }

            // Button row
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                // for the number of sets of exercise
                for (i in 1..exercise.sets) {
                    var currentCount by remember { mutableStateOf(initialCount) }
                    var isActive by remember { mutableStateOf(false) }

                    Button(
                        onClick = {
                            if (!isActive) {
                                isActive = true
                            } else if (currentCount > 0) {
                                currentCount--
                            } else {
                                isActive = false
                                currentCount = initialCount
                            }
                        },
                        modifier = modifier
                            .size(50.dp)
                            .border(
                                width = 2.dp,
                                color = if (isActive) activeColor else Color.Gray,
                                shape = CircleShape
                            ),
                        shape = CircleShape,
                        colors = ButtonColors(
                            containerColor = if (isActive) activeColor else inactiveColor,
                            contentColor = if (isActive) activeTextColor else textColor,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.DarkGray
                        ),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = currentCount.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        icon = {
            Icon(Icons.Default.Info, contentDescription = "Info Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}