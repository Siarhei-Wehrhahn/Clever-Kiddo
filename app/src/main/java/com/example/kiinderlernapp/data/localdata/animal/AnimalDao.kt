package com.example.kiinderlernapp.data.localdata.animal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.data.datamodels.Tamagotchi

// Definiere das Data Access Object (DAO) für die Tierdatenbank
@Dao
interface AnimalDao {

    // Füge ein Tier zur Datenbank hinzu oder aktualisiere es, wenn es bereits existiert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(animal: Animal)

    // Frage alle Tiere aus der Datenbank ab
    @Query("SELECT * FROM animal_database")
    suspend fun getAll(): List<Animal>

    // Aktualisiere die Informationen zu einem Tier in der Datenbank
    @Update
    suspend fun update(animal: Animal)

    // Lösche alle Tiere aus der Datenbank
    @Query("DELETE FROM animal_database")
    suspend fun deleteAll()

    // Lösche ein bestimmtes Tier anhand seiner eindeutigen ID
    @Query("DELETE FROM animal_database WHERE id = :id")
    fun delete(id: Int)
}
