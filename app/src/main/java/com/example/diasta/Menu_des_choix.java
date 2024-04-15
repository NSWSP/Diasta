package com.example.diasta;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu_des_choix extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_des_choix);

        Button btnStats = findViewById(R.id.btnstats);
        Button btnCommentaire = findViewById(R.id.btncommentaire);

        btnStats.setOnClickListener(v -> navigateToAccueil());
        btnCommentaire.setOnClickListener(v -> navigateToCommentaire());  // Modifier pour conduire à l'activité Commentaire
    }

    private void navigateToAccueil() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String accountName = sharedPreferences.getString("accountName", null);

        if (accountName != null) {
            Intent intent = new Intent(menu_des_choix.this, accueil.class);
            intent.putExtra("accountName", accountName);
            startActivity(intent);
        } else {
            // Faire quelque chose si le nom de compte n'est pas trouvé
        }
    }

    private void navigateToCommentaire() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String accountName = sharedPreferences.getString("accountName", null);

        if (accountName != null) {
            Intent intent = new Intent(menu_des_choix.this, commentaires.class);
            intent.putExtra("accountName", accountName);
            startActivity(intent);
        } else {
            // Faire quelque chose si le nom de compte n'est pas trouvé
        }
    }
}
