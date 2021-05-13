package com.example.demande_stage.ui.users;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.demande_stage.AsyncTasks.AsyncSetLists;
import com.example.demande_stage.AsyncTasks.DeleteTask;
import com.example.demande_stage.AsyncTasks.UpdateTask;
import com.example.demande_stage.AsyncTasks.getItems;
import com.example.demande_stage.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class usersFragment extends Fragment implements AsyncSetLists {

    private usersViewModel usersViewModel;
    ListView listView;
    AlertDialog dialog,update_dialog,delete_dialog;
    LinearLayout lila1;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        usersViewModel =
                new ViewModelProvider(this).get(usersViewModel.class);
        root = inflater.inflate(R.layout.fragment_users, container, false);
        listView = root.findViewById(R.id.list_users);
        getItems g = new getItems(this);
        g.delegate=this;
        g.execute("users");
        dialog = new AlertDialog.Builder(root.getContext()).create();
        update_dialog = new AlertDialog.Builder(root.getContext()).create();
        delete_dialog=new AlertDialog.Builder(root.getContext()).create();
        ///
       //empty the view to avoid creating new over old
        if(update_dialog!=null){
            container.removeView(root);
        }
        lila1= new LinearLayout(root.getContext());
        //loadIntoListView(s);
        return root;
    }
    ///
    private void loadIntoListView(String json) throws JSONException {
        //Getting data from Json Response and putting each one in an array !
        JSONObject object = new JSONObject(json);
        JSONArray Jarray  = object.getJSONArray("users");
        String[] webchrz = new String[Jarray.length()];
        String [] ids = new String[Jarray.length()];
        String [] emails = new String[Jarray.length()];
        String [] passwords = new String[Jarray.length()];
        String [] what_to_show = new String[Jarray.length()];
        for (int i = 0; i < Jarray.length(); i++)
        {
            JSONObject obj = Jarray.getJSONObject(i);
            what_to_show[i] = "User ID : "+obj.getString("id")+"\nUsername : "+obj.getString("username");
            webchrz[i]=obj.getString("username");
            ids[i] = obj.getString("id");
            emails [i] = obj.getString("email");
            passwords[i] = obj.getString("password");
        }
        //Only Showing the array of username in the listView
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, what_to_show);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            //On Each Item of the list show dialog with User details!
            dialog.setTitle("User Details");
            dialog.setCancelable(false);
            dialog.setIcon(R.drawable.user_icon);
            dialog.setMessage("User ID :"+ ids[position]+"\n" +
                    "Username :"+webchrz[position]+"\n" +
                    "Email : "+emails[position]+" \n" +
                    "Password : "+passwords[position]);
            //ON CLICK UPDATE
            dialog.setButton(AlertDialog.BUTTON_POSITIVE,"UPDATE ", (dialog, which) -> {
                //Update the USER
                dialog.dismiss();
                //Update DialogAlert!
                update_dialog.setTitle("Update User");
                EditText name,email,password;
                name = new EditText(root.getContext());
                email = new EditText(root.getContext());
                password = new EditText(root.getContext());
                name.setText(webchrz[position]);
                name.setBackgroundColor(Color.BLACK);
                name.setTextColor(Color.WHITE);
                //
                email.setText(emails[position]);
                email.setBackgroundColor(Color.BLACK);
                email.setTextColor(Color.WHITE);
                //
                password.setText(passwords[position]);
                password.setBackgroundColor(Color.BLACK);
                password.setTextColor(Color.WHITE);
                    lila1.addView(name);
                    lila1.addView(email);
                    lila1.addView(password);
                    update_dialog.setView(lila1);
                update_dialog.setCancelable(false);
                update_dialog.setIcon(R.drawable.user_icon);
                lila1.setOrientation(LinearLayout.VERTICAL); //1 is for vertical orientation
                update_dialog.setButton(AlertDialog.BUTTON_POSITIVE,"Confirm",((dialog1, which1) -> {
                    if(name.getText().toString().equals("") || email.getText().toString().equals("") || password.getText().toString().equals("")){
                        Toast.makeText(root.getContext(),"Can't leave blank!",Toast.LENGTH_SHORT).show();
                    }else{
                        UpdateTask up = new UpdateTask(this);
                        up.delegate=this;
                        up.execute("user",name.getText().toString(),email.getText().toString(),password.getText().toString(),ids[position]);
                        update_dialog.cancel();
                        //remove all views to avoid creating new EditTexts everytime!
                        lila1.removeAllViewsInLayout();
                        getParentFragmentManager().beginTransaction().detach(usersFragment.this).attach(usersFragment.this).commit();
                    }
                }));
                update_dialog.setButton(AlertDialog.BUTTON_NEGATIVE,"DISMISS",(dialog2, which2)-> {
                    update_dialog.dismiss();
                    lila1.removeAllViewsInLayout();
                });
                update_dialog.show();


            });
            //Delete user !
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE,"DELETE",(dialog, which)-> {
                dialog.dismiss();
                //Delete Dialog!
                delete_dialog.setTitle("Delete User");
                delete_dialog.setTitle("User Details");
                delete_dialog.setCancelable(true);
                delete_dialog.setIcon(R.drawable.user_icon);
                delete_dialog.setMessage("Last Confirmation before deleting ! Are you sure?");
                delete_dialog.setButton(AlertDialog.BUTTON_POSITIVE,"Confirm",((dialog1, which1) -> {
                    //Call the HTTP request to delete the user!
                    DeleteTask delete= new DeleteTask(this);
                    delete.execute("user",ids[position]);
                    getParentFragmentManager().beginTransaction().detach(usersFragment.this).attach(usersFragment.this).commit();
                }));
                delete_dialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancel",((dialog1, which1) -> delete_dialog.dismiss()));
                delete_dialog.show();
                //End of delete Dialog
            });
            dialog.setButton(AlertDialog.BUTTON_NEUTRAL,"DISMISS",(dialog, which)-> dialog.dismiss());
            dialog.show();
        });
    }
    
    @Override
    public void finishTask(String s) {

        try {
            loadIntoListView(s);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}