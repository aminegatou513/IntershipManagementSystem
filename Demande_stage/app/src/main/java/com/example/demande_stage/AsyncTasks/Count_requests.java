package com.example.demande_stage.AsyncTasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.demande_stage.ui.home.HomeFragment;

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

public class Count_requests extends AsyncTask<String,String,String> {
    HomeFragment context;
    public AsyncResponse delegate = null;
    public Count_requests(HomeFragment cnx){
        context = cnx;
    }

    @Override
    protected void onPostExecute(String s) {
        System.out.println(s);
        delegate.process2Finish(s);
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        String count_requests_url = "http://10.0.2.2/ensamStage/count_requests.php";
        String type = strings[0];
        if(type.equals("count_req")){
            try{
                URL url = new URL(count_requests_url);
                try{
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    bufferedWriter.write("");
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

                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }catch(MalformedURLException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
}
