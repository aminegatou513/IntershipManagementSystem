package com.example.demande_stage.AsyncTasks;

import android.app.AlertDialog;
import android.os.AsyncTask;

import com.example.demande_stage.R;
import com.example.demande_stage.ui.requests.requestsFragment;
import com.example.demande_stage.ui.users.usersFragment;

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

public class UpdateTask extends AsyncTask<String,String,String> {
    usersFragment context;
    requestsFragment con;
    public AsyncSetLists delegate = null;
    AlertDialog notificationDialog;
    String type ="";
    public UpdateTask(usersFragment cnx){
        context = cnx;
    }
    public UpdateTask(requestsFragment arg0){ con=arg0; }
    @Override
    protected String doInBackground(String... strings) {
        String updateUser_Url = "http://10.0.2.2/ensamStage/update_user.php";
        String updateRequest_Url = "http://10.0.2.2/ensamStage/accept_refuse.php";
       type = strings[0];
        if(type.equals("user")){
            String user_name = strings[1];
            String email = strings[2];
            String password = strings[3];
            String ID = strings[4];
            try {
                URL url = new URL(updateUser_Url);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String login_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") +
                            "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+
                            "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") +
                            "&" + URLEncoder.encode("role", "UTF-8") + "=" + URLEncoder.encode("user", "UTF-8")+
                            "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8");
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
        // If the type of the request is to update the requests
        if(type.equals("accept_refuse")){
            String id = strings[1];
            String statut = strings[2];
            try {
                URL url = new URL(updateRequest_Url);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String login_data = URLEncoder.encode("id_demande", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") +
                            "&" + URLEncoder.encode("statut", "UTF-8") + "=" + URLEncoder.encode(statut, "UTF-8");
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
        System.out.println(type);
        if(type.equals("accept_refuse")){
            notificationDialog = new AlertDialog.Builder(con.getActivity()).create();
        }
        if(type.equals("user")){
            notificationDialog = new AlertDialog.Builder(context.getActivity()).create();
        }
        notificationDialog.setTitle("Result");
        notificationDialog.setCancelable(false);
        notificationDialog.setIcon(R.drawable.user_icon);
        notificationDialog.setMessage(s);
        notificationDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"DISMISS",(dialog, which)-> notificationDialog.dismiss());
        notificationDialog.show();
        super.onPostExecute(s);
    }
}
