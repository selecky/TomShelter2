package com.example.tomse.tomshelter;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Pet.class}, version = 5)
public abstract class PetDatabase extends RoomDatabase {

    private static PetDatabase instance;

    public abstract PetDao getPetDao();


    public static synchronized PetDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PetDatabase.class, "pet_database")
                    .fallbackToDestructiveMigration()
                    //.addCallback(roomCallback)
                    .build();

        }
        return instance;


    }


//
//    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
//        }
//    };
//
//    private static class PopulateDbAsyncTask extends AsyncTask <Void, Void, Void> {
//        private PetDao asyncDao;
//
//        private PopulateDbAsyncTask(PetDatabase petDatabase) {
//            asyncDao = petDatabase.getPetDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//           Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_pets);
//            String defaultIcon = ImageUtil.convert(icon);
//
//            asyncDao.insert(new Pet("Vicky", "Cat", 8, defaultIcon));
//            asyncDao.insert(new Pet("Barney", "Dog", 3, defaultIcon));
//            asyncDao.insert(new Pet("Barca", "Guinea Pig", 4, defaultIcon));
//            return null;
//        }
//    }

}


