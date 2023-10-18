package com.example.kiinderlernapp.data.localdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kiinderlernapp.data.datamodels.Animal

@Database(entities = [Animal::class], version = 1)
abstract class DataBase: RoomDatabase(){
    abstract val dogDatabseDao: DogDao

    companion object {
        lateinit var INSTANCE: DataBase

        fun getDatabase(context: Context): DataBase {
            synchronized(DataBase::class.java){
                if (!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        "animal_database"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}

