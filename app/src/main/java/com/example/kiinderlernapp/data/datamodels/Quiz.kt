package com.example.kiinderlernapp.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("animal_database")
data class Quiz(

    @PrimaryKey
    var question: String,
    var answerA: Int,
    var answerB: Int,
    var answerC: Int,
    var answerD: Int,
    var rightAnswere: Int
)
