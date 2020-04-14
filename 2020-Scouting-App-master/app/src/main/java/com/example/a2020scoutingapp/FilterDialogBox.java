package com.example.a2020scoutingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FilterDialogBox extends DialogFragment {

    Button addFilter;
    EditText ed;
    ArrayList<String> filters;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.filter_dialog_box, null);
//        addFilter=view.findViewById(R.id.filterButton);
        ed=view.findViewById(R.id.filterEditText);
        filters= new ArrayList<>();
        final TextView viewFilters =view.findViewById(R.id.ViewFilters);

        String bob="";
        for(String s:filters){
            bob+=" "+s;
        }
        viewFilters.setText("Current Filters are:"+ bob);
        addFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bob="";
                if(!filters.contains(ed.getText().toString())){
                filters.add(ed.getText().toString());


                    for(String s:filters){
                        bob+=" "+s;
                    }
                    viewFilters.setText("Current Filters are:"+bob);
            }else{
                filters.remove(ed.getText().toString());
                    filters.add(ed.getText().toString());


                    for(String s:filters){
                        bob+=" "+s;
                    }
                    viewFilters.setText("Current Filters are:"+bob);
                }
        }});

        builder.setView(view).setPositiveButton("Create", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // insert any filtering of the data here




            }

        });
        builder.setNegativeButton("Cancel", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Test", "Dialog box canceled");
            }
        });


        return builder.create();


    }



}
