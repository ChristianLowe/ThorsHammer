package com.grognak

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.grognak.databank.Item
import com.grognak.databank.SkillType
import java.io.Serializable
import java.io.StringWriter
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SkillStats : HashMap<SkillType, Long>() {
    // Source: https://www.reddit.com/r/2007scape/comments/3idn2b/exp_to_lvl_formula/
    private val EXPERIENCES = intArrayOf(0, 0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833, 16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983, 75127, 83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742, 302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895, 1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594, 3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629, 11805606, 13034431)

    override fun get(key: SkillType): Long {
        return super.get(key) ?: 0
    }

    /**
     * Returns true on level-up
     */
    fun add(skill: SkillType, xp: Long): Boolean {
        val oldLevel = this.level(skill)
        this[skill] = this[skill] + xp
        val newLevel = this.level(skill)

        return newLevel > oldLevel
    }

    fun level(skill: SkillType): Int {
        val xp = this[skill]

        var level = 1
        while (level < 99) {
            if (EXPERIENCES[level + 1] > xp) break
            level++
        }

        return level
    }
}

class Inventory : ArrayList<Item>() {
    private val maxInventorySize = 10

    fun isFull(): Boolean = this.size >= maxInventorySize
}

class Bank : HashMap<Item, Long>() {
    override fun get(key: Item): Long {
        return super.get(key) ?: 0
    }

    fun add(item: Item) {
        add(item, 1)
    }

    fun add(item: Item, count: Long) {
        this[item] = this[item] + count
    }
}

class UserData(@JsonProperty("name") val name: String) : Serializable {
    @JsonProperty("channel")
    var channel: Long? = null

    @JsonProperty("xp")
    val xp: SkillStats = SkillStats()

    @JsonProperty("inventory")
    val inventory: Inventory = Inventory()

    @JsonProperty("bank")
    val bank: Bank = Bank()
}

