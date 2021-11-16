package com.example.colorblindtest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RankDao {
    @Insert
    void insertNewUser(Rank... ranks);

    @Update
    void updateUser(Rank... ranks);

    @Delete
    void deleteUser(Rank... ranks);

    @Query("DELETE FROM RANK")
    void deleteAllUser();

    @Query("SELECT * FROM RANK ORDER BY High_Score DESC")
    LiveData<List<Rank>>getAllUserLive();

    @Query("SELECT COUNT (username) FROM RANK WHERE username LIKE :name")
    int verifyDuplicate(String name);

    @Query("SELECT High_Score FROM RANK WHERE USERNAME LIKE :name")
    int verifyScore(String name);

    @Query("SELECT Hour FROM RANK WHERE USERNAME LIKE :name")
    int verifyHour(String name);

    @Query("SELECT Minute FROM RANK WHERE USERNAME LIKE :name")
    int verifyMinute(String name);

    @Query("SELECT Second FROM RANK WHERE USERNAME LIKE :name")
    int verifySecond(String name);


}
