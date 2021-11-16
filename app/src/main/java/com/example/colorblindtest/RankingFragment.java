package com.example.colorblindtest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RankingFragment extends Fragment {
    RecyclerView mRecyclerView;
    MyViewModel myViewModel;
    MyAdapter myAdapter;

    public RankingFragment(){
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_ranking,container,false);

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.clearData:
                AlertDialog.Builder builder= new AlertDialog.Builder(requireActivity());
                builder.setTitle(R.string.clear_screen);
                builder.setPositiveButton(R.string.confirm_msg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myViewModel.deleteAllUsers();
                    }
                });
                builder.setNegativeButton(R.string.cancel_msg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create();
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu,menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        myViewModel=new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(getActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        mRecyclerView=view.findViewById(R.id.ranking_recycler_view);
        myAdapter=new MyAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(myAdapter);
        myViewModel=new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(getActivity().getApplication(),requireActivity())).get(MyViewModel.class);
        myViewModel.getAllUsersLive().observe(requireActivity(), new Observer<List<Rank>>() {
            @Override
            public void onChanged(List<Rank> ranks) {
                myAdapter.setAllusers(ranks);
                myAdapter.notifyDataSetChanged();
            }
        });
    }
    
}
