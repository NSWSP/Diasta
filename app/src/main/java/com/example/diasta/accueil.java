package com.example.diasta;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class accueil extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> userInputList;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Button btnOuvrirAccueil = findViewById(R.id.btnRetourversMain);
        Button ajout_de_mesure = findViewById(R.id.ajout_de_mesure);
        ListView listView = findViewById(R.id.listViewDisplay);

        sharedPreferences = getSharedPreferences("UserInputPrefs", MODE_PRIVATE);
        loadData(listView);

        setupButtonListeners(btnOuvrirAccueil, ajout_de_mesure);
    }


    private void loadData(ListView listView) {
        // Récupération des données sous forme de Set, conversion en ArrayList
        Set<String> userInputSet = new HashSet<>(sharedPreferences.getStringSet("userInputs", new HashSet<>()));
        userInputList = new ArrayList<>(userInputSet);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userInputList);
        listView.setAdapter(adapter);

        // Ajout d'un écouteur de clic long sur les éléments de la liste pour la suppression
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            confirmDeletion(position);
            return true;
        });
    }

    private void confirmDeletion(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Supprimer l'entrée")
                .setMessage("Voulez-vous supprimer cette entrée ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    String itemToRemove = userInputList.remove(position);
                    adapter.notifyDataSetChanged(); // Rafraîchir l'adapter
                    updateSharedPreferences(); // Mise à jour des SharedPreferences
                    Toast.makeText(accueil.this, "Entrée supprimée", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Non", null)
                .show();
    }

    private void updateSharedPreferences() {
        Set<String> updatedInputs = new HashSet<>(userInputList);
        sharedPreferences.edit().putStringSet("userInputs", updatedInputs).apply();
    }

    private void setupButtonListeners(Button btnOuvrirAccueil, Button ajout_de_mesure) {
        btnOuvrirAccueil.setOnClickListener(v -> {
            Intent intent = new Intent(accueil.this, MainActivity.class);
            startActivity(intent);
        });
        ajout_de_mesure.setOnClickListener(v -> {
            Intent intent = new Intent(accueil.this, graph.class);
            startActivity(intent);
        });
    }
}
