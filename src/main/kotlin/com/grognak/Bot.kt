package com.grognak

import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.JDA
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.entities.*
import javax.security.auth.login.LoginException

const val ERROR_LOGIN = "Could not login with your given token. " +
        "Please update your token in config.txt, verify internet connectivity, and restart."

private lateinit var jda: JDA

fun main(args: Array<String>) {
    val config = Config.fromFileName("config.txt")

    if (config["discord_client_id"] != null) {
        val url = "https://discordapp.com/oauth2/authorize?client_id=${config["discord_client_id"]}&scope=bot"
        println("To add this bot to your server, visit here: $url")
    }

    try {
        startBot(config)
    } catch (e: LoginException) {
        println(ERROR_LOGIN)
        return
    }
}

fun startBot(config: Config) {
    jda = JDABuilder(AccountType.BOT)
            .setToken(config["discord_token"]!!)
            .addEventListener(MessageListener())
            .setGame(Game.watching("you ʘ‿ʘ"))
            .buildBlocking()
}

/**
 * MessageUser sends a queued message to a user in the last channel that they used a command.
 * If we're sending a duplicate message to the user ("you caught a trout...", "you caught a trout..."),
 * we instead edit the last message sent to them to indicate the amount of times they have received this message.
 * (i.e) "you caught a trout... (x3)"
 */
val lastMessage = HashMap<User, Pair<Message, Int>>()
fun messageUser(user: User, msg: String?) {
    val userData = UserDataManager.getUserData(user)
    val channelLong = userData.channel

    if (channelLong != null && msg != null) {
        val channel = jda.getTextChannelById(channelLong)

        if (lastMessage[user] != null) {
            val (oldMessage, oldCount) = lastMessage[user]!!
            if (oldMessage.contentDisplay.contains(msg)) {
                val newCount = oldCount + 1
                val newMessage = oldMessage.editMessage("${userData.name}, $msg (x$newCount)").complete()
                lastMessage[user] = Pair(newMessage, newCount)
                return
            }
        }

        val message = channel.sendMessage("${userData.name}, $msg").complete()
        lastMessage[user] = Pair(message, 1)
    }
}
