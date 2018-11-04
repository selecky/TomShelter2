package com.example.tomse.tomshelter;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PetDao {

    @Insert
    void insert (Pet pet);

    @Update
    void update (Pet pet);

    @Delete
    void delete (Pet pet);

    @Query("DELETE FROM pet_table")
    void deleteAllPets();

    @Query("SELECT * FROM pet_table ORDER BY age DESC")
    LiveData<List<Pet>> getAllPets();
}
