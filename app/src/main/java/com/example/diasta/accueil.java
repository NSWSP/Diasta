package com.example.diasta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Button btnOuvrirAccueil = findViewById(R.id.btnRetourversMain);
        Button ajout_de_mesure = findViewById(R.id.ajout_de_mesure);
        ListView listView = findViewById(R.id.listViewDisplay);

        SharedPreferences sharedPreferences = getSharedPreferences("UserInputPrefs", MODE_PRIVATE);
        // Récupération des données sous forme de Set, conversion en ArrayList
        Set<String> userInputSet = sharedPreferences.getStringSet("userInputs", new HashSet<>());
        ArrayList<String> userInputList = new ArrayList<>(userInputSet);

        // Utilisation d'un ArrayAdapter pour lier les données à ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userInputList);
        listView.setAdapter(adapter);

        // Définissez un écouteur de clics sur le bouton
        btnOuvrirAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créez une Intent pour démarrer l'activité Accueil
                Intent intent = new Intent(accueil.this, MainActivity.class);
                startActivity(intent); // Démarrez l'activité
            }
        });
        ajout_de_mesure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créez une Intent pour démarrer l'activité Accueil
                Intent intent = new Intent(accueil.this, graph.class);
                startActivity(intent); // Démarrez l'activité
            }
        });
    }
}