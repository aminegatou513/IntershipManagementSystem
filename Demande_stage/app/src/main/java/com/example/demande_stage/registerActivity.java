package com.example.demande_stage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demande_stage.AsyncTasks.BackgroundTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class registerActivity extends AppCompatActivity {

    EditText username,email,password;
    Button signup;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        email = findViewById(R.id.et_email);
        //
        signup = findViewById(R.id.button_signin);
        text = findViewById(R.id.text);
    }
    public void signinProcess(View view){
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String ema = email.getText().toString();
        if(user.equals("") || pass.equals("") || ema.equals("")){
            text.setText("Items Cannot Be Blank!");
        }
        else{
            if(!emailValidator(ema)){
                text.setText("Please Provide a valid email ! Ex : xxx@gmail.xxx");
            }
            else{
                String type="signin";
                BackgroundTask backgroundTask= new BackgroundTask(getApplicationContext(),this);
                backgroundTask.execute(type, user, ema,pass);
            }
        }

    }
    ///////////
    public void goBack(View view){
        Intent i = new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(i);
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
}