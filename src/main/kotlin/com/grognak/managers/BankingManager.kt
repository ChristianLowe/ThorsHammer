package com.grognak.managers

import com.grognak.UserData
import com.grognak.databank.Item
import com.grognak.databank.Location
import net.dv8tion.jda.core.entities.User

object BankingManager {
    private val bankingLocations = arrayOf(Location.Lumbridge)

    fun processRequest(user: User, params: List<String>): String {
        val userData = UserDataManager.getUserData(user)
        if (userData.location !in bankingLocations) {
            return "you don't have access to a bank in this area. Try moving to a major city."
        }

        return when(params.getOrNull(0)) {
            "d", "deposit"      -> deposit(user, params.slice(1 until params.size))
            "w", "withdrawal"   -> withdrawal(user, params.slice(1 until params.size))
            "c", "check"        -> check(userData)

            else -> "possible bank actions are: `deposit, withdrawal, check`"
        }
    }

    private fun deposit(user: User, params: List<String>): String {
        val userData = UserDataManager.getUserData(user)

        val amountStr = params.getOrNull(0)
        val itemStr = params.getOrNull(1)

        if (amountStr == null) {
            return "usage: `bank deposit <amount> <item>"
        }

        var amount: Int
        if (amountStr == "a" || amountStr == "all") {
            amount = 28 // FIXME use a constant
        } else if (amountStr.toIntOrNull() != null) {
            amount = if (amountStr.toInt() > 0) amountStr.toInt() else 0
        } else {
            return "invalid amount."
        }

        val item = Item.valueOfCaseInsensitive(itemStr)
        if (item == null && itemStr != null) {
            return "that's an invalid item."
        } else if (item != null && item !in userData.inventory) {
            return "you don't have that item in your inventory."
        }

        UserDataManager.withUserData(user) {
            while (amount > 0) {
                val transferItem: Item?
                transferItem = if (item == null) {
                    it.inventory.firstOrNull()
                } else {
                    it.inventory.find { invItem -> invItem == item }
                }

                if (transferItem == null) {
                    break
                } else {
                    it.bank.add(transferItem)
                    it.inventory.remove(transferItem)
                }

                amount--
            }
        }

        return "successfully deposited your ${itemStr ?: "items"}"
    }

    private fun withdrawal(user: User, params: List<String>): String {
        return "withdrawaling is not currently implemented."
    }

    private fun check(userData: UserData): String = "your bank contains: ${userData.bank}"
}