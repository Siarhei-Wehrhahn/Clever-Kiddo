package com.example.kiinderlernapp.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("animal_database")
data class Animal(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val imageRecource: String,
    var isDog: Boolean
)
