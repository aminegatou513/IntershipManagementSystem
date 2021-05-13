package com.example.demande_stage.AsyncTasks;

import android.app.AlertDialog;
import android.os.AsyncTask;

import com.example.demande_stage.R;
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

public class DeleteTask extends AsyncTask<String,String,String> {
    usersFragment context;
    public AsyncSetLists delegate = null;
    AlertDialog notificationDialog;
    public DeleteTask(usersFragment cnx){
        context = cnx;
    }
    @Override
    protected String doInBackground(String... strings) {
        String deleteUser_Url = "http://10.0.2.2/ensamStage/delete_user.php";
        String type = strings[0];
        if(type.equals("user")){
            String ID = strings[1];
            try {
                URL url = new URL(deleteUser_Url);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String login_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(ID, "UTF-8");
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
    //

    @Override
    protected void onPostExecute(String s) {
        notificationDialog = new AlertDialog.Builder(context.getActivity()).create();
        notificationDialog.setTitle("Delete Statuts");
        notificationDialog.setCancelable(false);
        notificationDialog.setIcon(R.drawable.user_icon);
        notificationDialog.setMessage(s);
        notificationDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"DISMISS",(dialog, which)-> notificationDialog.dismiss());
        notificationDialog.show();
        super.onPostExecute(s);
    }
}
