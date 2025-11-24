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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.apsu.repquest.dataclasses.Exercise
import edu.apsu.repquest.dataclasses.Workout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    workoutId: String,
    onNavigateBack: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout Name") },
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
        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                exerciseTracker()
            }
        }
    }
}

@Composable
fun exerciseTracker(
    workoutId: String = "",
    modifier: Modifier = Modifier,
    activeColor: Color = Color.Blue,
    inactiveColor: Color = Color.White,
    textColor: Color = Color.Black,
    activeTextColor: Color = Color.White,
) {
    // Placeholder values for all exercise data. Replace to calls to DB using workoutId
    var initialCount = 5
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
                    text = "Exercise Name",
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                )

                TextField(
                    modifier = Modifier
                        .width(100.dp),
                    value = weight,
                    onValueChange = {weight = it},
                )
            }

            // Button row
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                // for the number of sets of exercise
                for (i in 1..5) {
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