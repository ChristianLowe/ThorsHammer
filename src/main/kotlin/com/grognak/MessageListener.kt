package com.grognak

import com.grognak.activities.MovingActivity
import com.grognak.databank.SkillType
import com.grognak.databank.Location
import com.grognak.managers.ActivityManager
import com.grognak.managers.BankingManager
import com.grognak.managers.SkillingManager
import com.grognak.managers.UserDataManager
import net.dv8tion.jda.core.entities.User
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

class MessageListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        with (event) {
            if (channel.name.toLowerCase() != "bots"
                    || author.isBot
                    || author.fullName() in config["ignore_list"]!!.split(' ')) {
                return
            }

            println("${author.fullName()}: ${message.contentDisplay}")

            val messageParams = message.contentDisplay.trim().split("\\s+".toRegex())

            val response = when (messageParams.get(0).toLowerCase()) {
                "g", "go"       -> go(author, messageParams.getOrNull(1))
                "m", "move"     -> move(author, messageParams.getOrNull(1))
                "s", "stop"     -> stop(author)
                "b", "bank"     -> bank(author, messageParams)
                "l", "levels"   -> levels(author)
                "i", "inv", "inventory" -> inventory(author)
                "?", "help", "commands" -> help()
                "ping"          -> ping()

                else -> null
            }

            UserDataManager.withUserData(author) {
                it.channel = channel.idLong
            }

            messageUser(author, response)
        }
    }

    private fun go(user: User, action: String?): String {
        val skill = when (action) {
            "f", "fish", "fishing" -> SkillType.Fishing

            else -> return "go do what? Possible skills: `${SkillType.values().joinToString()}`"
        }

        return SkillingManager.startSkilling(user, skill)
    }

    private fun move(user: User, place: String?): String {
        val location = Location.fromString(place)

        if (location == null) {
            val userData = UserDataManager.getUserData(user)
            return "move where? You are currently at `${userData.location}`. Valid locations are `${Location.values().joinToString()}`"
        } else {
            val waitTime = ActivityManager.addActivity(MovingActivity(user, location))
            return "you make your way to ${location.name}. (ETA: ${formatMilliseconds(waitTime)})"
        }
    }

    private fun stop(user: User): String {
        ActivityManager.cancelUserActivity(user)
        return "you stopped what you're doing."
    }

    private fun bank(user: User, messageParams: List<String>): String {
        return BankingManager.processRequest(user, messageParams.slice(1 until messageParams.size))
    }

    private fun levels(user: User): String {
        val userData = UserDataManager.getUserData(user)

        return "your levels are at {${userData.xp.map { (s, _) -> "$s=${userData.xp.level(s)}" }.joinToString()}}"
    }

    private fun inventory(user: User): String {
        val userData = UserDataManager.getUserData(user)
        return "on hand, you have: ${userData.inventory}."
    }

    private fun help(): String {
        return "valid commands are: `go, move, stop, bank, levels, inventory`. Valid skills are: `${SkillType.values().joinToString()}`. Try saying `go fishing`!"
    }

    private fun ping(): String {
        return "pong! :)"
    }
}