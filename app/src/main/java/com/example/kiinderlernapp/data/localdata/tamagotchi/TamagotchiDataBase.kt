package com.example.kiinderlernapp.data.localdata.tamagotchi

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.withTransaction
import com.example.kiinderlernapp.data.datamodels.Tamagotchi
import kotlinx.coroutines.runBlocking

@Database(entities = [Tamagotchi::class], version = 1)
abstract class TamagotchiDataBase : RoomDatabase() {
    abstract val tamagotchiDatabaseDao: TamagotchiDao

    companion object {
        private lateinit var INSTANCE: TamagotchiDataBase

        fun getDatabase(context: Context): TamagotchiDataBase {
            synchronized(TamagotchiDataBase::class.java) {
                if (!Companion::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TamagotchiDataBase::class.java,
                        "tamagotchi_stats"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
