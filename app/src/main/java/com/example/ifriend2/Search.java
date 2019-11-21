package com.example.ifriend2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner dropdown;
    private ChipGroup chipGroup;
    private static final String[] levels = new String[]{"Undergrad", "Master", "Doctor"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //set the spinners adapter to the previously created one.
        dropdown = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, levels);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        // set chipGroup for keyword search
        chipGroup = (ChipGroup)findViewById(R.id.keyword);
        final ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < chipGroup.getChildCount(); i++){
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        list.add(buttonView.getText().toString());
                    }else{
                        list.remove(buttonView.getText().toString());
                    }
                    if(!list.isEmpty()){
                        Toast.makeText(Search.this, list.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                Log.i("position:", position + " value: " + levels[position]);
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                Log.i("position:", position + " value: 2");

                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                Log.i("position:", position + " value: three");

                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

}
