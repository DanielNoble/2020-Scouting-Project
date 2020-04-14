package com.example.a2020scoutingapp;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface GameDataDAO {
    //get all data
    @Query("Select * FROM RawData")
   List<GameData> allData();

    //get all data for a specific team
    @Query("Select  *  FROM RawData WHere TeamNumber=:number")
    List<GameData> allTeamData(int number);

    //get all teams
    @Query("Select Distinct TeamNumber  FROM RawData")
    List<String> allTeams();

//    //get total auto points teams
//    @Query("Select Distinct TeamNumber  FROM RawData Order By `Total Auto points`")
//    List<String> TeamsInAsscendingAutoPointsOrder();
//    //get total teleop points teams
//    @Query("Select Distinct TeamNumber  FROM RawData Order By `Total Teleop points` ")
//    List<String> TeamsInAsscendingTelePointsOrder();
    @Insert
    void insert(GameData...gameData);
    @Delete
    void delete(GameData...gameData);



}
