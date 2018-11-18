package com.example.tomse.tomshelter;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "pet_table")
public class Pet {

    @PrimaryKey (autoGenerate = true)
    private int id;

    private String name;

    private String species;

    private int age;

    private String photo;


    public Pet(String name, String species, int age, String photo) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.photo = photo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public String getPhoto() {
        return photo;
    }
}
