package com.example.ifriend2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyProfile extends AppCompatActivity implements View.OnClickListener{

    TextView mName;
    EditText mEmail, mMajor,mHobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("My Profile");

        initializeComponents();

        Intent intent =getIntent();
        if(intent.hasExtra("name")) {
            String name = intent.getStringExtra("name");
            retriveProfile(name);


        }else{
            Toast.makeText(this, "Cannot load Profile information", Toast.LENGTH_SHORT).show();
        }

        Log.i("My profile: ", "on create");
    }


    private void initializeComponents(){
        mName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mMajor = findViewById(R.id.major);
        mHobby = findViewById(R.id.hobby);

        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.cancle).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Log.i("My profile: ", "button clicked");
        switch (view.getId()){
            case R.id.save:
                //If new Note, call createNewNote()
                Log.i("My profile:"," save button clicked");
                break;
            //If delete note, call deleteNewestNote()
            case R.id.cancle:
                Log.i("My profile:","cancel button clicked");
                break;
            //This shouldn't happen
            default:
                break;
        }

    }

    private void retriveProfile(String name){



        mName.setText(name);

        // to do: use name to do query and populate other profile info
    }


}
