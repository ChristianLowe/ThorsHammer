package com.grognak.managers

import com.grognak.activities.FishingActivity
import com.grognak.activities.WoodcuttingActivity
import com.grognak.databank.Location
import com.grognak.databank.SkillType
import net.dv8tion.jda.core.entities.User

object SkillingManager {
    private val skillingLocationMap = mapOf(
            SkillType.Fishing to arrayOf(Location.Lake),
            SkillType.Woodcutting to arrayOf(Location.Forest)
    )

    fun startSkilling(user: User, skill: SkillType): String {
        val userData = UserDataManager.getUserData(user)
        if (userData.location !in skillingLocationMap[skill]!!) {
            return "you can't perform that action here. Your current location is `${userData.location}`. Try moving?"
        }

        val response: String
        when (skill) {
            SkillType.Fishing -> {
                response = "you cast out your net..."
                ActivityManager.addActivity(FishingActivity(user))
            }
            SkillType.Woodcutting -> {
                response = "you swing your hatchet..."
                ActivityManager.addActivity(WoodcuttingActivity(user))
            }
        }
        return response
    }
}