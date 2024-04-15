package com.example.diasta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class commentaires extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentaires);

        Intent intent = getIntent();
        String glucoseLevel = intent.getStringExtra("glucoseLevel");
        String time = intent.getStringExtra("time");

        TextView textViewAdvice = findViewById(R.id.textViewAdvice);
        textViewAdvice.setText(generateAdvice(glucoseLevel, time));
    }

    private String generateAdvice(String glucoseLevel, String time) {
        // Ici, vous définissez la logique pour générer des conseils basés sur le taux de sucre et l'heure
        double glucoseValue = Double.parseDouble(glucoseLevel.split(" ")[0]);
        if (glucoseValue < 70) {
            return "Risque d'hypoglycémie. Considérez de manger quelque chose de sucré.";
        } else if (glucoseValue > 180) {
            return "Taux de sucre élevé. Il est conseillé de consulter votre médecin.";
        } else {
            return "Taux de sucre normal. Continuez votre routine habituelle.";
        }
    }
}