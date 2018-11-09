package com.example.tomse.tomshelter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditPetActivity extends AppCompatActivity {

    public static final String EXTRA_NAME =
            "com.example.tomse.tomshelter.EXTRA_NAME";

    public static final String EXTRA_SPECIES =
            "com.example.tomse.tomshelter.EXTRA_SPECIES";

    public static final String EXTRA_AGE =
            "com.example.tomse.tomshelter.EXTRA_AGE";

    public static final String EXTRA_ID =
            "com.example.tomse.tomshelter.EXTRA_ID";

    private EditText editName;
    private EditText editSpecies;
    private NumberPicker agePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        editName = findViewById(R.id.edit_name);
        editSpecies = findViewById(R.id.edit_species);
        agePicker = findViewById(R.id.age_picker);

        agePicker.setMaxValue(30);
        agePicker.setMinValue(0);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Pet");
            editName.setText(intent.getStringExtra(EXTRA_NAME));
            editSpecies.setText(intent.getStringExtra(EXTRA_SPECIES));
            agePicker.setValue(intent.getIntExtra(EXTRA_AGE, 0));

        } else {
            setTitle("Add Pet");
        }
    }

    private void savePet() {
        String name = editName.getText().toString().trim();
        String species = editSpecies.getText().toString().trim();
        int age = agePicker.getValue();

        if (name.isEmpty() || species.isEmpty()) {
            Toast.makeText(this, "Please insert name and species", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent savePetIntent = new Intent();
        savePetIntent.putExtra(EXTRA_NAME, name);
        savePetIntent.putExtra(EXTRA_SPECIES, species);
        savePetIntent.putExtra(EXTRA_AGE, age);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            savePetIntent.putExtra(EXTRA_ID, id);

        }

        setResult(RESULT_OK, savePetIntent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_pet_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_pet:
                savePet();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }
}
