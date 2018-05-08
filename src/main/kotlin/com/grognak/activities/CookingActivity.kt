package com.grognak.activities

import com.grognak.managers.UserDataManager
import com.grognak.databank.Item.*
import net.dv8tion.jda.core.entities.User

class CookingActivity(user: User) : Activity(user) {

    private val minLevels = mapOf(
            Shrimp      to 1,
            Sardines    to 1,
            Herring     to 5,
            Anchovies   to 1,
            Trout       to 15,
            Pike        to 20,
            Salmon      to 25,
            Tuna        to 30,
            Lobsters    to 40,
            Bass        to 43,
            Swordfish   to 45,
            Monkfish    to 62,
            Sharks      to 80
    )

    private val foodXp = mapOf(
            Shrimp      to 30,
            Sardines    to 40,
            Herring     to 50,
            Anchovies   to 30,
            Trout       to 70,
            Pike        to 80,
            Salmon      to 90,
            Tuna        to 100,
            Lobsters    to 120,
            Bass        to 130,
            Swordfish   to 140,
            Monkfish    to 150,
            Sharks      to 210
    )

    override fun perform(): Long? {
        val userData = UserDataManager.getUserData(user)

        // TODO
    }

    override fun waitTime(): Long = 2_000
}