package com.example.kiinderlernapp.data.localdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kiinderlernapp.data.datamodels.Animal

// Definiere die Datenbankklasse und gibt an, dass sie das RoomDatabase erweitert
@Database(entities = [Animal::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract val dogDatabseDao: DogDao // Stellt Zugriff auf den Data Access Object (DAO) für Tiere bereit

    companion object {
        lateinit var INSTANCE: DataBase // Singleton-Instanz der Datenbank

        // Funktion zum Abrufen oder Erstellen der Datenbank
        fun getDatabase(context: Context): DataBase {
            synchronized(DataBase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    // erstelle die Datenbank
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        "animal_database" // Name der Datenbank
                    )
                        .build()
                }
            }
            return INSTANCE // Gib die Datenbankinstanz zurück
        }
    }
}
