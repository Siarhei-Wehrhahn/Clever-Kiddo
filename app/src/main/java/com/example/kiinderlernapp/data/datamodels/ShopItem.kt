package com.example.kiinderlernapp.data.datamodels

import com.example.kiinderlernapp.R

class ShopItem(val res: Int, val price: Int, val item: String)

fun loadItem(): List<ShopItem> {
    var items = mutableListOf(
        ShopItem(R.drawable.toilet_paper, 30, "toilet_paper"),
        ShopItem(R.drawable.fussball, 20, "football"),
        ShopItem(R.drawable.tennisball, 15, "tennisball"),
        ShopItem(R.drawable.apfel, 10, "apple"),
        ShopItem(R.drawable.broccoli, 15, "broccoli"),
        ShopItem(R.drawable.erbsen, 15, "peas"),
        ShopItem(R.drawable.erdbeere, 10, "strawberry"),
        ShopItem(R.drawable.granatapfel, 20, "pomegrenade"),
        ShopItem(R.drawable.gurcke, 10, "cucumber"),
        ShopItem(R.drawable.kiwi, 10, "kiwi"),
        ShopItem(R.drawable.kopfsalat, 15, "salat")
    )
    return items
}