package com.example.kiinderlernapp.data.localdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kiinderlernapp.data.datamodels.Animal
import com.example.kiinderlernapp.data.datamodels.dog.Dogs

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(animal: Animal)

    @Query("SELECT * FROM animal_database")
    suspend fun getAll(): List<Animal>

    @Update
    suspend fun update(animal: Animal)

    @Query("DELETE FROM animal_database")
    suspend fun deleteAll()

    @Query("DELETE FROM animal_database WHERE id = :id")
    fun delete(id: Int)
}