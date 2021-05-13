package com.example.demande_stage;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demande_stage.AsyncTasks.AsyncSetLists;
import com.example.demande_stage.AsyncTasks.UpdateTask;
import com.example.demande_stage.AsyncTasks.for_users;
import com.example.demande_stage.AsyncTasks.getItems;
import com.example.demande_stage.ui.requests.requestsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class user_dash extends AppCompatActivity implements AsyncSetLists, for_users {
    //We need to get User ID before doing anything!
    String ID =" ";
    String username =" ";
    ListView list ;
    AlertDialog dialog;
    FloatingActionButton add ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        list = findViewById(R.id.list_request);
        add = findViewById(R.id.floatingActionButton2);
        Intent i = getIntent();
        username =i.getStringExtra("username");
        setTitle("Hello "+username.toUpperCase());
        getItems g = new getItems(this);
        g.delegate=this;
        g.execute("get_ID",username);
        //
        add.setOnClickListener(v -> {
            Intent i1 = new Intent(getApplicationContext(),MainActivity.class);
            i1.putExtra("username",username);
            i1.putExtra("ID",ID);
            startActivity(i1);
            System.out.println(ID);
        });
    }

    @Override
    public void finishTask(String s) {
        //This method get us the ID returned by the asyncTask !
        ID = s;
        System.out.println(ID);
        //Now method to get all requests of the user connected !
        getItems gu= new getItems(this);
        gu.del=this;
        gu.execute("us_req",ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_user_drawer,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home){
            Intent i = new Intent(getApplicationContext(), user_dash.class);
            i.putExtra("username",username);
            i.putExtra("ID",ID);
            startActivity(i);
        }
        if(id == R.id.nav_logout){
            Intent i = new Intent(getApplicationContext(), MainActivity2.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadIntoListView(String json) throws JSONException{
        //Getting data from Json Response and putting each one in an array !
        System.out.println(json+"hhh");

        if(json.equals("No Requests found"+"\n")){
            //Set the array to view list empty !
            ArrayList<String> e = new ArrayList();
            e.add(json);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,e);
            list.setAdapter(arrayAdapter);
        }else if(json.equals("Error"+"\n")){
            ArrayList<String> e = new ArrayList();
            e.add(json);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, e);
            list.setAdapter(arrayAdapter);
        } else{
            JSONObject object = new JSONObject(json);
            JSONArray Jarray  = object.getJSONArray("answer");
            String[] titles = new String[Jarray.length()];
            String [] ids = new String[Jarray.length()];
            String [] first_names = new String[Jarray.length()];
            String [] last_names = new String[Jarray.length()];
            String [] city = new String[Jarray.length()];
            String [] emails = new String[Jarray.length()];
            String [] cins = new String[Jarray.length()];
            String [] apgs = new String[Jarray.length()];
            String [] adress = new String [Jarray.length()];
            String [] logiciel = new String[Jarray.length()];
            String [] langues = new String[Jarray.length()];
            String [] statut = new String [Jarray.length()];
            //this array is only created for " what to show " purposes
            String [] what_to_show = new String[Jarray.length()];
            //Lopping through JSONArray to get the data we need and separate them
            for (int i = 0; i < Jarray.length(); i++)
            {
                JSONObject obj = Jarray.getJSONObject(i);
                what_to_show[i] = "Request ID : "+obj.getString("id_demande")+"\nRequest Name : "+obj.getString("titre")+"\nStatut : "+obj.getString("statut");
                titles[i] = obj.getString("titre");
                ids[i] = obj.getString("id_demande");
                first_names[i] = obj.getString("prenom");
                last_names[i] = obj.getString("nom");
                city[i] = obj.getString("lieu_naiss");
                emails[i] = obj.getString("email");
                cins[i] = obj.getString("CIN");
                apgs[i] = obj.getString("Code_Apoge");
                adress[i] = obj.getString("adresse");
                langues[i] = obj.getString("langue");
                logiciel[i] = obj.getString("logiciel");
                statut[i] = obj.getString("statut");
            }
            //Only Showing the array of what_to_show in the listView
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, what_to_show);
            list.setAdapter(arrayAdapter);
            list.setOnItemClickListener((parent, view, position, id) -> {
                dialog = new AlertDialog.Builder(this).create();
                //On Each Item of the list show dialog with User details!
                dialog.setTitle("Request Details");
                dialog.setCancelable(false);
                dialog.setIcon(R.drawable.request);
                dialog.setMessage(what_to_show[position]+"\n" +
                        "First_Name : "+first_names[position]+"\n" +
                        "Last_Name :"+last_names[position]+"\n" +
                        "City of Birth :"+city[position]+"\n" +
                        "Email : "+emails[position]+"\n" +
                        "CIN : "+cins[position]+"\n" +
                        "Code Apoge : "+apgs[position]+"\n" +
                        "Adress : "+adress[position]+"\n" +
                        "Mastered languages : "+langues[position]+"\n" +
                        "Mastered Softwares : "+logiciel[position]+"\n");
                dialog.setButton(AlertDialog.BUTTON_NEUTRAL,"DISSMISS",(dialog, which)-> dialog.dismiss());
                dialog.show();
            });
        }

    }
    @Override
    public void finishTask(String result, String helper) {
        // This method will load the listView with the data we need
        try{
            loadIntoListView(result);
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
