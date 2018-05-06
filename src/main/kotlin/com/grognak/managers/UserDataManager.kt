package com.grognak.managers

import com.fasterxml.jackson.databind.ObjectMapper
import com.grognak.UserData
import com.grognak.fullName
import net.dv8tion.jda.core.entities.User
import redis.clients.jedis.Jedis

object UserDataManager {
    private val redis = Jedis("redis://redis:6379")
    private val mapper = ObjectMapper()

    fun withUserData(user: User, function: (UserData) -> Unit) {
        synchronized(user) {
            val userData = getUserData(user)
            function(userData)
            save(user, userData)
        }
    }

    fun getUserData(user: User): UserData {
        val userName = user.fullName()

        if (!redis.exists(userName)) {
            println("*** New user: $userName")
            save(user, UserData(user.name))
        }

        return mapper.readValue(redis[userName], UserData::class.java)
    }

    private fun save(user: User, userData: UserData) {
        redis[user.fullName()] = mapper.writeValueAsString(userData)
    }
}

