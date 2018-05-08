package com.grognak.activities

import com.grognak.managers.UserDataManager
import com.grognak.databank.Location
import com.grognak.databank.Location.*
import com.grognak.messageUser
import com.grognak.random
import net.dv8tion.jda.core.entities.User

class MovingActivity(user: User, private val location: Location) : Activity(user) {
    private val flavorText = mapOf(
            Lumbridge to "Lumbridge, a bustling city with a giant castle and a bank.",
            Lake to "the glistening Lake Lumbridge, a clear blue pool of water that is teeming with fish."
    )

    override fun perform(): Long? {
        UserDataManager.withUserData(user) {
            it.location = location
        }

        messageUser(user, "you arrive at ${flavorText[location]}")

        return null
    }

    override fun waitTime(): Long = 30_000 // TODO: implement distances between location nodes
}