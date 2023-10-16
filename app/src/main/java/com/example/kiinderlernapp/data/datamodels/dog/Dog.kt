package com.example.kiinderlernapp.data.datamodels.dog

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dog(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val imageRecource: String
)
