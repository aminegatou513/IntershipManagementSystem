package com.example.demande_stage.AsyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.demande_stage.R;
import com.example.demande_stage.RecapData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class new_requestTask extends AsyncTask<String,String,String> {

    String url_add = "http://10.0.2.2/ensamStage/ajout_demande.php";
    Context context;
    Activity activity;
    public for_users delegate = null;

    public new_requestTask(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        if(type.equals("req")){
            for(int i=0;i<strings.length;i++){
                System.out.println(i+"/"+strings[i]);
            }
            String nom = strings[1];
            String prenom = strings[2];
            String lieu_naiss = strings[3];
            String email = strings[4];
            String CIN = strings[5];
            String code_Apoge = strings[6];
            String adresse = strings[7];
            String langue = strings[8];
            String logiciel = strings[9];
            String user_id = strings[10];
            String titre = strings[11];
            try {
                URL url = new URL(url_add);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String login_data = URLEncoder.encode("titre", "UTF-8") + "=" + URLEncoder.encode(titre, "UTF-8")+
                            "&" +URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(nom, "UTF-8")+
                            "&" +URLEncoder.encode("prenom", "UTF-8") + "=" + URLEncoder.encode(prenom, "UTF-8")+
                            "&" +URLEncoder.encode("lieu_naiss", "UTF-8") + "=" + URLEncoder.encode(lieu_naiss, "UTF-8")+
                            "&" +URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+
                            "&" + URLEncoder.encode("CIN", "UTF-8") + "=" + URLEncoder.encode(CIN, "UTF-8")+
                            "&" + URLEncoder.encode("Code_Apoge", "UTF-8") + "=" + URLEncoder.encode(code_Apoge, "UTF-8")+
                            "&" + URLEncoder.encode("adresse", "UTF-8") + "=" + URLEncoder.encode(adresse, "UTF-8")+
                            "&" +URLEncoder.encode("langue", "UTF-8") + "=" + URLEncoder.encode(langue, "UTF-8")+
                            "&" +URLEncoder.encode("logiciel", "UTF-8") + "=" + URLEncoder.encode(logiciel, "UTF-8")+
                            "&" +URLEncoder.encode("statut", "UTF-8") + "=" + URLEncoder.encode("pending", "UTF-8")+
                            "&" +URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
                    bufferedWriter.write(login_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String result = "";
                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");

                    }
                    result = stringBuilder.toString();

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.finishTask(s,"");
        System.out.println(s);
        super.onPostExecute(s);
    }
}
