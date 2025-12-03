package edu.apsu.repquest.screens

import android.content.Context
import java.io.File

class FileHelper(private val context: Context) {

    fun writeFile(filename: String, content: String) {
        val file = File(context.filesDir, filename)
        file.writeText(content)
    }
}