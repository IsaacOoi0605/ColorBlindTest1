package com.example.colorblindtest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.colorblindtest.databinding.FragmentHomeBinding;

import java.util.List;


public class HomeFragment extends Fragment {
    MyViewModel myViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //initialize viewmodel
        myViewModel=new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(getActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        FragmentHomeBinding binding;
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        //set the line input to the username from shared preferences
        binding.usernameInput.setText(myViewModel.getKeyUsername().getValue());
        //if username is entered then button start and change username shown up
        if(myViewModel.IS_LOGGEDIN==false){
            binding.usernameInput.setVisibility(View.VISIBLE);
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.btnStart.setVisibility(View.GONE);
            binding.btnChange.setVisibility(View.GONE);
        }
        //else it will be hidden
        else {
            binding.usernameInput.setVisibility(View.GONE);
            binding.btnLogin.setVisibility(View.GONE);
            binding.btnStart.setVisibility(View.VISIBLE);
            binding.btnChange.setVisibility(View.VISIBLE);
        }
        //proceed to the ranking fragment
        binding.btnViewRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(view);
                controller.navigate(R.id.action_homeFragment_to_rankingFragment2);

            }
        });
        //to enter the username
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //avoid it to be empty
                if(binding.usernameInput.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), R.string.username_empty, Toast.LENGTH_SHORT).show();
                }
                else {
                    //remove the spacing infront and behind the text
                    String name=binding.usernameInput.getText().toString().trim();
                //set the username to the value entered
                myViewModel.getKeyUsername().setValue(name);
                //save the username to saved preferences
                myViewModel.saveKeyUsername();
                //set text of the line input to the username been entered
                binding.usernameInput.setText(myViewModel.getKeyUsername().getValue());
                //button start and change shown up and hide the line input and login button
                binding.usernameInput.setVisibility(View.GONE);
                binding.btnLogin.setVisibility(View.GONE);
                binding.btnStart.setVisibility(View.VISIBLE);
                binding.btnChange.setVisibility(View.VISIBLE);
                //set the status to logged in
                myViewModel.setLoggedIn(); }
            }
        });
        //launch the test
        binding.btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_homeFragment_to_questionFragment3);
                myViewModel.getFalseAnswer().clear();
                myViewModel.getCurrentScore().setValue(0);
                myViewModel.getIndex().setValue(0);
                myViewModel.getQuestionNum().setValue(1);
                myViewModel.IS_STARTED=false;
            }
        });

        binding.btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.usernameInput.setVisibility(View.VISIBLE);
                binding.btnLogin.setVisibility(View.VISIBLE);
                binding.btnStart.setVisibility(View.GONE);
                binding.btnChange.setVisibility(View.GONE);
                myViewModel.setLoggedOut();
            }
        });
        return binding.getRoot();
    }


}