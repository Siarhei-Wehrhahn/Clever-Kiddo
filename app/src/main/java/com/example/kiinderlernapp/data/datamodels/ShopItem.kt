package com.example.kiinderlernapp.data.datamodels

import com.example.kiinderlernapp.R

class ShopItem(val res: Int, val price: Int)

fun loadItem(): List<ShopItem> {
    var items = mutableListOf(
        ShopItem(R.drawable.fussball, 20),
        ShopItem(R.drawable.tennisball, 15),
        ShopItem(R.drawable.toilet_paper, 30),
        ShopItem(R.drawable.apfel, 10),
        ShopItem(R.drawable.broccoli, 15),
        ShopItem(R.drawable.erbsen, 15),
        ShopItem(R.drawable.erdbeere, 10),
        ShopItem(R.drawable.granatapfel, 20),
        ShopItem(R.drawable.gurcke, 10),
        ShopItem(R.drawable.kiwi, 10),
        ShopItem(R.drawable.kopfsalat, 15),
    )
    return items
}