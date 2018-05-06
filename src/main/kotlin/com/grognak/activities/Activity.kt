package com.grognak.activities

import com.grognak.UserDataManager
import com.grognak.databank.Item
import com.grognak.databank.SkillType
import com.grognak.messageUser
import net.dv8tion.jda.core.entities.User
import java.util.concurrent.ThreadLocalRandom

abstract class Activity(val user: User) {
    abstract fun perform(): Long?
    abstract fun waitTime(): Long

    fun productionRoulette(levelMap: Map<*, Int>, currentLevel: Int): Item {
        val selectionMap = levelMap.filter { it.value <= currentLevel }
        val totalPool = selectionMap.values.fold(0) { acc, i -> acc + i }
        val selectedValue = ThreadLocalRandom.current().nextDouble()

        var accumulator = 0.0
        selectionMap.forEach {
            val currentValue = (it.value + accumulator) / totalPool

            if (currentValue > selectedValue) return it.key as Item

            accumulator += it.value
        }

        throw IllegalStateException("Roulette should have returned an item.")
    }

    fun addItem(user: User, item: Item) {
        UserDataManager.withUserData(user) {
            if (!it.inventory.isFull()) {
                it.inventory.add(item)
            }
        }
    }

    fun addXP(user: User, skill: SkillType, xp: Int) {
        UserDataManager.withUserData(user) {
            val didLevelUp: Boolean = it.xp.add(skill, xp.toLong())

            if (didLevelUp)
                messageUser(user, "congrats! You are now ${skill.name} level ${it.xp.level(skill)}! \uD83D\uDC4D")
        }
    }
}