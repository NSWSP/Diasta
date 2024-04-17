package com.example.diasta;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class commentaires extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentaires);

        // Récupérer les données transmises par l'intent
        String lastMeasurement = getIntent().getStringExtra("lastMeasurement");

        // Définir les TextView pour afficher la mesure et le commentaire
        TextView measurementTextView = findViewById(R.id.measurementTextView);
        TextView commentTextView = findViewById(R.id.commentTextView);

        // Afficher la mesure
        measurementTextView.setText(lastMeasurement);

        // Générer et afficher le commentaire
        commentTextView.setText(generateComment(lastMeasurement));
    }

    private String generateComment(String measurement) {
        if (measurement != null && !measurement.isEmpty()) {
            // Extrait la valeur numérique du glucose
            String glucoseString = measurement.split(" : ")[1].split(" ")[0];
            double glucoseLevel = Double.parseDouble(glucoseString);

            // Générer un commentaire en fonction du niveau de glucose
            if (glucoseLevel < 70) {
                return "Hypoglycémie: Attention, votre taux de sucre est trop bas!";
            } else if (glucoseLevel > 180) {
                return "Hyperglycémie: Attention, votre taux de sucre est trop élevé!";
            } else {
                return "Taux de sucre normal. Continuez comme ça!";
            }
        }
        return "Mesure non disponible";
    }
}
