package io

import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.ArrayList

interface FileEditor {
    fun openFile(filename: String): Boolean
    fun saveDataToFile(fileName: String, lines: ArrayList<String>)
    fun readDataFromFile(): ArrayList<String>

    class Base : FileEditor {
        private var reader: Scanner? = null

        override fun openFile(filename: String): Boolean {
            return try {
                val file = File(filename)
                reader = Scanner(file)
                true
            } catch (e: FileNotFoundException) {
                println("File not found: $filename")
                false
            }
        }

        override fun saveDataToFile(fileName: String, lines: ArrayList<String>) {
            val file = File("$fileName.txt")
            file.writeText(lines.joinToString("\n"))

            val isNewFileCreated :Boolean = file.createNewFile()

            if(isNewFileCreated){
                println("$fileName is created successfully.")
            } else{
                println("$fileName already exists.")
            }

        }

        override fun readDataFromFile(): ArrayList<String> {
            val lines = ArrayList<String>()
            var line:String
            while (reader!!.hasNext()) {
                line = reader!!.nextLine()
                lines.add(line)
            }
            reader?.close()
            return lines
        }

    }
}

