package com.example.demande_stage.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.demande_stage.AsyncTasks.AsyncResponse;
import com.example.demande_stage.AsyncTasks.Count_requests;
import com.example.demande_stage.R;
import com.example.demande_stage.AsyncTasks.Count_users;

public class HomeFragment extends Fragment implements AsyncResponse {
    private HomeViewModel homeViewModel;
    TextView text,text2;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        Count_users user_numbers = new Count_users(this);
        user_numbers.delegate=this;
        user_numbers.execute("count_user");
        text = root.findViewById(R.id.number_users);
        Count_requests req = new Count_requests(this);
        req.delegate=this;
        req.execute("count_req");
        text2 = root.findViewById(R.id.number_requests);
        return root;
    }

    @Override
    public void processFinish(String output) {
        text.setText("TOTAL USERS :  "+output);
    }

    @Override
    public void process2Finish(String output) {
        text2.setText("TOTAL REQUESTS :   "+output);
    }
}
////////////////////
//////////////////////////
