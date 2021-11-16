package com.example.colorblindtest;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//singleton
@Database(entities = {Rank.class},version = 1,exportSchema = false)
public abstract class RankDatabase extends RoomDatabase {
    private static RankDatabase INSTANCE;
    public abstract RankDao getRankDao();
    static synchronized RankDatabase getDatabase(Context context){
        if (INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),RankDatabase.class,"rank_database")
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }

}
