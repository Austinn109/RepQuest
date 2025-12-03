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

    fun findWorkout(id: String): Workout? {

        for (i in 0..<userWorkouts.size){
            if(userWorkouts[i].id == id){
                return userWorkouts[i]
            }
        }

        //It shouldn't get here
        return null

    }

    fun getSystemOfMeasurement() : String{
        if (measurementOption.equals("Imperial"))
            return "lbs"

        return "kgs"
    }

    fun getTimeMeasurement() : String{
        if (timeOption.equals("seconds"))
            return "secs"
        return "mins"
    }

    fun export(context: Context){
        exportSettings()
        exportWorkouts(context)

    }


    private fun exportWorkouts(context: Context) {



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