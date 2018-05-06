package com.grognak.activities

import com.grognak.managers.UserDataManager
import com.grognak.databank.Item.*
import com.grognak.databank.SkillType
import com.grognak.messageUser
import com.grognak.random
import net.dv8tion.jda.core.entities.User

val minLevels = mapOf(
        Shrimp      to 1,
        Sardines    to 5,
        Herring     to 10,
        Anchovies   to 15,
        Trout       to 20,
        Pike        to 25,
        Salmon      to 30,
        Tuna        to 35,
        Lobsters    to 40,
        Bass        to 46,
        Swordfish   to 50,
        Monkfish    to 62,
        Sharks      to 76
)

val fishXp = mapOf(
        Shrimp      to 10,
        Sardines    to 20,
        Herring     to 30,
        Anchovies   to 40,
        Trout       to 50,
        Pike        to 60,
        Salmon      to 70,
        Tuna        to 80,
        Lobsters    to 90,
        Bass        to 100,
        Swordfish   to 100,
        Monkfish    to 120,
        Sharks      to 110
)

class FishingActivity(user: User) : Activity(user) {

    override fun perform(): Long? {
        val userData = UserDataManager.getUserData(user)

        if (!userData.inventory.isFull()) {
            val catch = productionRoulette(minLevels, userData.xp.level(SkillType.Fishing))
            messageUser(user, "you caught ${catch.name}, worth ${fishXp[catch]} xp.")

            addItem(user, catch)
            addXP(user, SkillType.Fishing, fishXp[catch]!!)
        }

        if (userData.inventory.isFull()) {
            messageUser(user, "your inventory is full!")
            return null
        } else {
            return waitTime()
        }
    }

    override fun waitTime() = (5_000..30_000).random().toLong()
}