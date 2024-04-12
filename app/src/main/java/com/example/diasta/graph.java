package com.example.diasta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class graph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
    }

    // Méthode appelée lorsque l'utilisateur clique sur le bouton
    public void submitData(View view) {
        EditText editText = findViewById(R.id.editTextUserInput);
        String userInput = editText.getText().toString().trim();

        // Validation que l'entrée est un nombre décimal
        try {
            double value = Double.parseDouble(userInput);
            String formattedInput = String.format("%.2f mg/l", value); // Formater avec deux décimales

            // Charger les données existantes de SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserInputPrefs", MODE_PRIVATE);
            Set<String> existingInputs = sharedPreferences.getStringSet("userInputs", new HashSet<>());

            // Créer une nouvelle copie du Set pour permettre la modification
            Set<String> updatedInputs = new HashSet<>(existingInputs);
            updatedInputs.add(formattedInput); // Ajouter la nouvelle entrée formatée

            // Sauvegarder la liste mise à jour dans SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("userInputs", updatedInputs);
            editor.apply();

            editText.setText(""); // Efface le champ après l'ajout
            Toast.makeText(this, "Données ajoutées", Toast.LENGTH_SHORT).show();

            // Rediriger vers l'activité accueil
            Intent intent = new Intent(this, accueil.class);
            startActivity(intent);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Veuillez entrer un nombre valide.", Toast.LENGTH_SHORT).show();
        }
    }
}
