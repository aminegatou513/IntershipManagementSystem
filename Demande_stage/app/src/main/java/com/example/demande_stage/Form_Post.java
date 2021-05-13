package com.example.demande_stage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Form_Post extends AppCompatActivity {
    EditText nom,prenom,lieu_nais,email,cin,code_apo,adrs;
    TextView title;
    AlertDialog alert;
    Button next;
    String username,ID;

    /// fonction
    public void alertError(String message){
        alert.setTitle("Error");
        alert.setMessage(message);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
    //VALIDATE EMAIL
    /**
     * validate your email address format. Ex-akhi@mani.com
     */
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form__post);
        alert = new AlertDialog.Builder(this).create();
        ///
        title=findViewById(R.id.stage_title);
        Intent i1 = getIntent();
        String formation = i1.getStringExtra("stage");
        username =i1.getStringExtra("username");
        setTitle("Hello "+username.toUpperCase());
        ID=i1.getStringExtra("ID");
        switch (formation){
            case "info":
                title.setText("Stage dev JAVA");
                break;
            case "meca":
                title.setText("Stage Conception Mecanique");
                break;
            case "indus":
                title.setText("Stage Production");
                break;
            default:
                title.setText("");
        }
        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        lieu_nais = findViewById(R.id.lieu_naiss);
        email = findViewById(R.id.email);
        cin = findViewById(R.id.cin);
        code_apo = findViewById(R.id.code_apg);
        adrs= findViewById(R.id.adresse);
        next = findViewById(R.id.button_next);

        next.setOnClickListener(v -> {
            if(nom.getText().toString().equals("") || prenom.getText().toString().equals("") || lieu_nais.getText().toString().equals("") || email.getText().toString().equals("") || cin.getText().toString().equals("") || code_apo.getText().toString().equals("") || adrs.getText().toString().equals("")){
                alertError("Items cannot be Blank");
            }else{
                try{
                    String mail = email.getText().toString();
                    if(!emailValidator(mail)){
                        alertError("Email format not supported");
                    }else{
                        Intent data = new Intent(getApplicationContext(),tech_info.class);
                        String nm = nom.getText().toString();
                        String pr = prenom.getText().toString();
                        String lieu = lieu_nais.getText().toString();
                        String ci = cin.getText().toString();
                        String code_apoge = code_apo.getText().toString();
                        String adress = adrs.getText().toString();
                        data.putExtra("nom",nm);
                        data.putExtra("prenom",pr);
                        data.putExtra("lieu_nais",lieu);
                        data.putExtra("mail",mail);
                        data.putExtra("cin",ci);
                        data.putExtra("code_apogee",code_apoge);
                        data.putExtra("adress",adress);
                        data.putExtra("stage",formation);
                        data.putExtra("username",username);
                        data.putExtra("ID",ID);
                        startActivity(data);
                    }
                }catch (Exception e){
                    alertError("Erreur Inconnu !!");
                }

            }
        });
    }
    ////
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
}