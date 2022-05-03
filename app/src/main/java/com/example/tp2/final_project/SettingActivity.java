package com.example.tp2.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    protected Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        spinner = (Spinner) findViewById(R.id.spinner);

        List exempleList = new ArrayList();
        exempleList.add("Sport pratiqué");
        exempleList.add("Terrain de Football");
        exempleList.add("Terrain de Pétanque");
        exempleList.add("Skate Park");
        exempleList.add("Terrain de Tennis");
        exempleList.add("Mur d'Escalade");
        exempleList.add("Salle de Musculation");
        exempleList.add("Terrain Multisports");
        exempleList.add("Salle de Danse");
        exempleList.add("City-Stade");

        ArrayAdapter spinner_adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                exempleList
        );

        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinner.setAdapter(spinner_adapter);

        Button maps = findViewById(R.id.map_Affichage);

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent map = new Intent(SettingActivity.this, EquipementMaps.class);

                ArrayList<Object> markers = new ArrayList<>();

                for (int i = 0; i < MainActivity.datas.size(); i++) {
                    markers.add((double) MainActivity.datas.get(i).getLat());
                    markers.add((double) MainActivity.datas.get(i).getLng());
                    markers.add(MainActivity.datas.get(i).getNom());
                }
                map.putExtra("marker", markers);
                startActivity(map);
                finish();
            }
        });

        ImageView searchImage  = findViewById(R.id.search_IV);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isOnline()){
                    Toast.makeText(SettingActivity.this,"Votre connexion n'est pas bonne veuillez réessayer plus tard",Toast.LENGTH_LONG).show();
                }else{
                    Intent shareContact = new Intent();

                    EditText postal = findViewById(R.id.postal);
                    TextInputEditText city = findViewById(R.id.city);
                    TextInputEditText dpt = findViewById(R.id.dpt);
                    RadioButton interieur = findViewById(R.id.interieur);
                    RadioButton decouvert = findViewById(R.id.decouvert);

                    String link = "";

                    if(!postal.getEditableText().toString().equals("")){
                        link  = link + "&refine.depcode=" + postal.getText().toString();
                    }
                    if(!city.getEditableText().toString().equals("")){
                        String tmp = city.getEditableText().toString().replaceAll(" ","-");
                        link = link + "&refine.comlib=" +tmp;
                    }
                    if (!dpt.getEditableText().toString().equals("")){
                        String tmp = dpt.getEditableText().toString().replaceAll(" ","-");
                        link = link + "&refine.deplib="+tmp;
                    }
                    if(interieur.isChecked()){
                        link = link+"&refine.naturelibelle=Intérieur";
                    }else if(decouvert.isChecked()){
                        link = link+"&refine.naturelibelle=Découvert";
                    }
                    if(!spinner.getSelectedItem().toString().equals("Sport pratiqué")){
                        switch (spinner.getSelectedItem().toString()){
                            default:
                                link += link + "&refine.insnom=" + spinner.getSelectedItem().toString();
                        }
                    }
                    shareContact.putExtra("link",link);
                    setResult(RESULT_OK, shareContact);
                    finish();
                    }
                }

        });
    }
    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}