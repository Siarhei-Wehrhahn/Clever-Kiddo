package com.example.kiinderlernapp.data.datamodels.cat

data class Response(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)