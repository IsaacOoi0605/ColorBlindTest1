package com.example.colorblindtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    List<Rank> allUsers=new ArrayList<>();


    public void setAllusers(List<Rank> allUsers){
        this.allUsers=allUsers;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView=layoutInflater.inflate(R.layout.cell_card,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Rank user=allUsers.get(position);
        holder.textName.setText(user.getUsername());
        holder.textScore.setText("High Score:"+user.getHighScore());
        holder.textTime.setText("Completion Time:"+user.getHour()+":"+user.getMinute()+":"+user.getSecond());
    }


    @Override
    public int getItemCount() {
        return allUsers.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textScore,textName,textTime;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            textScore=itemView.findViewById(R.id.textScore);
            textName=itemView.findViewById(R.id.textName);
            textTime=itemView.findViewById(R.id.textTime);
        }
    }
}
