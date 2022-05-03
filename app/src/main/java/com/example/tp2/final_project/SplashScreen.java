
package com.example.tp2.final_project;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    protected String link = "https://public.opendatasoft.com/api/records/1.0/search/?dataset=res_equipements_2017&q=&sort=equdatecreation&facet=comlib&facet=equipementtypelib&facet=gestiontypeproprietaireprinclib&facet=naturesollib&facet=naturelibelle&facet=equacceshandimaire&facet=caracteristiques";
    static ArrayList<Data> datas = new ArrayList<>();
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acitvity);
        mContext = this;

        Button refresh = findViewById(R.id.refresh);

        if(!isOnline()){

            refresh.setVisibility(View.VISIBLE);
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isOnline()){
                    new MyAsyncTask().execute(datas, link);
                    refresh.setVisibility(View.INVISIBLE);
                    }
                }
            });

        }else{
            new MyAsyncTask().execute(datas, link);
        }

    }

    public static void start(){
        Intent main = new Intent(mContext,MainActivity.class);
        mContext.startActivity(main);
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
