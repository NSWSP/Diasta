package com.example.diasta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class accueil extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Button btnRetourversMain = findViewById(R.id.btnRetourversMain);

        // Définissez un écouteur de clics sur le bouton
        btnRetourversMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créez une Intent pour démarrer l'activité Accueil
                Intent intent = new Intent(accueil.this, MainActivity.class);
                startActivity(intent); // Démarrez l'activité
            }
        });
    }
}