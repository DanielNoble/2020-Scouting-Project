package com.example.a2020scoutingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DataView extends AppCompatActivity {
    GameRepository gameRepository;
    RecyclerView rv;
    DataAdapter da;
    RecyclerView.LayoutManager lm;
    ArrayList<GameData> allData;
    private static final int RESULT_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gameRepository=new GameRepository(this.getApplication());
        rv=findViewById(R.id.dataView);
        allData= (ArrayList<GameData>) gameRepository.getAllGames();
        da= new DataAdapter(allData);

        lm=new LinearLayoutManager(this);
        rv.setAdapter(da);

        rv.setLayoutManager(lm);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Downloading a csv file choose location", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent= new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/csv");
                intent.putExtra(Intent.EXTRA_TITLE,"scouting_data.csv");
                startActivityForResult(intent,RESULT_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_CODE){
            Uri uri = null;
            if (data != null) {

                uri = data.getData();

                try {
                    ParcelFileDescriptor pfd = this.getContentResolver().
                            openFileDescriptor(uri, "w");
                    FileOutputStream fileOutputStream =
                            new FileOutputStream(pfd.getFileDescriptor());

                    fileOutputStream.write(("matchNumber,teamNumber,startPosition,preLoadedBalls,auto-bottom port," +
                            "auto-Outer port,crossedLine,cellsCollected,Bottom Port,Top Port,missed," +
                                    "rotation,colorControl,balanced,parked,climbed,helped," +
                                    "defended,additionalComments").getBytes());
                    for(GameData gd:allData){
                        fileOutputStream.write(("\n"+gd.matchNumber+","+gd.teamNumber+","+gd.startSide+","+gd.preloadedBalls+","+gd.cellsShotRectAuto+","+
                                gd.cellsShotHexagonAuto+","+gd.crossedLine+","+
                                gd.cellsCollected+","+gd.cellsShotRect+","+gd.cellsShotHexagon+","+gd.cellsMissedTele+","+
                                gd.rotationControl+","+gd.colorControl+","+gd.isBalance()+","+gd.isParked+","+gd.isClimbed+","
                                +gd.isHelperClimbed+","+gd.isDefended+","+gd.additionalComments).getBytes());
                    }
                    // closing stream
                    fileOutputStream.close();
                    pfd.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
    }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.data_view_menu, menu);
//
//
//        return true;
//    }
////
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.filterButton:
//                // User chose the "create" item, show the app settings UI...
//                Log.d("Testing", "Selected Create");
//                FilterDialogBox dialogBox = new FilterDialogBox();
//                dialogBox.show(getSupportFragmentManager(), "new Website");
//
//
//                return true;
//            default:
//                // If we got here, the user's action was not recognized.
//                // Invoke the superclass to handle it.
//                return super.onOptionsItemSelected(item);
//
//
//        }
//
//
//    }
}
