package edu.apsu.repquest.screens

import android.util.Log

object DataManager{
    var timeOption: String = "seconds"
    var measurementOption: String = "Imperial"
    var chimeToggle: Boolean = true
    var vibrationsToggle: Boolean = true


    fun updateSettingsDropDown(option: String){

        if(option.equals("minutes") || option.equals("seconds")){
            timeOption = option
            Log.d("DataUpdate", "Time updated to " + timeOption)
        }else if (option.equals("Imperial") || option.equals("Metric")){
            measurementOption = option
            Log.d("DataUpdate", "Measurements updated to " + measurementOption)
        }
    }

    fun exportSettings(){
        Log.d("Export","exportSettings() functionality not implemented in DataManager")
    }

    fun importSettings(){
        Log.d("Import","importSettings() functionality not implemented in DataManager")
    }
}