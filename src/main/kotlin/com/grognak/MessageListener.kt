package com.grognak

import com.grognak.activities.BankingActivity
import com.grognak.databank.SkillType
import com.grognak.activities.FishingActivity
import net.dv8tion.jda.core.entities.User
import net.dv8tion.jda.core.events.message.MessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter

class MessageListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        with (event) {
            if (channel.name.toLowerCase() != "thorshammer" || author.isBot) return
            println("${author.fullName()}: ${message.contentDisplay}")

            val messageParams = message.contentDisplay.trim().split("\\s+".toRegex())

            val response = when (messageParams.get(0).toLowerCase()) {
                "g", "go"   -> go(author, messageParams.getOrNull(1))
                "s", "stop" -> stop(author)
                "b", "bank" -> bank(author)
                "i", "inv", "inventory" -> inventory(author)
                "?", "help", "commands" -> help()
                "ping"      -> ping()

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

            else -> return "go where??"
        }

        val response: String
        when (skill) {
            SkillType.Fishing -> {
                response = "you cast out your net..."
                SkillingManager.addActivity(FishingActivity(user))
            }
        }

        return response
    }

    private fun stop(user: User): String {
        SkillingManager.cancelUserActivity(user)
        return "you stopped what you're doing."
    }

    private fun bank(user: User): String {
        val waitTime = SkillingManager.addActivity(BankingActivity(user))
        return "you start making your way to the bank. ETA: ${formatMilliseconds(waitTime)}."
    }

    private fun inventory(user: User): String {
        val userData = UserDataManager.getUserData(user)
        return "on hand, you have: ${userData.inventory}.\nIn your bank, you have ${userData.bank}."
    }

    private fun help(): String {
        return "valid commands are: `go, stop, bank, inventory`. Valid activities are: `fishing`. Try saying `go fishing`!"
    }

    private fun ping(): String {
        return "pong! :)"
    }
}