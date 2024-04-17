package com.example.diasta;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    public CustomAdapter(Context context, List<String> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Récupération de l'élément à cette position
        String item = getItem(position);

        // Vérifiez si une vue existante est réutilisée, sinon, inflez la vue
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);

        // Extrait la valeur de glucose de la chaîne
        double glucoseLevel = extractGlucoseLevel(item);

        // Appliquez la logique de coloration
        if (glucoseLevel < 70 || glucoseLevel > 180) {
            textView.setTextColor(Color.RED); // Critique
        } else if ((glucoseLevel >= 70 && glucoseLevel <= 90) || (glucoseLevel > 160 && glucoseLevel <= 180)) {
            textView.setTextColor(Color.YELLOW); // Proche critique
        } else {
            textView.setTextColor(Color.GREEN); // Normal
        }

        textView.setText(item);
        return convertView;
    }

    private double extractGlucoseLevel(String item) {
        try {
            String[] parts = item.split(" : ");
            String valuePart = parts[1].split(" ")[0];
            return Double.parseDouble(valuePart);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
