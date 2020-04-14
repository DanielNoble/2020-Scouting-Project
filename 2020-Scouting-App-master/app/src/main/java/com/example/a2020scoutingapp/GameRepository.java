package com.example.a2020scoutingapp;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class GameRepository {
    public GameDataDAO dao;
    private List<GameData> allData;

    GameRepository(Application application) {
        GameDatabase db = GameDatabase.getDatabase(application);
        dao = db.dao();
        try {
            allData = new getAllTeamDataAsyncTask(dao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    List<GameData> getAllGames() {
        return allData;
    }

    public void insert(GameData... game) {
        new insertAsyncTask(dao).execute(game);
    }

    public void delete(GameData... game) {
        new deleteAsyncTask(dao).execute(game);
    }

    public List<String> allTeams() {
        try {
            return new getAllTeamsAsyncTask(
                    dao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        return null;
    }
    public List<GameData> getTeamData(int teamNumber) {
        try {
            return new getSpecificTeamDataAsyncTask(
                    dao).execute(teamNumber).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        return null;
    }


    private static class insertAsyncTask extends AsyncTask<GameData, Void, Void> {

        private GameDataDAO mAsyncTaskDao;

        insertAsyncTask(GameDataDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final GameData... params) {
            mAsyncTaskDao.insert(params);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<GameData, Void, Void> {

        private GameDataDAO mAsyncTaskDao;

        deleteAsyncTask(GameDataDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final GameData... params) {
            mAsyncTaskDao.delete(params);
            return null;
        }
    }

    private static class getAllTeamsAsyncTask extends AsyncTask<Void, Void, List<String>> {

        private GameDataDAO mAsyncTaskDao;

        getAllTeamsAsyncTask(GameDataDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {

            return mAsyncTaskDao.allTeams();
        }
    }

    private static class getSpecificTeamDataAsyncTask extends AsyncTask<Integer, Void, List<GameData>> {

        private GameDataDAO mAsyncTaskDao;

        getSpecificTeamDataAsyncTask(GameDataDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<GameData> doInBackground(Integer... ints) {

            return mAsyncTaskDao.allTeamData(ints[0]);
        }
    }
    private static class getAllTeamDataAsyncTask extends AsyncTask<Void, Void, List<GameData>> {

        private GameDataDAO mAsyncTaskDao;

        getAllTeamDataAsyncTask(GameDataDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<GameData> doInBackground(Void... voids) {

            return mAsyncTaskDao.allData();
        }
    }

}
