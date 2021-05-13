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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class tech_info extends AppCompatActivity {

    CheckBox cb1,cb2,cb3,cb4,cb5,cb6;
    EditText t1,t2;
    Button next;
    AlertDialog alert;
    TextView title;
    String username,ID;
    static int nb_check;
    //////
    public void alertError(String message){
        alert.setTitle("Error");
        alert.setMessage(message);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", (dialog, which) -> dialog.dismiss());
        alert.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        alert = new AlertDialog.Builder(this).create();
        setContentView(R.layout.activity_tech_info);
        title=findViewById(R.id.stage_title);
        ///
        next=findViewById(R.id.button_next);
        ////
        cb1=findViewById(R.id.checkBox);
        cb2=findViewById(R.id.checkBox2);
        cb3=findViewById(R.id.checkBox3);
        cb4=findViewById(R.id.checkBox4);
        cb5=findViewById(R.id.checkBox7);
        cb6=findViewById(R.id.checkBox8);
        //
        t1 = findViewById(R.id.logiciel);
        t2 = findViewById(R.id.comp);
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
        /////////////////
        next.setOnClickListener(v -> {
            if(t1.getText().toString().equals("") || t2.getText().toString().equals("")){
                alertError("Vous devez remplir les zones de textes!");
            }
            else{
                Intent data = new Intent(getApplicationContext(), RecapData.class);
                data.putExtra("logiciel",t1.getText().toString());
                data.putExtra("Comp",t2.getText().toString());
                /////
                data.putExtra("nom",i1.getStringExtra("nom"));
                data.putExtra("prenom",i1.getStringExtra("prenom"));
                data.putExtra("lieu_nais",i1.getStringExtra("lieu_nais"));
                data.putExtra("mail",i1.getStringExtra("mail"));
                data.putExtra("cin",i1.getStringExtra("cin"));
                data.putExtra("code_apogee",i1.getStringExtra("code_apogee"));
                data.putExtra("adress",i1.getStringExtra("adress"));
                ArrayList<String> checked_comp = new ArrayList<String>();
                ArrayList<String> checked_lang = new ArrayList<String>();
                if(cb1.isChecked()){
                    nb_check++;
                    checked_lang.add("Francais");
                    //data.putExtra("fr","Francais");
                }
                if(cb2.isChecked()){
                    nb_check++;
                    checked_lang.add("Arabe");
                    //data.putExtra("ar","Arabe");
                }
                if(cb3.isChecked()){
                    nb_check++;
                    checked_lang.add("Anglais");
                    //data.putExtra("ang","Anglais");
                }
                if(cb4.isChecked()){
                    nb_check++;
                    checked_lang.add("Allemand");
                    //data.putExtra("deu","Allemand");
                }
                if(cb5.isChecked()){
                    nb_check++;
                    checked_comp.add("Office");
                    //data.putExtra("off","Office");
                }
                if(cb6.isChecked()){
                    nb_check++;
                    checked_comp.add("Matlab");
                    //data.putExtra("mat","Matlab");
                }
                if(nb_check==0){
                    checked_comp.clear();
                    checked_lang.clear();
                    //data.putExtra("check_null","Vous n'avez chosi aucune competence");
                }
                data.putStringArrayListExtra("checked_comp",checked_comp);
                data.putStringArrayListExtra("checked_lang",checked_lang);
                data.putExtra("stage",formation);
                data.putExtra("username",username);
                data.putExtra("ID",ID);
                startActivity(data);
            }
        });
        ///
    }
    //
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