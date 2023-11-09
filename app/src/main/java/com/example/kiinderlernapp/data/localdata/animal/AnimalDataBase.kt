package com.example.kiinderlernapp.data.localdata.animal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kiinderlernapp.data.datamodels.Animal

// Definiere die Datenbankklasse und gibt an, dass sie das RoomDatabase erweitert
@Database(entities = [Animal::class], version = 1)
abstract class AnimalDataBase : RoomDatabase() {
    abstract val animalDatabseDao: AnimalDao // Stellt Zugriff auf den Data Access Object (DAO) für Tiere bereit

    companion object {
        lateinit var INSTANCE: AnimalDataBase // Singleton-Instanz der Datenbank

        // Funktion zum Abrufen oder Erstellen der Datenbank
        fun getDatabase(context: Context): AnimalDataBase {
            synchronized(AnimalDataBase::class.java) {
                if (!Companion::INSTANCE.isInitialized) {
                    // erstelle die Datenbank
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AnimalDataBase::class.java,
                        "animal_database" // Name der Datenbank
                    )
                        .build()
                }
            }
            return INSTANCE // Gib die Datenbankinstanz zurück
        }
    }
}
