package com.example.tomse.tomshelter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends ListAdapter<Pet, PetAdapter.PetHolder> {
    private MyOnItemClickListener myListener;

    public PetAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Pet> DIFF_CALLBACK = new DiffUtil.ItemCallback<Pet>() {
        @Override
        public boolean areItemsTheSame(@NonNull Pet oldPet, @NonNull Pet newPet) {
            return oldPet.getId() == newPet.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Pet oldPet, @NonNull Pet newPet) {
            return oldPet.getName().equals(newPet.getName()) &&
                    oldPet.getSpecies().equals(newPet.getSpecies()) &&
                    oldPet.getAge() == newPet.getAge() &&
               oldPet.getPhoto() == newPet.getPhoto();
        }
    };


    @NonNull
    @Override
    public PetHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pet_card, viewGroup, false);

        return new PetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PetHolder petHolder, int position) {
        Pet currentPet = getItem(position);

        petHolder.nameView.setText(currentPet.getName());
        petHolder.speciesView.setText(currentPet.getSpecies());
        petHolder.ageView.setText(String.valueOf(currentPet.getAge()));
        petHolder.ageViewSuffix.setText(" years old");

        if (currentPet.getPhoto() != null && !currentPet.getPhoto().isEmpty()) {
            Bitmap photoBitmap = ImageUtil.convert(currentPet.getPhoto());
            petHolder.photoView.setImageBitmap(photoBitmap);

        }


    }


    public Pet getPetAt(int position) {
        return getItem(position);
    }


    class PetHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView speciesView;
        private TextView ageView;
        private ImageView photoView;
        private TextView ageViewSuffix;


        public PetHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name_view);
            speciesView = itemView.findViewById(R.id.species_view);
            ageView = itemView.findViewById(R.id.age_view);
            photoView = itemView.findViewById(R.id.photo_view);
            ageViewSuffix = itemView.findViewById(R.id.age_view_suffix);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (myListener != null && position != RecyclerView.NO_POSITION) {
                        myListener.myOnItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface MyOnItemClickListener {
        void myOnItemClick(Pet pet);
    }

    public void mySetOnItemClickListener(MyOnItemClickListener listener) {
        this.myListener = listener;

    }
}
