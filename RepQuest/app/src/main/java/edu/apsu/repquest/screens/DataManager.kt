package edu.apsu.repquest.screens

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import edu.apsu.repquest.dataclasses.Workout
import java.io.File

object DataManager{
    var timeOption: String = "seconds"
    var measurementOption: String = "Imperial"
    var chimeToggle: Boolean = true
    var vibrationsToggle: Boolean = true

    var userWorkouts: MutableList<Workout> = mutableListOf()

    fun updateSettingsDropDown(option: String){

        if(option.equals("minutes") || option.equals("seconds")){
            timeOption = option
            Log.d("DataUpdate", "Time updated to " + timeOption)
        }else if (option.equals("Imperial") || option.equals("Metric")){
            measurementOption = option
            Log.d("DataUpdate", "Measurements updated to " + measurementOption)
        }
    }

    fun addUserWorkout(workout: Workout){
        userWorkouts.add(workout)
    }

    fun export(context: Context){
        exportSettings()

    }


    private fun exportWorkouts(context: Any) {



    }

    fun exportSettings(){
        Log.d("Export","exportSettings() functionality not implemented in DataManager")
    }

    fun load(){

    }
    fun importSettings(){
        Log.d("Import","importSettings() functionality not implemented in DataManager")
    }
}