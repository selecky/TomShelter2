package com.example.tomse.tomshelter;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class PetViewModel extends AndroidViewModel {
    private PetRepository repository;
    private LiveData<List<Pet>> allPets;


    public PetViewModel(@NonNull Application application) {
        super(application);

        repository = new PetRepository(application);
        allPets = repository.getAllPets();
    }

    public void insert(Pet pet) {
        repository.insert(pet);
    }

    public void update(Pet pet) {
        repository.update(pet);
    }

    public void delete(Pet pet) {
        repository.delete(pet);
    }

    public void delteAll() {
        repository.deleteAll();
    }

    public LiveData<List<Pet>> getAllPets() {
        return allPets;
    }
}
