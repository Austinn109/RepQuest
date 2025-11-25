package edu.apsu.repquest.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateExercise (
    onNavigateBack: () -> Unit,
) {
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
                .padding(paddingValues)
        ) {
            Text(
                text = "Exercise Name:",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )

            var name by remember { mutableStateOf("") }
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
            )

            Text(
                text = "Unit of Completion",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )

            // Radio button row
            val unitOfCompletionOptions = listOf("Reps", "Distance", "Time")
            var selectedUnitOfCompletion by remember { mutableStateOf("") }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                unitOfCompletionOptions.forEach { completionText ->
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RadioButton(
                            selected = (selectedUnitOfCompletion == completionText),
                            onClick = {
                                selectedUnitOfCompletion = completionText
                            }
                        )
                        Text(
                            text = completionText
                        )
                    }
                }

            }

            // Reps
            if (selectedUnitOfCompletion == unitOfCompletionOptions[0]) {
                Column(

                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(
                            text = "Reps:",
                            fontWeight = FontWeight.Bold,
                        )
                        
                    }
                }
            }
        }
    }
}