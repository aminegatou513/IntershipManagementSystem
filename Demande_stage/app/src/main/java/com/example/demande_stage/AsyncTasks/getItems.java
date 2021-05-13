package com.example.demande_stage.AsyncTasks;

import android.os.AsyncTask;

import com.example.demande_stage.ui.home.HomeFragment;
import com.example.demande_stage.ui.requests.requestsFragment;
import com.example.demande_stage.ui.users.usersFragment;
import com.example.demande_stage.user_dash;

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

public class getItems extends AsyncTask<String,String,String> {
    String user_url ="http://10.0.2.2/ensamStage/all_users.php";
    String request_url ="http://10.0.2.2/ensamStage/all_requests.php";
    String user_id ="http://10.0.2.2/ensamStage/get_user_ID.php";
    String request_user = "http://10.0.2.2/ensamStage/get_requests_user.php";
    usersFragment context;
    requestsFragment con;
    user_dash us;
    public AsyncSetLists delegate = null;
    public for_users del = null;
    boolean helper = false;
    public getItems(usersFragment cnx){
        context = cnx;
    }
    public getItems(requestsFragment cnx){ con=cnx; }

    public getItems(user_dash user_dash) {
        us = user_dash;
    }

    ///////////
    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        if(type.equals("users")){
            try {
                URL url = new URL(user_url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String json;
                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json + "\n");
                }
                bufferedReader.close();
                con.disconnect();
                return sb.toString().trim();
            } catch (Exception e) {
                return null;
            }
        }
        /// pour les demandes !
        if(type.equals("request")){
            try {
                URL url = new URL(request_url);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String json;
                while ((json = bufferedReader.readLine()) != null) {
                    sb.append(json + "\n");
                }
                bufferedReader.close();
                con.disconnect();
                return sb.toString().trim();
            } catch (Exception e) {
                return null;
            }
        }
        if(type.equals("get_ID")){
            String username = strings[1];
            try{
                URL url = new URL(user_id);
                try{
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String login_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
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

                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }catch(MalformedURLException e){
                System.out.println(e.getMessage());
            }
        }
        if(type.equals("us_req")){
            String id = strings[1];
            helper =true;
            try{
                URL url = new URL(request_user);
                try{
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String login_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id ,"UTF-8");
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

                }catch(IOException e){
                    System.out.println(e.getMessage());
                }
            }catch(MalformedURLException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if(helper){
            del.finishTask(s,"test");
        }else{
            delegate.finishTask(s);
        }
        //System.out.println(s);
        super.onPostExecute(s);
    }
}
