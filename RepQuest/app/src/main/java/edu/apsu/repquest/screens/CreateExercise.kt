package edu.apsu.repquest.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.apsu.repquest.dataclasses.Exercise

enum class UnitOfCompletion {
    REPS, DISTANCE, TIME
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExercise(
    onNavigateBack: () -> Unit,
    onExerciseCreated: (Exercise) -> Unit
) {
    var exerciseName by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf<UnitOfCompletion?>(null) }
    var reps by remember { mutableStateOf("") }
    var sets by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var distance by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var increment by remember { mutableStateOf("") }
    var restTime by remember { mutableStateOf("60") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Create Exercise") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Exercise Name
            Text(
                text = "Exercise Name",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = exerciseName,
                onValueChange = { exerciseName = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("e.g., Bench Press") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Unit of Completion
            Text(
                text = "Unit of Completion",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                UnitOfCompletion.entries.forEach { unit ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RadioButton(
                            selected = selectedUnit == unit,
                            onClick = { selectedUnit = unit }
                        )
                        Text(
                            text = unit.name.lowercase().replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Dynamic fields based on selected unit
            when (selectedUnit) {
                UnitOfCompletion.REPS -> {
                    InputField(
                        label = "Sets",
                        value = sets,
                        onValueChange = { sets = it },
                        placeholder = "e.g., 3"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InputField(
                        label = "Reps",
                        value = reps,
                        onValueChange = { reps = it },
                        placeholder = "e.g., 10"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InputField(
                        label = "Weight (lbs)",
                        value = weight,
                        onValueChange = { weight = it },
                        placeholder = "e.g., 135"
                    )
                }
                UnitOfCompletion.DISTANCE -> {
                    InputField(
                        label = "Distance (miles)",
                        value = distance,
                        onValueChange = { distance = it },
                        placeholder = "e.g., 3.1"
                    )
                }
                UnitOfCompletion.TIME -> {
                    InputField(
                        label = "Sets",
                        value = sets,
                        onValueChange = { sets = it },
                        placeholder = "e.g., 3"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    InputField(
                        label = "Time (seconds)",
                        value = time,
                        onValueChange = { time = it },
                        placeholder = "e.g., 60"
                    )
                }
                null -> {
                    Text(
                        text = "Please select a unit of completion",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
            }

            if (selectedUnit != null) {
                Spacer(modifier = Modifier.height(16.dp))

                InputField(
                    label = "Increment",
                    value = increment,
                    onValueChange = { increment = it },
                    placeholder = "e.g., 5"
                )

                Spacer(modifier = Modifier.height(16.dp))

                InputField(
                    label = "Rest Time (seconds)",
                    value = restTime,
                    onValueChange = { restTime = it },
                    placeholder = "e.g., 60"
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Done Button
            Button(
                onClick = {
                    // Validate and create exercise
                    if (exerciseName.isNotBlank() && selectedUnit != null) {
                        val exercise = Exercise(
                            exerciseName = exerciseName,
                            sets = sets.toIntOrNull() ?: 0,
                            reps = reps.toIntOrNull() ?: 0,
                            weight = weight.toDoubleOrNull() ?: 0.0,
                            increment = increment.toDoubleOrNull() ?: 0.0,
                            restTime = restTime.toIntOrNull() ?: 60
                        )
                        onExerciseCreated(exercise)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = exerciseName.isNotBlank() && selectedUnit != null
            ) {
                Text(
                    text = "Add Exercise",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )
    }
}