package com.example.tomse.tomshelter;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PetRepository {

    private PetDao petDao;
    private LiveData<List<Pet>> allPets;

    public PetRepository (Application application){
        PetDatabase petDatabase = PetDatabase.getInstance(application);
        petDao = petDatabase.getPetDao();
        allPets = petDao.getAllPets();
    }

    public void insert (Pet pet){
        new InsertAsyncTask(petDao).execute(pet);

    }

    public void update (Pet pet){
        new UpdateAsyncTask(petDao).execute(pet);

    }

    public void delete (Pet pet){
        new DeleteAsyncTask(petDao).execute(pet);

    }

    public void deleteAll (){
        new DeleteAllAsyncTask(petDao).execute();

    }

    public LiveData<List<Pet>> getAllPets() {
        return allPets;
    }


    public static class InsertAsyncTask extends AsyncTask <Pet, Void, Void> {
        private PetDao asyncDao;

        public InsertAsyncTask(PetDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected Void doInBackground(Pet... pets) {
            asyncDao.insert(pets[0]);
            return null;
        }
    }


    public static class UpdateAsyncTask extends AsyncTask <Pet, Void, Void> {
        private PetDao asyncDao;

        public UpdateAsyncTask(PetDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected Void doInBackground(Pet... pets) {
            asyncDao.update(pets[0]);
            return null;
        }
    }


    public static class DeleteAsyncTask extends AsyncTask <Pet, Void, Void> {
        private PetDao asyncDao;

        public DeleteAsyncTask(PetDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected Void doInBackground(Pet... pets) {
            asyncDao.delete(pets[0]);
            return null;
        }
    }

    public static class DeleteAllAsyncTask extends AsyncTask <Void, Void, Void> {
        private PetDao asyncDao;

        public DeleteAllAsyncTask(PetDao asyncDao) {
            this.asyncDao = asyncDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncDao.deleteAllPets();
            return null;
        }
    }
}
