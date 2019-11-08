package com.example.ifriend2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class MyProfile extends AppCompatActivity implements View.OnClickListener{

    TextView mName, mFriendsCount;
    EditText mDescription, mEmail, mMajor, mHobby;
    Boolean isMyProfile;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("My Profile");

        initializeComponents();

        Intent intent =getIntent();
        if(intent.hasExtra("name")) {
            isMyProfile = false;
            name = intent.getStringExtra("name");
            disableMyProfile();

        }else{
            isMyProfile = true;
            name = ParseUser.getCurrentUser().getUsername();
            enableMyProfile();
//            Toast.makeText(this, "Cannot load Profile information", Toast.LENGTH_SHORT).show();
        }
        retriveProfile();

        Log.i("My profile: ", "on create");
    }


    private void initializeComponents(){
        mName = findViewById(R.id.name);
        mDescription = findViewById(R.id.description);
        mEmail = findViewById(R.id.email);
        mMajor = findViewById(R.id.major);
        mHobby = findViewById(R.id.hobby);
        mFriendsCount = findViewById(R.id.friendsCount);


        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.cancle).setOnClickListener(this);
        findViewById(R.id.addFriend).setOnClickListener(this);


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
            case R.id.addFriend:
                Log.i("My profile:","add friend button clicked");
                addFriend();
                break;
            //This shouldn't happen
            default:
                break;
        }

    }

    private void retriveProfile(){
        mName.setText(name);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username",name);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
               if( e == null && objects.size() > 0){
                   if(objects.size() == 1){
                        mEmail.setText(objects.get(0).getString("email"));
                        mMajor.setText(objects.get(0).getString("major"));
                        mFriendsCount.setText(Integer.toString(objects.get(0).getList("friends").size()));

                   }else{
                       Log.i("get user profile error:", "more than one record");
                   }
               }
            }
        });

        // to do: use name to do query and populate other profile info
    }

    private void enableMyProfile(){
        findViewById(R.id.save).setEnabled(true);
        findViewById(R.id.cancle).setEnabled(true);
        findViewById(R.id.save).setVisibility(View.VISIBLE);
        findViewById(R.id.cancle).setVisibility(View.VISIBLE);

        findViewById(R.id.addFriend).setEnabled(false);
        findViewById(R.id.addFriend).setVisibility(View.INVISIBLE);

    }

    private void disableMyProfile(){
        findViewById(R.id.save).setEnabled(false);
        findViewById(R.id.cancle).setEnabled(false);
        findViewById(R.id.save).setVisibility(View.INVISIBLE);
        findViewById(R.id.cancle).setVisibility(View.INVISIBLE);

        findViewById(R.id.addFriend).setEnabled(true);
        findViewById(R.id.addFriend).setVisibility(View.VISIBLE);

    }


    private void saveChanges(){

    }

    private void addFriend(){
        List tempFriends = ParseUser.getCurrentUser().getList("friends");
        String friendName = mName.getText().toString();
        tempFriends.add(friendName);
        ParseUser.getCurrentUser().put("friends",tempFriends);
        ParseUser.getCurrentUser().saveInBackground();
        Toast.makeText(this, "add "+ friendName + " to my friend list", Toast.LENGTH_SHORT).show();
    }
}
