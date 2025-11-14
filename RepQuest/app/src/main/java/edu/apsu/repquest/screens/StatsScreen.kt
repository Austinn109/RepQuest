package edu.apsu.repquest.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StatsScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(64.dp))
        Text(
            text = "Statistics",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.size(64.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Text(text = "Exercise Name", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = "Unit Comp", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = "Amount", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = "Incremental", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = "Rest", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.size(16.dp))

        // Data Row under headers
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(64.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Push Ups", fontSize = 14.sp)
            Text(text = "Reps", fontSize = 14.sp)
            Text(text = "30", fontSize = 14.sp)
            Text(text = "+5", fontSize = 14.sp)
            Text(text = "60s", fontSize = 14.sp)
        }


    }
}