package com.example.tp2.final_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Data> datas = new ArrayList<>();
    static EquipementAdapter adapter;
    RecyclerView recyclerView;
    protected DrawerLayout drawerLayout;
    protected Spinner spinner;
    // Recensement des équipements sportifs - Equipements - 2017
    protected String link = "https://public.opendatasoft.com/api/records/1.0/search/?dataset=res_equipements_2017&q=&sort=equdatecreation&facet=comlib&facet=equipementtypelib&facet=gestiontypeproprietaireprinclib&facet=naturesollib&facet=naturelibelle&facet=equacceshandimaire&facet=caracteristiques";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.burgerMenu);

        adapter = new EquipementAdapter(this, datas);

        recyclerView = findViewById(R.id.lv_equipement);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.hamburgermenu:
                Intent settings = new Intent(this, SettingActivity.class);
                startActivityForResult(settings, 1000);
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == RESULT_OK){
                if (data != null) {
                    String tmp = link + (String) data.getSerializableExtra("link");
                    new MyAsyncTask().execute(datas,tmp);

                }
            }
        }
    }

    @Override
    public void onBackPressed(){
        return;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.WriteSettings();
    }

    public void WriteSettings(){
        FileOutputStream fOut = null;
        ObjectOutputStream osw = null;

        try{
            fOut = openFileOutput("savingFile",MODE_APPEND);
            osw = new ObjectOutputStream(fOut);
            for(int i =0;i <datas.size();i++){
                osw.writeObject(datas.get(i));
                osw.flush();
            }
        }
        catch (Exception e) {

        }
        finally {
            try {
                osw.close();
                fOut.close();
            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.ReadSettings();
        adapter.notifyDataSetChanged();
    }

    public void ReadSettings(){
        FileInputStream fIn = null;
        ObjectInputStream isr = null;

        ArrayList<Data> inputBuffer = new ArrayList<>();
        Data data = null;

        try{
            fIn = openFileInput("savingFile");
            isr = new ObjectInputStream(fIn);

            while((Data)isr.readObject() != null){
                datas.add((Data) isr.readObject());
            }
        }
        catch (Exception e) {

        }
        finally {
               try {
                      isr.close();
                      fIn.close();
                      } catch (IOException e) {
               }
        }
    }

}