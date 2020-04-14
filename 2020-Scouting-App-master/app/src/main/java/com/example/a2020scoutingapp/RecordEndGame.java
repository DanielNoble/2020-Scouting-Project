package com.example.a2020scoutingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.Strategy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class RecordEndGame extends AppCompatActivity {
    CheckBox parked;
    CheckBox climbed;
    CheckBox balanced;
    CheckBox helped;
    CheckBox defended;
    EditText additionalComments;
    Button advertise;
    Button showQR;
    ImageView qrImage;

    NearbyCreator nc;
    Button exit;
    SharedPreferences spe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_end_game);
        spe = getSharedPreferences("Saved Data", 0);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        helped = findViewById(R.id.checkBox3);
        parked = findViewById(R.id.checkBox5);
        balanced = findViewById(R.id.checkBox2);
        climbed = findViewById(R.id.checkBox4);
        defended = findViewById(R.id.Defended);
        additionalComments = findViewById(R.id.addtionalComments);
        showQR = findViewById(R.id.QR);

        qrImage = findViewById(R.id.imageView);
        advertise = findViewById(R.id.advertise);
        exit = findViewById(R.id.Exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences spe = RecordEndGame.this.getSharedPreferences("Saved Data", MODE_PRIVATE);
                spe.edit().putString("AutoData", "").apply();
                spe.edit().putString("TeleData", "").apply();
                spe.edit().putString("EndGameData", "").apply();
                startActivity(new Intent(RecordEndGame.this, MainActivity.class));
            }
        });
        showQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringEndgame();


                QRCreator cd = new QRCreator(stringAll(), 1000, 1000);
                qrImage.setImageBitmap(cd.getBitmap());
                qrImage.setVisibility(View.VISIBLE);

            }
        });

        nc = new NearbyCreator(this, "Scouting-App2020", Strategy.P2P_STAR);
        final NearbyCreator .OptionsOfDiscovery optionsOfDiscovery = new NearbyCreator.OptionsOfDiscovery() {
            @Override
            public void OnDiscoverySuccess() {
                Toast.makeText(RecordEndGame.this, "Discovery Starting ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnDiscoveryFailure(Exception e) {
                Toast.makeText(RecordEndGame.this, "Discovery Failing please contact Davin or Pranav", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void OnStringReceived(String user,String s) {
                Toast.makeText(RecordEndGame.this, "Received Back: " + s, Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnStringUpdate() {
                Toast.makeText(RecordEndGame.this, "Update occured", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnConnectionGood(String s) {
                    Toast.makeText(RecordEndGame.this, "Connection should have worked",Toast.LENGTH_SHORT).show();
                    nc.sendMessage(s,
                            "" + spe.getString("AutoData", "") + spe.getString("TeleData", "") + spe.getString("EndGameData", ""));

            }

            @Override
            public void OnConnectionError() {
                Toast.makeText(RecordEndGame.this, "Connection error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnConnectionRejected() {
                Toast.makeText(RecordEndGame.this, "You have been rejected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnConnectionDisconnected() {
                Toast.makeText(RecordEndGame.this, "Connection disconnected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean Authenticated(@NonNull DiscoveredEndpointInfo discoveredEndpointInfo) {
                Toast.makeText(RecordEndGame.this,"Got endpoint",Toast.LENGTH_SHORT).show();
                return discoveredEndpointInfo.getServiceId().equals("Labib Master");
            }

            @Override
            public void OnConnectionSuccess() {
                Toast.makeText(RecordEndGame.this, "Connection success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnConnectionFailure(Exception e) {
                Toast.makeText(RecordEndGame.this, "Connection failed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void OnConnectionLost() {
                Toast.makeText(RecordEndGame.this, "Connection lost", Toast.LENGTH_SHORT).show();
            }
        };

        advertise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringEndgame();

                    if(advertise.getText().toString().toLowerCase().equals("advertise")){
                        Toast.makeText(RecordEndGame.this,"advertising..." ,Toast.LENGTH_SHORT).show();
                    nc.startDiscovery("Scouting-App2020", optionsOfDiscovery);
                    advertise.setText("Stop Advertising");
                    advertise.setBackgroundColor(Color.RED);}else{
                        nc.stopDiscovery();
                        Toast.makeText(RecordEndGame.this,"Discovery Stopped",Toast.LENGTH_SHORT).show();
                        advertise.setText("Advertise");
                        advertise.setBackgroundColor(getResources().getColor(R.color.primaryGreen));

                    }


            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor e=getSharedPreferences("Saved Data",MODE_PRIVATE).edit();
        e.putString("AutoData","");
        e.putString("TeleData","");
        e.putString("EndGameData","");
    }

    private void stringEndgame() {
        String delimeter = "#@;-;@#";
        additionalComments.setText(additionalComments.getText().toString()+" ");
        String endText = delimeter + parked.isChecked() + delimeter + climbed.isChecked() + delimeter + helped.isChecked() + delimeter + balanced.isChecked() + delimeter+defended.isChecked()
                + delimeter +
                additionalComments.getText().toString() + " ";
        SharedPreferences sp = RecordEndGame.this.getSharedPreferences("Saved Data", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("EndGameData", endText).apply();


    }

    public String stringAll() {

        return "" + spe.getString("AutoData", "") + spe.getString("TeleData", "") + spe.getString("EndGameData", "");
    }


}
