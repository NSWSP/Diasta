package com.example.diasta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnOuvrirAccueil = findViewById(R.id.btnOuvrirAccueil);

        // Définissez un écouteur de clics sur le bouton
        btnOuvrirAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créez une Intent pour démarrer l'activité Accueil
                Intent intent = new Intent(MainActivity.this, accueil.class);
                startActivity(intent); // Démarrez l'activité
            }
        });
    }
}