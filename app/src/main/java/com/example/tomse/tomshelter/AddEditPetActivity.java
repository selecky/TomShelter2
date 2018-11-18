package com.example.tomse.tomshelter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEditPetActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 3;

    public static final String EXTRA_NAME =
            "com.example.tomse.tomshelter.EXTRA_NAME";

    public static final String EXTRA_SPECIES =
            "com.example.tomse.tomshelter.EXTRA_SPECIES";

    public static final String EXTRA_AGE =
            "com.example.tomse.tomshelter.EXTRA_AGE";

    public static final String EXTRA_ID =
            "com.example.tomse.tomshelter.EXTRA_ID";

    public static final String EXTRA_PHOTO_STRING =
            "com.example.tomse.tomshelter.EXTRA_PHOTO";

    private EditText editName;
    private EditText editSpecies;
    private NumberPicker agePicker;
    private Button setImageButton;
    private ImageView editPhoto;
    

    private String photoString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        editName = findViewById(R.id.edit_name);
        editSpecies = findViewById(R.id.edit_species);
        editPhoto = findViewById(R.id.edit_photo);


        setImageButton = findViewById(R.id.set_image_button);
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

            photoString = intent.getStringExtra(EXTRA_PHOTO_STRING);
            Bitmap bitmap = ImageUtil.convert(photoString);
            editPhoto.setImageBitmap(bitmap);

        } else {
            setTitle("Add Pet");
        }

        setImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }


        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            editPhoto.setImageBitmap(imageBitmap);

            photoString = ImageUtil.convert(imageBitmap);


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

        if (photoString != null && !photoString.isEmpty()) {
            savePetIntent.putExtra(EXTRA_PHOTO_STRING, photoString);
        } else {
            Toast.makeText(this, "Take a photo of the pet!", Toast.LENGTH_LONG).show();
            return;
        }

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
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
