package com.grognak.activities

import com.grognak.managers.UserDataManager
import com.grognak.databank.Item.*
import com.grognak.databank.SkillType
import com.grognak.messageUser
import com.grognak.random
import net.dv8tion.jda.core.entities.User

val minWoodLevels = mapOf(
        Normal         to 1,
        Oak            to 15,
        Wilow          to 30,
        Teak           to 35,
        Maple          to 45,
        Mahogany       to 50,
        Yew            to 60,
        Magic          to 75,
        Redwood        to 90
)

val experience = mapOf(
        Normal         to 25,
        Oak            to 38,
        Wilow          to 68,
        Teak           to 85,
        Maple          to 100,
        Mahogany       to 125,
        Yew            to 175,
        Magic          to 250,
        Redwood        to 380
)


class WoodcuttingActivity(user: User) : Activity(user) {
    
    override fun perform(): Long? {

        if (!UserDataManager.getUserData(user).inventory.isFull()) {
            val wood = productionRoulette(minWoodLevels, UserDataManager.getUserData(user).xp.level(SkillType.Woodcutting))
            messageUser(user, "you cut and collected ${wood.name} wood, worth ${experience[wood]} xp.")

            addItem(user, wood)
            addXP(user, SkillType.Woodcutting, experience[wood]!!)
            return waitTime()
        } else {
            messageUser(user, "your inventory is full!")
            return null
        }
    }

    override fun waitTime() = (10_000..40_000).random().toLong()
}