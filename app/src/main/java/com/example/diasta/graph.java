package com.example.diasta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class graph extends AppCompatActivity {

    private ArrayList<String> userInputs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
    }

    // Méthode appelée lorsque l'utilisateur clique sur le bouton
    public void submitData(View view) {
        EditText editText = findViewById(R.id.editTextUserInput);
        String userInput = editText.getText().toString().trim();

        if(userInput.isEmpty()){
            Toast.makeText(this, "Veuillez entrer des données.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Charger les données existantes de SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserInputPrefs", MODE_PRIVATE);
        Set<String> existingInputs = sharedPreferences.getStringSet("userInputs", new HashSet<>());

        // Créer une nouvelle copie du Set pour permettre la modification
        Set<String> updatedInputs = new HashSet<>(existingInputs);

        // Ajouter la nouvelle entrée utilisateur à la liste existante
        updatedInputs.add(userInput);

        // Sauvegarder la liste mise à jour dans SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("userInputs", updatedInputs);
        editor.apply();

        editText.setText(""); // Efface le champ après l'ajout

        // Pas besoin de passer les données ici puisque SecondActivity les chargera de SharedPreferences
        Intent intent = new Intent(this, accueil.class);
        startActivity(intent);
    }

}