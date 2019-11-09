package com.example.ifriend2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class MyProfile extends AppCompatActivity implements View.OnClickListener{

    TextView mName, mFriendsCount;
    EditText mDescription, mEmail, mMajor, mHobby;
    Boolean isMyProfile, isMyFriend;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("My Profile");

        initializeComponents();

        Intent intent =getIntent();
        if(intent.hasExtra("name")) {
            // see other's file
            isMyProfile = false;
            name = intent.getStringExtra("name");
            isMyFriend = intent.getBooleanExtra("isfriendList", false);
//            isMyFriend = false;
//            setIsMyFriend(name);
            Log.i("profile:isFriend?", isMyFriend.toString());
            disableMyProfile();

        }else{
            // see user's own profile
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
        findViewById(R.id.sendMessage).setOnClickListener(this);

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
            case R.id.sendMessage:
                Log.i("My profile:","remove friend button clicked");
                sendMessage();
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
    }

    private void setIsMyFriend(String name){
        final String searchName = name;

        Log.i("search friend1", name);
        Log.i("search friend", searchName);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if( e == null && objects.size() > 0){
                    if(objects.size() == 1 && objects.get(0).getList("friends").size() > 0){

                        for (Object friend : objects.get(0).getList("friends")) {
                            Log.i("friend list", friend.toString());
                            if(friend.toString().equals(searchName)){
                                isMyFriend = true;
                                Log.i("inside:", "is my friend");
                            }
                        }

                    }else{
                        Log.i("get user profile error:", "more than one record");
                    }
                }
            }
        });

    }

    private void enableMyProfile(){
        findViewById(R.id.save).setEnabled(true);
        findViewById(R.id.cancle).setEnabled(true);
        findViewById(R.id.save).setVisibility(View.VISIBLE);
        findViewById(R.id.cancle).setVisibility(View.VISIBLE);

        findViewById(R.id.addFriend).setEnabled(false);
        findViewById(R.id.addFriend).setVisibility(View.INVISIBLE);
        findViewById(R.id.sendMessage).setEnabled(false);
        findViewById(R.id.sendMessage).setVisibility(View.INVISIBLE);

    }

    private void disableMyProfile(){
        findViewById(R.id.save).setEnabled(false);
        findViewById(R.id.cancle).setEnabled(false);
        findViewById(R.id.save).setVisibility(View.INVISIBLE);
        findViewById(R.id.cancle).setVisibility(View.INVISIBLE);

        findViewById(R.id.addFriend).setVisibility(View.VISIBLE);
        findViewById(R.id.sendMessage).setVisibility(View.VISIBLE);
        findViewById(R.id.addFriend).setEnabled(true);
        findViewById(R.id.sendMessage).setEnabled(true);
//        if(isMyFriend){
//
//        }else{
//            findViewById(R.id.addFriend).setEnabled(true);
//            findViewById(R.id.sendMessage).setEnabled(false);
//        }

    }


    private void saveChanges(){

    }

    private void addFriend(){

        List tempFriends = ParseUser.getCurrentUser().getList("friends");
        String friendName = mName.getText().toString();
        if(!tempFriends.contains(friendName)){
            tempFriends.add(friendName);
            ParseUser.getCurrentUser().put("friends",tempFriends);
            ParseUser.getCurrentUser().saveInBackground();
            Toast.makeText(this, "add "+ friendName + " to my friend list", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, friendName + " is already added on my friend list", Toast.LENGTH_SHORT).show();

        }
    }

    private void sendMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send a Friend Request");
        final EditText requestEditText = new EditText(this);
        builder.setView(requestEditText);
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ParseObject message = new ParseObject("Message");
                message.put("from", ParseUser.getCurrentUser().getUsername());
                message.put("to", mName.getText().toString());
                message.put("message", requestEditText.getText().toString());

                message.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Toast.makeText(MyProfile.this, "Friend request sent", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MyProfile.this, "Friend request failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void removeFriend(){

        List tempFriends = ParseUser.getCurrentUser().getList("friends");
        String friendName = mName.getText().toString();
        tempFriends.remove(friendName);
        ParseUser.getCurrentUser().put("friends",tempFriends);
        ParseUser.getCurrentUser().saveInBackground();
        Toast.makeText(this, "remove "+ friendName + " to my friend list", Toast.LENGTH_SHORT).show();
    }
}
