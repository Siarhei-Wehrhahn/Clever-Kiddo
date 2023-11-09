package com.example.kiinderlernapp.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tamagotchi_stats")
data class Tamagotchi(
    @PrimaryKey
    var eat: Int,
    var sleep: Int,
    var joy: Int,
    var toilet: Int,
    var tennisBall: Int,
    var footBall: Int,
    var apple: Int,
    var broccoli: Int,
    var peas: Int,
    var strawberry: Int,
    var pomegrenade: Int,
    var cucumber: Int,
    var kiwi: Int,
    var salat: Int
)