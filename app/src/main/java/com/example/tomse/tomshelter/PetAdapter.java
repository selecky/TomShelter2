package com.example.tomse.tomshelter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetHolder> {
    private List<Pet> pets = new ArrayList<>();
    private MyOnItemClickListener myListener;
    public static Pet selectedPet;


    @NonNull
    @Override
    public PetHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pet_card, viewGroup, false);

        return new PetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PetHolder petHolder, int i) {
        Pet currentPet = pets.get(i);

        petHolder.nameView.setText(currentPet.getName());
        petHolder.speciesView.setText(currentPet.getSpecies());
        petHolder.ageView.setText(String.valueOf(currentPet.getAge()));


    }

    @Override
    public int getItemCount() {
        return pets.size();
    }


    public void setPets(List<Pet> pets) {
        this.pets = pets;
        notifyDataSetChanged();
    }

    public Pet getPetAt(int position) {
        return pets.get(position);
    }


    class PetHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView speciesView;
        private TextView ageView;


        public PetHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.name_view);
            speciesView = itemView.findViewById(R.id.species_view);
            ageView = itemView.findViewById(R.id.age_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (myListener != null && position != RecyclerView.NO_POSITION) {
                        myListener.myOnItemClick(pets.get(position));
                        selectedPet = pets.get(position);
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
