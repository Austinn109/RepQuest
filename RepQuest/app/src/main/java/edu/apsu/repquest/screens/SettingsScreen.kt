package edu.apsu.repquest.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.apsu.repquest.dataclasses.Exercise
import edu.apsu.repquest.dataclasses.Workout

@Composable
fun SettingsScreen() {
    var selectedTimeOption by remember { mutableStateOf(DataManager.timeOption) }
    val timeMeasureOptions = listOf("seconds", "minutes")

    var selectedDistanceOption by remember { mutableStateOf(DataManager.measurementOption) }
    val distanceMeasureOptions = listOf("Imperial", "Metric")

    var vibrationsChecked by remember { mutableStateOf(true) }

    var chimeChecked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(64.dp))
        Text(
            text = "Settings",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.size(64.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Time Measurement",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            CustomDropdownMenu(
                selectedOption = selectedTimeOption,
                onOptionSelected = { selectedTimeOption = it },
                options = timeMeasureOptions,
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Measurement System",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            CustomDropdownMenu(
                selectedOption = selectedDistanceOption,
                onOptionSelected = { selectedDistanceOption = it },
                options = distanceMeasureOptions
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Vibrations",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Switch(
                checked = vibrationsChecked,
                onCheckedChange = { vibrationsChecked = it
                DataManager.vibrationsToggle = vibrationsChecked
                    //Updates to the DataManager
                Log.d("DataUpdate", "Vibration toggle: ${DataManager.vibrationsToggle}")}
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Chime",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Switch(
                checked = chimeChecked,
                onCheckedChange = { chimeChecked = it
                DataManager.chimeToggle = chimeChecked
                    Log.d("DataUpdate", "Chime toggle: ${DataManager.chimeToggle}")}

            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "User Data:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { DataManager.exportSettings() }
                ) {
                    Text(text = "Upload")
                }
                Button(
                    onClick = {
                    // DataManager.exportSettings()
                        DataManager.addUserWorkout(
                            Workout(
                            id = "push_day",
                            workoutName = "Push Day",
                            exercises = listOf(
                                Exercise(exerciseName = "Bench Press", sets = 3, reps = 10, weight = 135.0),
                                Exercise(exerciseName = "Overhead Press", sets = 3, reps = 8, weight = 95.0),
                                Exercise(exerciseName = "Dumbbell Flyes", sets = 3, reps = 12, weight = 50.0),
                                Exercise(exerciseName = "Dips", sets = 3, reps = 10, weight = 35.0),
                                Exercise(exerciseName = "Do work", sets = 7, reps = 12, weight = 19999.0)
                                )
                            )
                        )
                        DataManager.addUserWorkout(Workout(
                            id = "pull_day",
                            workoutName = "Pull Day",
                            exercises = listOf(
                                Exercise(exerciseName = "Deadlift", sets = 1, reps = 5, weight = 225.0),
                                Exercise(exerciseName = "Pull-ups", sets = 4, reps = 10, weight = 15.0),
                                Exercise(exerciseName = "Barbell Row", sets = 5, reps = 5, weight = 135.0),
                                Exercise(exerciseName = "Curls", sets = 3, reps = 12, weight = 95.0),
                            )
                        ))
                    }
                ) {
                    Text(text = "Download")
                }
            }
        }
    }
}


@Composable
fun CustomDropdownMenu(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    options: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown"
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.LightGray,
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        DataManager.updateSettingsDropDown(option)
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}