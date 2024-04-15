package com.example.diasta;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu_des_choix extends AppCompatActivity {

    private String accountName;  // Ajoutez cette ligne pour stocker le nom du compte

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_des_choix);

        accountName = getIntent().getStringExtra("accountName");  // Récupérez le nom du compte ici

        if (accountName == null) {
            Toast.makeText(this, "Aucun compte n'est configuré. Veuillez vous connecter ou vous inscrire.", Toast.LENGTH_LONG).show();
            finish(); // Fermez l'activité si aucun compte n'est trouvé
            return;  // Ajoutez return pour arrêter l'exécution si aucun compte n'est trouvé
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
        Intent intent = new Intent(Menu_des_choix.this, commentaires.class);
        intent.putExtra("accountName", accountName);
        startActivity(intent);
    }
}