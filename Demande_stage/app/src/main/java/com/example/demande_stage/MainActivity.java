package com.example.demande_stage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
  Button b1,b2,b3;
  String stage;
    String username,ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1=findViewById(R.id.b1);
        Button b2=findViewById(R.id.b2);
        Button b3=findViewById(R.id.b3);
        Intent a = getIntent();
        username =a.getStringExtra("username");
        setTitle("Hello "+username.toUpperCase());
        ID=a.getStringExtra("ID");
        //////
        b1.setOnClickListener(v -> {
            Intent i1 = new Intent(getApplicationContext(),Infos_cards.class);
            stage="info";
            i1.putExtra("stage",stage);
            i1.putExtra("username",username);
            i1.putExtra("ID",ID);
            startActivity(i1);
        });
        b2.setOnClickListener(v -> {
            Intent i1 = new Intent(getApplicationContext(),Infos_cards.class);
            stage="meca";
            i1.putExtra("stage",stage);
            i1.putExtra("username",username);
            i1.putExtra("ID",ID);
            startActivity(i1);
        });
        ////
        b3.setOnClickListener(v -> {
            Intent i1 = new Intent(getApplicationContext(),Infos_cards.class);
            stage="indus";
            i1.putExtra("stage",stage);
            i1.putExtra("username",username);
            i1.putExtra("ID",ID);
            startActivity(i1);
        });
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