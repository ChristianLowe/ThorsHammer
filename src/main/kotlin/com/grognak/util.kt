package com.grognak

import net.dv8tion.jda.core.entities.User
import java.util.concurrent.ThreadLocalRandom

fun User.fullName(): String = "${this.name}#${this.discriminator}"

fun ClosedRange<Int>.random() = ThreadLocalRandom.current().nextInt(endInclusive - start) + start

fun formatMilliseconds(totalTime: Long): String {
    val timeInSeconds = totalTime / 1000
    val minutes = timeInSeconds / 60
    val seconds = timeInSeconds % 60

    if (minutes > 0) return "$minutes minutes, $seconds seconds"
    else return "$seconds seconds"
}
