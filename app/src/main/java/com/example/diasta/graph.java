package com.example.diasta;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class graph extends AppCompatActivity {

    private String accountName; // Variable membre pour le nom du compte
    private TextView textViewTime; // TextView pour afficher l'heure sélectionnée

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        accountName = getIntent().getStringExtra("accountName");
        textViewTime = findViewById(R.id.textViewTime); // Assurez-vous que ce TextView est correctement configuré dans votre layout

        textViewTime.setOnClickListener(v -> {
            Calendar currentTime = Calendar.getInstance();
            int hour = currentTime.get(Calendar.HOUR_OF_DAY);
            int minute = currentTime.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(graph.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                    textViewTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minuteOfHour));
                }
            }, hour, minute, true);

            timePickerDialog.setTitle("Sélectionnez l'heure");
            timePickerDialog.show();
        });
    }

    public void submitData(View view) {
        EditText editTextMeasurement = findViewById(R.id.editTextMeasurement);
        String userInput = editTextMeasurement.getText().toString().trim();
        String timeInput = textViewTime.getText().toString();

        if (userInput.isEmpty() || timeInput.equals("Cliquez pour choisir l'heure")) {
            Toast.makeText(this, "Veuillez entrer une mesure et sélectionner une heure.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double value = Double.parseDouble(userInput);
            if (value < 0 || value > 200) {
                Toast.makeText(this, "La mesure doit être entre 0 et 200 mg/dl.", Toast.LENGTH_SHORT).show();
                return;
            }

            String formattedInput = timeInput + " : " + String.format("%.2f mg/dl", value);

            SharedPreferences sharedPreferences = getSharedPreferences(accountName, MODE_PRIVATE);
            Set<String> existingInputs = sharedPreferences.getStringSet("userInputs", new HashSet<>());
            Set<String> updatedInputs = new HashSet<>(existingInputs);
            updatedInputs.add(formattedInput);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("userInputs", updatedInputs);
            editor.apply();

            editTextMeasurement.setText("");
            textViewTime.setText("Cliquez pour choisir l'heure"); // Réinitialiser le TextView après l'ajout
            Toast.makeText(this, "Donnée ajoutée", Toast.LENGTH_SHORT).show();

            // Préparer un intent pour retourner à l'accueil avec le nom du compte inclus
            Intent intent = new Intent(this, accueil.class);
            intent.putExtra("accountName", accountName);
            startActivity(intent);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Veuillez entrer un nombre valide pour la mesure.", Toast.LENGTH_SHORT).show();
        }
    }
}
