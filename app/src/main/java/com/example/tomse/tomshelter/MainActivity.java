package com.example.tomse.tomshelter;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_PET_REQUEST_CODE = 1;
    public static final int EDIT_PET_REQUEST_CODE = 2;
    

    private PetViewModel petViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAddPet = findViewById(R.id.button_add_pet);
        buttonAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditPetActivity.class);
                startActivityForResult(intent, ADD_PET_REQUEST_CODE);

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final PetAdapter petAdapter = new PetAdapter();
        recyclerView.setAdapter(petAdapter);


        petViewModel = ViewModelProviders.of(this).get(PetViewModel.class);
        petViewModel.getAllPets().observe(this, new Observer<List<Pet>>() {
            @Override
            public void onChanged(@Nullable List<Pet> pets) {
                petAdapter.setPets(pets);


            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPetIndex = viewHolder.getAdapterPosition();
                Pet pet = petAdapter.getPetAt(swipedPetIndex);
                petViewModel.delete(pet);
                Toast.makeText(MainActivity.this, "Pet deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        petAdapter.mySetOnItemClickListener(new PetAdapter.MyOnItemClickListener() {
            @Override
            public void myOnItemClick(Pet pet) {
                Intent intent = new Intent(MainActivity.this, AddEditPetActivity.class);

                intent.putExtra(AddEditPetActivity.EXTRA_ID, pet.getId());
                intent.putExtra(AddEditPetActivity.EXTRA_NAME, pet.getName());
                intent.putExtra(AddEditPetActivity.EXTRA_SPECIES, pet.getSpecies());
                intent.putExtra(AddEditPetActivity.EXTRA_AGE, pet.getAge());

                startActivityForResult(intent, EDIT_PET_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PET_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditPetActivity.EXTRA_NAME);
            String species = data.getStringExtra(AddEditPetActivity.EXTRA_SPECIES);
            int age = data.getIntExtra(AddEditPetActivity.EXTRA_AGE, 0);

            Pet pet = new Pet(name, species, age);
            petViewModel.insert(pet);

            Toast.makeText(this, "Pet saved", Toast.LENGTH_SHORT).show();


        } else if (requestCode == EDIT_PET_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditPetActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Pet can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditPetActivity.EXTRA_NAME);
            String species = data.getStringExtra(AddEditPetActivity.EXTRA_SPECIES);
            int age = data.getIntExtra(AddEditPetActivity.EXTRA_AGE, 0);

            Pet pet = new Pet(name, species, age);
            pet.setId(id);
            petViewModel.update(PetAdapter.selectedPet);
            Toast.makeText(this, "Pet updated", Toast.LENGTH_SHORT).show();


        } else
            Toast.makeText(this, "Pet not saved", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_pets:
                petViewModel.delteAll();
                Toast.makeText(this, "All pets deleted", Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
