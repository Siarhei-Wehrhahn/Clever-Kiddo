package com.example.kiinderlernapp.data.datamodels

import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey

@Entity("tamagotchi_stats")
data class Tamagotchi(
    @PrimaryKey
    var id: Int = 0,
    var eat: Int = 100,
    var sleep: Int = 100,
    var joy: Int = 100,
    var toilet: Int = 100,
    var tennisBall: Int = 1,
    var footBall: Int = 1,
    var apple: Int = 1,
    var broccoli: Int = 1,
    var peas: Int = 1,
    var strawberry: Int = 1,
    var pomegrenade: Int = 1,
    var cucumber: Int = 1,
    var kiwi: Int = 1,
    var salat: Int = 1
)