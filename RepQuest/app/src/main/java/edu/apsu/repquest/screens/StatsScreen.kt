package edu.apsu.repquest.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.apsu.repquest.dataclasses.Exercise

@Composable
fun StatsScreen() {
    val headers = listOf("Exercise Name", "Unit Comp", "Amount", "Rest")

    val data = listOf("Push Ups but with a really long title", "Reps", "30", "60s")

    /*
    Code to try and extract all exercises from all workouts and mitigate duplicates

    val workouts = DataManager.userWorkouts
    val dataList = ArrayList<List<String>>()
    for (workout in workouts){
        for(exercise in workout.exercises){
            for(i in 0 ..<workout.exercises.size){
                if(exercise.exerciseName.equals(workout.exercises[i].exerciseName)){
                    return
                }else{
                    dataList.add(listOf("${workout.exercises[i].exerciseName}"))
                }
            }
        }
    } */

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(32.dp))
        Text("Statistics", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(32.dp))

        // Header row
        LazyVerticalGrid(
            columns = GridCells.Fixed(headers.size),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(headers) { header ->
                Text(
                    text = header,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        // Data row
        LazyVerticalGrid(
            columns = GridCells.Fixed(data.size),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            //verticalArrangement = Arrangement.Center
        ) {
            items(data) { value ->
                Text(text = value, fontSize = 14.sp)
            }

            /*

            Code to try and put the combined list of exercises on screen. Moves whole screen
            and you can't see anything anymore

            for (dataEntry in dataList){
                items(dataEntry) { value ->
                    Text(text = value, fontSize = 14.sp)
                }
            }
            */

        }
    }
}