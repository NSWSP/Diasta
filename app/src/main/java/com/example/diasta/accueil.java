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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class accueil extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> userInputList;
    private SharedPreferences sharedPreferences;
    private String accountName; // Variable pour stocker le nom du compte

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
        userInputList = new ArrayList<>();
        for (String input : userInputSet) {
            userInputList.add(formatDisplay(input));  // Format each entry for display
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userInputList);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            confirmDeletion(position);
            return true;
        });
    }

    private String formatDisplay(String input) {
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            SimpleDateFormat newFormat = new SimpleDateFormat("EEE, MMM d, yyyy 'at' HH:mm", Locale.getDefault());
            String[] parts = input.split(" : ");  // Split the date-time part from the value
            Date date = originalFormat.parse(parts[0]);
            return newFormat.format(date) + " : " + parts[1];
        } catch (Exception e) {
            e.printStackTrace();
            return input;  // Return original input in case of formatting failure
        }
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
