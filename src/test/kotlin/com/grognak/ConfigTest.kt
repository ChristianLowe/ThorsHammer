package com.grognak

import org.junit.Assert.*
import org.junit.Test

class ConfigTest {

    @Test
    fun configTest() {
        val file = """
            |   #Testing comment 1
            |#Test comment 2
            |# ## Comments are great ## #
            |
            |var1 val1
            |var2 val2   val2
            |	var3	val3
            |var4
        """.trimMargin()

        val config = Config.fromInputStream(file.byteInputStream())

        assertEquals(4, config.size)
        assertEquals("val1", config.get("var1"))
        assertEquals("val2 val2", config.get("var2"))
        assertEquals("val3", config.get("var3"))
        assertEquals("", config.get("var4"))
    }
}