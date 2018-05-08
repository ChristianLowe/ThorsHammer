package com.grognak.databank

enum class Location {
    Lumbridge,
    Lake;

    companion object {
        fun fromString(place: String?): Location? {
            val stringToLocation = mapOf(
                    arrayOf("lumby", "lumbridge") to Lumbridge,
                    arrayOf("lake") to Lake
            )

            stringToLocation.forEach {
                if (place in it.key) return it.value
            }

            return null
        }
    }
}