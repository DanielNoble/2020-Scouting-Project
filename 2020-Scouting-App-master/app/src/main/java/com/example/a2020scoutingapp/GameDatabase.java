package com.example.a2020scoutingapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GameData.class}, version = 1,exportSchema = false)
public abstract class GameDatabase extends RoomDatabase {
    public abstract GameDataDAO dao();

    private static volatile GameDatabase INSTANCE;


    static GameDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GameDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GameDatabase.class, "Game_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
