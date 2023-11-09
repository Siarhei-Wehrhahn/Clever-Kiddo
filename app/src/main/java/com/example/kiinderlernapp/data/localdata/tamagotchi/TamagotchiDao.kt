package com.example.kiinderlernapp.data.localdata.tamagotchi

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kiinderlernapp.data.datamodels.Tamagotchi

// Definiere das Data Access Object (DAO) für die Tierdatenbank
@Dao
interface TamagotchiDao {

    @Query("SELECT * FROM tamagotchi_stats")
    suspend fun getTamagotchiValues(): Tamagotchi

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(tamagotchi: Tamagotchi)

    @Update
    suspend fun updateStats(tamagotchi: Tamagotchi)
}
