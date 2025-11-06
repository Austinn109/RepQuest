package edu.apsu.repquest.screens

import android.os.SystemClock
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.himanshoe.kalendar.Kalendar
import com.himanshoe.kalendar.KalendarType
import kotlinx.datetime.*
import java.time.LocalDate as JavaLocalDate

@Composable
fun HistoryScreen() {
    val today = JavaLocalDate.now()
    val currentDay = LocalDate(today.year, today.month.value, today.dayOfMonth)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(64.dp))
        Text(
            text = "History",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.size(64.dp))
        Kalendar(
            kalendarType = KalendarType.Oceanic,
            currentDay = currentDay
        )

    }
}