package com.example.colorblindtest;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;

import java.util.List;

public class RankRepository {
    private LiveData<List<Rank>>allUserLive;
    private RankDao rankDao;

    public RankRepository(Context context){
        RankDatabase rankDatabase=RankDatabase.getDatabase(context.getApplicationContext());
        rankDao=rankDatabase.getRankDao();
        allUserLive=rankDao.getAllUserLive();

    }

    public LiveData<List<Rank>> getAllUserLive(){
        return allUserLive;
    }

    public void insertUser(Rank...users){
        new InsertAsyncTask(rankDao).execute(users);
    }

    public void updateUser(Rank...users){
        new UpdateAsyncTask(rankDao).execute(users);
    }

    public void deleteUser(Rank...users){
        new DeleteAsyncTask(rankDao).execute(users);
    }

    public void deleteAllUser(Rank...users){
        new DeleteAllAsyncTask(rankDao).execute();
    }

    public int verifyDuplicate(String name){
        return rankDao.verifyDuplicate(name);
    }

    public int verifyScore(String name){return rankDao.verifyScore(name);}

    public int verifyHour(String name){return rankDao.verifyHour(name);}

    public int verifyMinute(String name){return rankDao.verifyMinute(name);}

    public int verifySecond(String name){return rankDao.verifySecond(name);}





    private static class InsertAsyncTask extends android.os.AsyncTask<Rank,Void,Void>{
        private RankDao rankDao;
        public InsertAsyncTask(RankDao rankDao){
            this.rankDao=rankDao;
        }

        @Override
        protected Void doInBackground(Rank... users) {
            rankDao.insertNewUser(users);
            return null;
        }
    }

    private static class UpdateAsyncTask extends android.os.AsyncTask<Rank,Void,Void>{
        private RankDao rankDao;
        public UpdateAsyncTask(RankDao rankDao){
            this.rankDao=rankDao;
        }

        @Override
        protected Void doInBackground(Rank... users) {
            rankDao.updateUser(users);
            return null;
        }
    }

    private static class DeleteAsyncTask extends android.os.AsyncTask<Rank,Void,Void>{
        private RankDao rankDao;
        public DeleteAsyncTask(RankDao rankDao){
            this.rankDao=rankDao;
        }

        @Override
        protected Void doInBackground(Rank... users) {
            rankDao.insertNewUser(users);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends android.os.AsyncTask<Void,Void,Void>{
        private RankDao rankDao;
        public DeleteAllAsyncTask(RankDao rankDao){
            this.rankDao=rankDao;
        }

        @Override
        protected Void doInBackground(Void...voids) {
            rankDao.deleteAllUser();
            return null;
        }
    }




}
