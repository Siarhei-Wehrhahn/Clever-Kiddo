package com.example.kiinderlernapp.data.localdata.tamagotchi

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.withTransaction
import com.example.kiinderlernapp.data.datamodels.Tamagotchi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
                        .addCallback(databaseCallback)
                        .build()
                }
            }
            return INSTANCE
        }
        private val databaseCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch(Dispatchers.IO) {
                    // Füge hier die Standardwerte in die Datenbank ein
                    INSTANCE.tamagotchiDatabaseDao.insertStats(Tamagotchi(0,100,100,100,100,1,1,1,1,1,1,1,1,1,1,1))
                }
            }
        }
    }
}