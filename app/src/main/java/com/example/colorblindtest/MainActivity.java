package com.example.colorblindtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;

public class MainActivity extends AppCompatActivity {
    NavController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller= Navigation.findNavController(this,R.id.fragmentContainerView);
        NavigationUI.setupActionBarWithNavController(this,controller);
    }

    //override the back arrow button function at the option bar
    @Override
    public boolean onSupportNavigateUp() {
        //if the current fragment is question fragment
        if (controller.getCurrentDestination().getId()==R.id.questionFragment3){
            //initialize the dialog
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            //set title for the dialog
            builder.setTitle(getString(R.string.exit_confirmation));
            //set no button
            builder.setPositiveButton(R.string.no_btn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            //set yes button
            builder.setNegativeButton(R.string.yes_btn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //back to previous
                    controller.navigateUp();
                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
        //if current showing fragment is on false answer list
        else if (controller.getCurrentDestination().getId() == R.id.falseAnswerListFragment){
            //will back to result fragment
            controller.navigate(R.id.winFragment);
        }
        //if current showing fragment is on ranking fragment will back to home screen
        else if(controller.getCurrentDestination().getId()==R.id.rankingFragment){
            controller.navigate(R.id.homeFragment);
        }

        return super.onNavigateUp();
    }
    //override the back button at the navigation bar of android same as above
    @Override
    public void onBackPressed() {
        onSupportNavigateUp();

    }
}