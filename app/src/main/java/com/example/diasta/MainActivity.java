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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter un nouveau compte");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Créer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String accountName = input.getText().toString();
                if (!accountName.isEmpty()) {
                    createAccount(accountName);
                } else {
                    Toast.makeText(MainActivity.this, "Le nom du compte ne peut pas être vide.", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void createAccount(String accountName) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserAccounts", MODE_PRIVATE);
        Set<String> accounts = new HashSet<>(sharedPreferences.getStringSet("accountNames", new HashSet<>()));
        if (accounts.contains(accountName)) {
            Toast.makeText(this, "Ce nom de compte existe déjà.", Toast.LENGTH_LONG).show();
        } else {
            accounts.add(accountName);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("accountNames", accounts);
            editor.apply();

            Intent intent = new Intent(this, Menu_des_choix.class);
            intent.putExtra("accountName", accountName);
            startActivity(intent);
        }
    }

    private void selectAccount() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserAccounts", MODE_PRIVATE);
        Set<String> accountNames = new HashSet<>(sharedPreferences.getStringSet("accountNames", new HashSet<>()));

        if (accountNames.isEmpty()) {
            Toast.makeText(this, "Aucun compte enregistré. Veuillez en créer un.", Toast.LENGTH_LONG).show();
        } else {
            final String[] accountsArray = accountNames.toArray(new String[0]);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choisissez le compte:");
            builder.setItems(accountsArray, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String accountName = accountsArray[which];
                    Intent intent = new Intent(MainActivity.this, Menu_des_choix.class);
                    intent.putExtra("accountName", accountName); // Assurez-vous que 'selectedAccountName' est correct
                    startActivity(intent);
                }
            });

            builder.show();
        }
    }
}
