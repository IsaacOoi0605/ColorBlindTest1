package com.example.colorblindtest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.ArrayList;
import java.util.List;


public class MyViewModel extends AndroidViewModel {
    SavedStateHandle handle;
    private static String KEY_HIGH_SCORE="key_high_score";
    private static String KEY_INDEX="key_index";
    private static String KEY_ANSWER="key_answer";
    private static String SAVE_SHP_DATA_NAME="save_ship_data_name";
    private static String KEY_CURRENT_SCORE="key_current_score";
    public static ArrayList<Question> FALSE_ANSWER=new ArrayList<Question>();
    private static String KEY_USERNAME="key_username";
    private static String KEY_QUESTION="key_question";
    public static boolean IS_LOGGEDIN=false;
    public static boolean IS_STARTED=false;
    private static String KEY_UP_TIME="key_time";
    private RankDao rankDao;
    private RankRepository rankRepository;


    public MyViewModel(@NonNull Application application, SavedStateHandle handle){
        super(application);
        if(!handle.contains(KEY_HIGH_SCORE)){
            SharedPreferences shp=getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE,shp.getInt(KEY_HIGH_SCORE,0));
            handle.set(KEY_USERNAME,shp.getString(KEY_USERNAME,null));
            handle.set(KEY_INDEX,0);
            handle.set(KEY_ANSWER,null);
            handle.set(KEY_CURRENT_SCORE,0);
            handle.set(KEY_QUESTION,0);
            handle.set(KEY_UP_TIME,null);
        }
        this.handle=handle;
        rankRepository=new RankRepository(application);
    }
    public LiveData<List<Rank>> getAllUsersLive(){
        return rankRepository.getAllUserLive();
    }
    public void insertUsers(Rank...users){
        rankRepository.insertUser(users);
    }
    public void updateUsers(Rank...users){
        rankRepository.updateUser(users);
    }
    public void deleteUsers(Rank...users){
        rankRepository.deleteUser(users);
    }
    public void deleteAllUsers(){rankRepository.deleteAllUser();}
    public int verifyUsers(String name){
        return rankRepository.verifyDuplicate(name);
    }
    public int verifyScore(String name){return rankRepository.verifyScore(name);}
    public int verifyHour(String name){return rankRepository.verifyHour(name);}
    public int verifyMinute(String name){return rankRepository.verifyMinute(name);}
    public int verifySecond(String name){return rankRepository.verifySecond(name);}

    public MutableLiveData<String>getAnswer(){return handle.getLiveData(KEY_ANSWER);}
    public MutableLiveData<Integer>getQuestionNum(){return handle.getLiveData(KEY_QUESTION);}
    public MutableLiveData<Integer>getIndex(){
        return handle.getLiveData(KEY_INDEX);
    }
    public MutableLiveData<Integer>getHighScore(){
        return handle.getLiveData(KEY_HIGH_SCORE);
    }
    public MutableLiveData<Integer>getCurrentScore(){ return handle.getLiveData(KEY_CURRENT_SCORE); }
    public MutableLiveData<String>getUptime(){return handle.getLiveData(KEY_UP_TIME);}
    public int getFalseAnswer(int position){ return this.FALSE_ANSWER.get(position).getUserInput(); }
    public MutableLiveData<String> getKeyUsername(){
        return handle.getLiveData(KEY_USERNAME);
    }
    public ArrayList<Question> getFalseAnswer(){
        return this.FALSE_ANSWER;
    }

    void setLoggedIn(){
        this.IS_LOGGEDIN=true;
    }
    void setLoggedOut(){this.IS_LOGGEDIN=false;}
    void saveKeyUsername(){
        SharedPreferences shp=getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        editor.putString(KEY_USERNAME,getKeyUsername().getValue());
        editor.apply();
    }
    void save(){
        SharedPreferences shp=getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        editor.putInt(KEY_HIGH_SCORE,getHighScore().getValue());
        editor.apply();
    }


    void answerCorrect(){
        getCurrentScore().setValue(getCurrentScore().getValue()+1);
        getIndex().setValue(getIndex().getValue()+1);
        getQuestionNum().setValue(getQuestionNum().getValue()+1);

    }

    void answerWrong(){
        getCurrentScore().setValue(getCurrentScore().getValue());
        getIndex().setValue(getIndex().getValue()+1);
        getQuestionNum().setValue(getQuestionNum().getValue()+1);

    }


    void insertFalseAnswer(Question question){
        FALSE_ANSWER.add(question);
    }


}
