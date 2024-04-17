package com.example.diasta;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Menu_des_choix extends AppCompatActivity {

    private String accountName;  // Variable pour stocker le nom du compte

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_des_choix);

        accountName = getIntent().getStringExtra("accountName");

        if (accountName == null) {
            Toast.makeText(this, "Aucun compte n'est configuré. Veuillez vous connecter ou vous inscrire.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Button btnStats = findViewById(R.id.btnstats);
        Button btnCommentaire = findViewById(R.id.btncommentaire);

        btnStats.setOnClickListener(v -> navigateToAccueil());
        btnCommentaire.setOnClickListener(v -> navigateToCommentaire());
    }

    private void navigateToAccueil() {
        Intent intent = new Intent(Menu_des_choix.this, accueil.class);
        intent.putExtra("accountName", accountName);
        startActivity(intent);
    }

    private void navigateToCommentaire() {
        String lastMeasurement = getLastMeasurement();
        if (lastMeasurement == null) {
            Toast.makeText(this, "Aucune mesure disponible.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Menu_des_choix.this, commentaires.class);
        intent.putExtra("accountName", accountName);
        intent.putExtra("lastMeasurement", lastMeasurement);
        startActivity(intent);
    }

    private String getLastMeasurement() {
        SharedPreferences sharedPreferences = getSharedPreferences(accountName, MODE_PRIVATE);
        Set<String> measurements = sharedPreferences.getStringSet("userInputs", new HashSet<>());
        if (measurements.isEmpty()) {
            return null;
        } else {
            ArrayList<String> sortedMeasurements = new ArrayList<>(measurements);
            Collections.sort(sortedMeasurements, Collections.reverseOrder());
            return sortedMeasurements.get(0); // Retourne la dernière mesure
        }
    }
}
