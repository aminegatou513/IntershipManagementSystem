package com.example.demande_stage.ui.requests;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.demande_stage.AsyncTasks.AsyncSetLists;
import com.example.demande_stage.AsyncTasks.UpdateTask;
import com.example.demande_stage.AsyncTasks.getItems;
import com.example.demande_stage.R;
import com.example.demande_stage.ui.users.usersFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class requestsFragment extends Fragment implements AsyncSetLists {

    private requestsViewModel requestsViewModel;
    View root;
    ListView listView;
    AlertDialog dialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        requestsViewModel =
                new ViewModelProvider(this).get(requestsViewModel.class);
         root = inflater.inflate(R.layout.fragment_requests, container, false);
         //Call our list view
        listView = root.findViewById(R.id.list_request);
        //call the getItems class for our result!
        getItems g = new getItems(this);
        g.delegate=this;
        g.execute("request");
        dialog = new AlertDialog.Builder(root.getContext()).create();
        return root;
    }
    //
    private void loadIntoListView(String json) throws JSONException {
        //Getting data from Json Response and putting each one in an array !
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
        String [] usernames = new String[Jarray.length()];
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
            usernames[i] = obj.getString("username");
        }
        //Only Showing the array of what_to_show in the listView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, what_to_show);
        listView.setAdapter(arrayAdapter);
        // Now for every element in list we show all data on click!
        listView.setOnItemClickListener((parent, view, position, id)->{
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
                    "Mastered Softwares : "+logiciel[position]+"\n" +
                    "Account_Username :"+usernames[position]);
            dialog.setButton(AlertDialog.BUTTON_NEUTRAL,"PENDING",(dialog, which)-> {
                UpdateTask up = new UpdateTask(this);
                up.delegate=this;
                up.execute("accept_refuse",ids[position],"pending");
                getParentFragmentManager().beginTransaction().detach(requestsFragment.this).attach(requestsFragment.this).commit();
            });
            //Now we set Two buttons ! First is to Accept the request and second to refuse it !
            dialog.setButton(AlertDialog.BUTTON_POSITIVE,"ACCEPT",(dialog,which)->{
                UpdateTask up = new UpdateTask(this);
                up.delegate=this;
                up.execute("accept_refuse",ids[position],"accepted");
                getParentFragmentManager().beginTransaction().detach(requestsFragment.this).attach(requestsFragment.this).commit();
            });
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE,"REFUSE",(dialog,which)->{
                UpdateTask up = new UpdateTask(this);
                up.delegate=this;
                up.execute("accept_refuse",ids[position],"refused");
                getParentFragmentManager().beginTransaction().detach(requestsFragment.this).attach(requestsFragment.this).commit();
            });
            dialog.show();
        });
        }

        //When task is finished ! ( task = getting data and set it into arrays ! )
    @Override
    public void finishTask(String s) {
        try {
            loadIntoListView(s);
        } catch (JSONException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }catch (Exception e){
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}