package com.example.kiinderlernapp.data.localdata.tamagotchi

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.OnConflictStrategy
import androidx.room.withTransaction
import com.example.kiinderlernapp.data.datamodels.Tamagotchi
import kotlinx.coroutines.runBlocking

@Database(entities = [Tamagotchi::class], version = 1)
abstract class TamagotchiDataBase : RoomDatabase() {
    abstract val tamagotchiDatabaseDao: TamagotchiDao // Stellt Zugriff auf den Data Access Object (DAO) für Tamagotchis bereit

    companion object {
        private var INSTANCE: TamagotchiDataBase? = null

        // Funktion zum Abrufen oder Erstellen der Datenbank
        fun getDatabase(context: Context): TamagotchiDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TamagotchiDataBase::class.java,
                    "tamagotchi_stats" // Name der Datenbank
                )
                    .addCallback(roomDatabaseCallback(context))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private fun roomDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Füge Standardwerte in die Datenbank ein, wenn sie erstellt wird
                    insertDefaultValues(context)
                }
            }
        }

        private fun insertDefaultValues(context: Context) {
            val database = getDatabase(context)
            val defaultTamagotchi = Tamagotchi(eat = 100, sleep = 100, joy = 100, toilet = 100,1,1,1,1,1,1,1,1,1,1,)
            runBlocking {
                try {
                    database.withTransaction {
                        database.tamagotchiDatabaseDao.insertStats(defaultTamagotchi)
                    }
                } catch (e: Exception) {
                    Log.e("TamagotchiDatabase", "${e.message}")
                }
            }
        }
    }
}
