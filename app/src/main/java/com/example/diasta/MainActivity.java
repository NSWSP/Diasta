package com.example.diasta;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnOuvrirAccueil = findViewById(R.id.btnOuvrirAccueil);
        Button btnAjouterCompte = findViewById(R.id.btnAjouterCompte); // Assurez-vous que ce bouton est dans votre layout

        btnOuvrirAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAccount();
            }
        });

        btnAjouterCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount();
            }
        });
    }

    private void addAccount() {
        // Affichez une boîte de dialogue avec un champ de texte pour le nom du compte
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter un nouveau compte");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Créer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String accountName = input.getText().toString();
                createAccount(accountName);
            }
        });
        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void createAccount(String accountName) {
        if (!accountName.isEmpty()) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserAccounts", MODE_PRIVATE);
            Set<String> accounts = sharedPreferences.getStringSet("accountNames", new HashSet<>());
            if (accounts.contains(accountName)) {
                Toast.makeText(MainActivity.this, "Ce nom de compte existe déjà.", Toast.LENGTH_LONG).show();
                return;
            }

            accounts.add(accountName);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("accountNames", accounts);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, accueil.class);
            intent.putExtra("accountName", accountName);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, "Le nom du compte ne peut pas être vide.", Toast.LENGTH_LONG).show();
        }
    }

    private void selectAccount() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserAccounts", MODE_PRIVATE);
        Set<String> accountNames = sharedPreferences.getStringSet("accountNames", new HashSet<>());

        final String[] accountsArray = accountNames.toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Choisissez le compte:");
        builder.setItems(accountsArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String accountName = accountsArray[which];
                Intent intent = new Intent(MainActivity.this, accueil.class);
                intent.putExtra("accountName", accountName);
                startActivity(intent);
            }
        });

        // Si aucun compte n'est enregistré, affichez un message
        if (accountNames.isEmpty()) {
            Toast.makeText(MainActivity.this, "Aucun compte enregistré. Veuillez en créer un.", Toast.LENGTH_LONG).show();
            return;
        }

        builder.show();
    }
}
