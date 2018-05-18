package com.grognak.databank

enum class Item {
    // Fish
    Shrimp,
    Sardines,
    Herring,
    Anchovies,
    Trout,
    Pike,
    Salmon,
    Tuna,
    Lobsters,
    Bass,
    Swordfish,
    Monkfish,
    Sharks,
    // Wood
    Normal,
    Oak,
    Willow,
    Teak,
    Maple,
    Mahogany,
    Yew,
    Magic,
    Redwood;

    companion object {
        fun valueOfCaseInsensitive(place: String?): Item? {
            Item.values().forEach {
                if (it.name.equals(place, true)) return it
            }

            return null
        }
    }
}