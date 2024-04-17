package com.example.diasta;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class accueil extends AppCompatActivity {

    private CustomAdapter adapter;
    private ArrayList<String> userInputList;
    private SharedPreferences sharedPreferences;
    private String accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Button ajoutDeMesure = findViewById(R.id.ajout_de_mesure);
        ListView listView = findViewById(R.id.listViewDisplay);

        accountName = getIntent().getStringExtra("accountName");
        sharedPreferences = getSharedPreferences(accountName, MODE_PRIVATE);
        loadData(listView);

        ajoutDeMesure.setOnClickListener(v -> {
            Intent intent = new Intent(accueil.this, graph.class);
            intent.putExtra("accountName", accountName);
            startActivity(intent);
        });
    }

    private void loadData(ListView listView) {
        Set<String> userInputSet = new HashSet<>(sharedPreferences.getStringSet("userInputs", new HashSet<>()));
        userInputList = new ArrayList<>(userInputSet);
        Collections.sort(userInputList, new Comparator<String>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

            @Override
            public int compare(String o1, String o2) {
                try {
                    Date date1 = dateFormat.parse(o1.split(" : ")[0]);
                    Date date2 = dateFormat.parse(o2.split(" : ")[0]);
                    return date2.compareTo(date1); // Tri décroissant
                } catch (Exception e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });

        adapter = new CustomAdapter(this, userInputList);
        listView.setAdapter(adapter);

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
                    adapter.notifyDataSetChanged();
                    updateSharedPreferences();
                    Toast.makeText(accueil.this, "Entrée supprimée", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Non", null)
                .show();
    }

    private void updateSharedPreferences() {
        Set<String> updatedInputs = new HashSet<>(userInputList);
        sharedPreferences.edit().putStringSet("userInputs", updatedInputs).apply();
    }
}
