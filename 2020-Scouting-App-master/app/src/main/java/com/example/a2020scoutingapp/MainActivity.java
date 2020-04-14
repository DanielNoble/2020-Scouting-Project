package com.example.a2020scoutingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText userName;
    EditText passWord;
    Button next;
    Button guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //find IDs
        next = findViewById(R.id.useFullButton);
        passWord = findViewById(R.id.password);
        userName = findViewById(R.id.username);
        guest= findViewById(R.id.guestButton);
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,(RecordAuto.class)));
            }
        });
        SharedPreferences.Editor e=getSharedPreferences("Saved Data",MODE_PRIVATE).edit();
        e.putString("AutoData","");
        e.putString("TeleData","");
        e.putString("EndGameData","");
        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            //    startActivity(new Intent(MainActivity.this,(RecordAuto.class)));

                validate(userName.getText().toString(), passWord.getText().toString());
            }
        });

    }
    private void validate(String userName, String passWord){
        if (userName.equals("2976") && passWord.equals("dpworlds")){
            startActivity(new Intent(MainActivity.this,(AdvertiseMain.class)));
        }
    }
}
