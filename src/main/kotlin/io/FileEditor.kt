package io

import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.ArrayList

interface FileEditor {
    fun openFile(filename: String): Boolean
    fun saveDataToFile(fileName: String, lines: Array<String>)
    fun readDataFromFile(): ArrayList<String>
    fun closeFile()

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

        override fun saveDataToFile(fileName: String, lines: Array<String>) {
            val file = File(fileName)
            file.writeText(lines.joinToString("\n"))
        }

        override fun readDataFromFile(): ArrayList<String> {
            val lines = ArrayList<String>()
            var line:String
            while (true) {
                line = reader!!.nextLine()
                if (line == "") break
                lines.add(line)
            }
            reader?.close()
            return lines
        }

        override fun closeFile() {
            reader?.close()
        }
    }
}

