package com.grognak

import com.grognak.activities.Activity
import net.dv8tion.jda.core.entities.User
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule

object ActivityManager {
    private val activityMap = HashMap<User, TimerTask>()

    fun addActivity(activity: Activity): Long {
        val waitTime = activity.waitTime()

        cancelUserActivity(activity.user)
        scheduleNextActivity(activity, waitTime)
        return waitTime
    }

    fun cancelUserActivity(user: User) {
        activityMap[user]?.cancel()
    }

    private fun scheduleNextActivity(activity: Activity, waitTime: Long) {
        activityMap[activity.user] = Timer(true).schedule(waitTime) {
            val newTime = activity.perform()

            if (newTime != null) {
                scheduleNextActivity(activity, newTime)
            }
        }
    }
}

