package com.example.tp2.final_project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MyAsyncTask extends AsyncTask {

    String myUrl = "";
    ArrayList<Data> datas;
    JSONObject myJsonObject;

    Boolean appIsStarted = false;

    @Override
    protected Object doInBackground(Object[] params) {

        datas = (ArrayList<Data>) params[0];
        myUrl = (String) params[1];
        String jsonStr = "";

        try{
            URL url = new URL(myUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            if(urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader input = new BufferedReader(isr);
                StringBuilder stringBuilder = new StringBuilder();
                String temp;

                while((temp = input.readLine()) != null){
                    stringBuilder.append(temp);
                }
                jsonStr = stringBuilder.toString();

                input.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return jsonStr;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        myJsonObject = this.parsJson((String) o);
        this.addDatas();
    }

    private JSONObject parsJson(String jsonStr){

        JSONObject jsonRoot = null;

        try{
            jsonRoot = new JSONObject(jsonStr);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonRoot;
    }

    private void addDatas(){

        try{
            JSONArray array = myJsonObject.getJSONArray("records");
            if(!MainActivity.datas.isEmpty() && array.length() > 0){
                MainActivity.datas.clear();
            }
            for(int i = 0;i<array.length();i++){
                ArrayList<Object> datasArray = new ArrayList<>();
                JSONObject obj = array.getJSONObject(i);
                JSONObject fields = obj.getJSONObject("fields");

                datasArray.add(fields.getString("deplib"));
                datasArray.add((fields.getString("comlib")));
                datasArray.add(fields.getString("equnom"));
                datasArray.add(fields.getInt("equnbequidentique"));
                datasArray.add(fields.getInt("equnbplacetribune"));
                datasArray.add(fields.getString("naturesollib"));
                datasArray.add(fields.getString("naturelibelle"));
                String handi = fields.getString("equacceshandimaire");
                if(handi.equals("Oui")){
                    datasArray.add(true);
                }else{
                    datasArray.add(false);
                }

                try {
                    JSONArray coord = fields.getJSONArray("coordonnees");
                    datasArray.add((double)coord.get(0));
                    datasArray.add((double)coord.get(1));
                }catch (Exception e){
                    e.printStackTrace();
                }

                datas.add(new Data(datasArray));
                MainActivity.datas = datas;

            }

        }catch (Exception e){

        }

        if(!appIsStarted){
            SplashScreen.start();
            appIsStarted = true;
        }else{
            MainActivity.adapter.notifyDataSetChanged();
        }
    }

}
