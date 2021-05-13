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
import android.widget.TextView;

public class Infos_cards extends AppCompatActivity {
    TextView stageinfo;
    Button btn1;
    String username,ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_cards);
        stageinfo=findViewById(R.id.stageinfos);
        btn1 = findViewById(R.id.buttoninfo);
        ///
        Intent i1 = getIntent();
        String formation = i1.getStringExtra("stage");
        username =i1.getStringExtra("username");
        setTitle("Hello "+username.toUpperCase());
        ID=i1.getStringExtra("ID");
        switch (formation){
            case "info" :
                stageinfo.setText("Stagiaire PFE en JAVA/REACT\n" +
                        "Casablanca\n" +
                        "Publiée le: 1 Mar-17:28\n" +
                        "Vue: 79\n" +
                        "Annonce N°: 8599418\n" +
                        "Nous cherchons des stagiaires développeurs en bac+5, avec une maitrise de java/jee et react js, pour un stage pfe à casablanca.\n" +
                        "Domaine : Informatique / Multimédia / Internet\n" +
                        "Fonction : Informatique - Développement\n" +
                        "Contrat : Stage\n" +
                        "Entreprise : confidentiel\n" +
                        "Salaire : 2 000 - 3 000 DH\n" +
                        "Niveau d'études : Bac plus 5");
                break;
            case "meca":
                stageinfo.setText("Stage En Genie Mecanique\n\n \n" +
                        "Casablanca, Grand Casablanca\n \n \n" +
                        "Bonjour, Grand société cherche un jeune pour un stage de 2 à 3 mois,de formation supérieur bac+2(dut) à bac+5 en génie mécanique et productique maîtrisant la conception mécanique et la lecture des plans industriels" +
                        " (solidWorks, Catia v5), disposant d'une réelle capacité d'adaptation, vos qualités relationnelles et votre esprit");
                break;
            case "indus":
                stageinfo.setText("Ingénieurs maintenance / production\n" +
                        "Casablanca\n" +
                        "Publiée le: 11 Feb-14:30\n" +
                        "Vue: 311\n" +
                        "Annonce N°: 8577013\n" +
                        "Entreprise basée à casablanca recrute des ingénieurs maintenance / production de formation en électro-mécanique/ mécanique avec expérience terrain ( stages acceptés ).\n" +
                        "\n" +
                        "Mobilité et disponibilités sont impératifs pour ce poste.\n" +
                        "\n" +
                        "\n" +
                        "Domaine : Industrie / Ingénierie / Energie\n" +
                        "Fonction : Production - Gestion/Maintenance\n" +
                        "Contrat : A discuter\n" +
                        "Entreprise : multinationale\n" +
                        "Salaire : A discuter\n" +
                        "Niveau d'études : Bac plus 5");
                break;
        }
        ///
        btn1.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(),Form_Post.class);
            i.putExtra("stage",formation);
            i.putExtra("username",username);
            i.putExtra("ID",ID);
            startActivity(i);
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