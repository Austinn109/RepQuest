package edu.apsu.repquest.dataclasses

data class UserConfig (
    var timeUnit: String = "seconds",
    var distanceUnit: String = "Imperial",
    var vibrate: Boolean = true,
    var chime: Boolean = true,
)