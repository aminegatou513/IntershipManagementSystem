package com.example.demande_stage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demande_stage.AsyncTasks.AsyncSetLists;
import com.example.demande_stage.AsyncTasks.BackgroundTask;

public class MainActivity2 extends AppCompatActivity implements AsyncSetLists {

    EditText user,pass;
    Button login;
    RadioGroup radioGroup;
    RadioButton value;
    TextView text,msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);
        user = findViewById(R.id.username);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.btn1);
        radioGroup = (RadioGroup)findViewById(R.id.group_radio);
        text = findViewById(R.id.create_acc);
        msg = findViewById(R.id.msg);
        radioGroup.clearCheck();
        user.setText("");
        pass.setText("");
        text.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),registerActivity.class) ;
            startActivity(i);
        });
    }
    public void login_process(View view){
        String username = user.getText().toString();
        String password= pass.getText().toString();
        String type = "";
        int checked = radioGroup.getCheckedRadioButtonId();
        if(checked == -1){
            Toast.makeText(this,"You must choose either to log in as Admin OR User",Toast.LENGTH_LONG).show();
            msg.setText("You must choose either to log in as Admin OR User");
        }else{
            value = findViewById(checked);
            if(value.getText().equals("Admin")){
                type="admin";
            }else if(value.getText().equals("User")){
                type="user";
            }
            //System.out.println(type);
            BackgroundTask backgroundTask= new BackgroundTask(getApplicationContext(),this);
            backgroundTask.delegate=this;
            backgroundTask.execute(type, username, password);
        }

    }

    @Override
    public void finishTask(String s) {
        msg.setText(s);
    }
}