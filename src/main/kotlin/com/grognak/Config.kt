package com.grognak

import java.io.File
import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap

class Config : HashMap<String, String>() {
    companion object {
        fun fromFileName(fileName: String): Config {
            try {
                return fromInputStream(File(fileName).inputStream())
            } catch (e: NoSuchFileException) {
                System.err.println("Please make sure you rename 'example.config.txt' to 'config.txt' and change the values within.")
                throw e
            }
        }

        fun fromInputStream(fileStream: InputStream): Config {
            val config = Config()
            val input = Scanner(fileStream)

            while (input.hasNextLine()) {

                val line = input.nextLine().trim().split("\\s+".toRegex())
                val variable = line[0]

                if (variable == "" || variable.startsWith('#')) continue

                config.set(variable, line.slice(1 until line.size).joinToString(" "))
            }

            return config
        }
    }
}