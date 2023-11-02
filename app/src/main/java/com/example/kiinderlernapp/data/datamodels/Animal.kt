package com.example.kiinderlernapp.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("animal_database")
data class Animal(
    @PrimaryKey(autoGenerate = true) // Primärschlüssel, automatische Generierung
    val id: Int, // Eindeutige Identifikation des Tiers
    val imageRecource: String, // Ressourcen-URL oder Pfad zum Tierbild
    var isDog: Boolean // Ein Boolean, der angibt, ob das Tier ein Hund ist (true) oder nicht (false)
)
