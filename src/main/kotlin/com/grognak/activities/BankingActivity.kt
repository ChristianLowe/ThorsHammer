package com.grognak.activities

import com.grognak.UserDataManager
import com.grognak.messageUser
import com.grognak.random
import net.dv8tion.jda.core.entities.User

class BankingActivity(user: User) : Activity(user) {
    override fun perform(): Long? {
        UserDataManager.withUserData(user) {
            for (item in it.inventory) {
                it.bank.add(item)
            }

            it.inventory.clear()
        }

        messageUser(user, "you stash your items in the bank.")

        return null
    }

    override fun waitTime(): Long = (40_000..50_000).random().toLong()
}