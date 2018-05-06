package com.grognak.databank

enum class Location {
    Lumbridge,
    Lake;

    companion object {
        fun valueOfCaseInsensitive(place: String?): Location? {
            Location.values().forEach {
                if (it.name.equals(place, true)) return it
            }

            return null
        }
    }
}