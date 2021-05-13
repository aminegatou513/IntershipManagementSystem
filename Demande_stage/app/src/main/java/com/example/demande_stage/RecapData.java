package com.example.demande_stage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demande_stage.AsyncTasks.for_users;
import com.example.demande_stage.AsyncTasks.new_requestTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RecapData extends AppCompatActivity implements for_users {
    AlertDialog alert;
    TextView stage_title,text_name,text_pr,text_lieun,text_email,cin,code_ap_,text_ad,text_lang,text_comp;
    Button download,submit;
    CreatePdf pdfcreator = new CreatePdf();
    Bitmap bmp,scaledbmp;
    String username,ID;
    //public static String DIRECTORY_DOCUMENTS = "Documents"; // DEFAULT DIRECTORY TO STORE PDF FILES
    /// fonction
    public void alertError(String message,String type){
        alert= new AlertDialog.Builder(this).create();
        alert.setTitle(type);
        alert.setMessage(message);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", (dialog, which) -> alert.dismiss());
        alert.show();
        alert.dismiss();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap_data);
        //set the title
        stage_title = findViewById(R.id.stage_title);
        //--------------//
        text_name = findViewById(R.id.text_nom);
        text_pr = findViewById(R.id.text_prenom);
        text_lieun = findViewById(R.id.text_lieu);
        text_email = findViewById(R.id.text_email);
        cin = findViewById(R.id.text_cin);
        code_ap_ = findViewById(R.id.text_code);
        text_ad = findViewById(R.id.text_adr);
        text_lang = findViewById(R.id.text_lang);
        text_comp =findViewById(R.id.text_comp);
        ///
        download = findViewById(R.id.pdf);
        submit=findViewById(R.id.submit);
        ///
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ensamcasa);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 200, 150, false);
        // below code is used for
        // checking our permissions.
        if (checkPermission()) {
            //System.out.println("here");
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            //System.out.println("heho");
            requestPermission();
        }
        Intent i1 = getIntent();
        String formation = i1.getStringExtra("stage");
        username =i1.getStringExtra("username");
        setTitle("Hello "+username.toUpperCase());
        ID=i1.getStringExtra("ID");
        switch (formation){
            case "info":
                stage_title.setText("Stage dev JAVA");
                break;
            case "meca":
                stage_title.setText("Stage Conception Mecanique");
                break;
            case "indus":
                stage_title.setText("Stage Production");
                break;
            default:
                stage_title.setText("");
        }
        /// get the data from the form  and show it in the table layout
        try{
            text_name.setText(i1.getStringExtra("nom"));
            text_pr.setText(i1.getStringExtra("prenom"));
            text_lieun.setText(i1.getStringExtra("lieu_nais"));
            text_email.setText(i1.getStringExtra("mail"));
            cin.setText(i1.getStringExtra("cin"));
            code_ap_.setText(i1.getStringExtra("code_apogee"));
            text_ad.setText(i1.getStringExtra("adress"));
            // Get checkBox Values
            ArrayList<String> checkBoxValues_comp = i1.getStringArrayListExtra("checked_comp");
            ArrayList<String> checkBoxValues_lang = i1.getStringArrayListExtra("checked_lang");
            StringBuilder lang = new StringBuilder();
            StringBuilder comp = new StringBuilder();
            // if the checkbox are empty set the text to "none" else put the value from the arraylist
            if(checkBoxValues_comp.isEmpty()){
                text_comp.setText("None");
            }else{
                for(String str : checkBoxValues_comp){
                    comp.append(str+"--");
                }
                text_comp.setText(comp);
            }
            ////
            if(checkBoxValues_lang.isEmpty()){
                text_lang.setText("None");
            }else{
                for(String str : checkBoxValues_lang){
                    lang.append(str+"--");
                }
                text_lang.setText(lang);
            }

        }catch(Exception e){
            alertError("Sorry an error has occured try later !","Error");
            finishAndRemoveTask();
        }
        //// download Button to generate the pdf !
        download.setOnClickListener(v -> generatePDF());
        //// submit the data to the database !
        submit.setOnClickListener(v -> {
            new_requestTask a = new new_requestTask(getApplicationContext(),this);
            a.delegate =this;
            a.execute("req",text_name.getText().toString(),text_pr.getText().toString()
            ,text_lieun.getText().toString(),text_email.getText().toString(),cin.getText().toString(),code_ap_.getText().toString(),
                    text_ad.getText().toString(),text_lang.getText().toString(),text_comp.getText().toString(),ID,stage_title.getText().toString());
        });
    }
    private void generatePDF(){
        // creating an object variable
        // for our PDF document.
        PdfDocument pdfDocument = new PdfDocument();

        // two variables for paint "paint" is used
        // for drawing shapes and we will use "title"
        // for adding text in our PDF file.
        Paint paint = new Paint();
        Paint title = new Paint();

        // we are adding page info to our PDF file
        // in which we will be passing our pageWidth,
        // pageHeight and number of pages and after that
        // we are calling it to create our PDF.
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pdfcreator.getPagewidth(), pdfcreator.getPageHeight(), 1).create();

        // below line is used for setting
        // start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        // creating a variable for canvas
        // from our page of PDF.
        Canvas canvas = myPage.getCanvas();

        // below line is used to draw our image on our PDF file.
        // the first parameter of our drawbitmap method is
        // our bitmap
        // second parameter is position from left
        // third parameter is position from top and last
        // one is our variable for paint.
        canvas.drawBitmap(scaledbmp, 56, 40, paint);

        // below line is used for adding typeface for
        // our text which we will be adding in our PDF file.
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        // below line is used for setting text size
        // which we will be displaying in our PDF file.
        title.setTextSize(20);

        // below line is sued for setting color
        // of our text inside our PDF file.
        title.setColor(ContextCompat.getColor(this, R.color.purple_200));

        // below line is used to draw text in our PDF file.
        // the first parameter is our text, second parameter
        // is position from start, third parameter is position from top
        // and then we are passing our variable of paint which is title.
        canvas.drawText("Recu d'inscription au "+stage_title.getText(), 340, 140, title);
        canvas.drawText("EnsamMobileApp", 340, 80, title);

        // similarly we are creating another text and in this
        // we are aligning this text to center of our PDF file.
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        title.setColor(ContextCompat.getColor(this, R.color.black));
        title.setTextSize(18);



        title.setTextAlign(Paint.Align.LEFT);
        title.setStyle(Paint.Style.FILL);
        //title.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Nom : "+text_name.getText(), 40, 200, title);
        canvas.drawText("Prenom : "+text_pr.getText(), 40, 250, title);
        canvas.drawText("Email : "+text_email.getText(), 40, 300, title);
        canvas.drawText("Lieu de naissance : "+text_lieun.getText(), 40, 350, title);
        canvas.drawText("CIN : "+cin.getText(), 40, 400, title);
        canvas.drawText("CodeApogé : "+code_ap_.getText(), 40, 450, title);
        canvas.drawText("Adresse : "+text_ad.getText(), 40, 500, title);
        canvas.drawText("Competences: "+text_comp.getText(), 40, 550, title);
        canvas.drawText("Langues: "+text_lang.getText(), 40, 600, title);
        /////
        title.setStyle(Paint.Style.STROKE);
        title.setStrokeWidth(2);
        title.setTextSize(25);
        canvas.drawText("Ce pdf doit être caché /signé au sein de l'ENSAM pour être valable. \n Merci pour votre Intêret. ",40,700,title);

        // after adding all attributes to our
        // PDF file we will be finishing our page.
        pdfDocument.finishPage(myPage);

        // below line is used to set the name of
        // our PDF file and its path.
        /*ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getExternalFilesDir(Environment.DIRECTORY_DCIM);
        stage_title.setText(Environment.DIRECTORY_DCIM);
        File file = new File(directory+File.separator+"recap.pdf");*/ //
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "recap_"+text_name.getText()+".pdf");
        //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/recap.pdf");

        try {
            // after creating a file name we will
            // write our PDF file to that location.
            pdfDocument.writeTo(new FileOutputStream(file));

            // below line is to print toast message
            // on completion of PDF generation.
            Toast.makeText(RecapData.this, "PDF file downloaded!.", Toast.LENGTH_SHORT).show();
            alertError("This is not an Error! Only Warning. Phones with Android 10+ can't run this method since its not secured anymore.\n","Pdf Downloaded Succesfully");
        } catch (IOException e) {
            // below line is used
            // to handle error
            //e.printStackTrace();
            alertError("Your android Version doesn't support this feature \n Error Code : "+e.getMessage(),"Error");
        }
        // after storing our pdf to that
        // location we are closing our PDF file.
        pdfDocument.close();
    }
    //// function to check the permissions depending on the Android SDK Version
    private boolean checkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        }
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int permission3 = ContextCompat.checkSelfPermission(getApplicationContext(),MANAGE_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        }else{
            // requesting permissions if not provided.
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, CreatePdf.PERMISSION_REQUEST_CODE);
        }

    }
    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CreatePdf.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean READ_EXTERNAL_STORAGE = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean WRITE_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                        // perform action when allow permission success
                    } else {
                        Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
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

    @Override
    public void finishTask(String s, String helper) {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("New Internship request");
        alert.setCancelable(false);
        alert.setIcon(R.drawable.request);
        alert.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",((dialog, which) -> {
            alert.dismiss();
            Intent i = new Intent(getApplicationContext(), user_dash.class);
            i.putExtra("username",username);
            i.putExtra("ID",ID);
            startActivity(i);
        }));
        if(s.equals("accepted"+"\n")){
            alert.setMessage("Request sent & Waiting for confirmation! You can view the details in your dashboard !");
        }else if (s.equals("error"+"\n")){
            alert.setMessage("Database Error!");
        }
        else{
            System.out.println(s);
            alert.setMessage("Internal Server Error!");
        }
        alert.show();
    }
}