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

public class FriendProfile extends AppCompatActivity implements View.OnClickListener{

    TextView mName, mFriendsCount;
    TextView mDescription, mEmail, mMajor, mHobby;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_friend_profile);
        setTitle("Friend Profile");

        initializeComponents();
        Intent intent = getIntent();
        if (intent.hasExtra("name")) {
            // see other's file
            name = intent.getStringExtra("name");
        }
        retriveProfile();
    }


    private void initializeComponents(){
        mName = findViewById(R.id.name);
        mDescription = findViewById(R.id.description);
        mEmail = findViewById(R.id.email);
        mMajor = findViewById(R.id.major);
        mHobby = findViewById(R.id.hobby);
        mFriendsCount = findViewById(R.id.friendsCount);


        findViewById(R.id.addFriend).setOnClickListener(this);
        findViewById(R.id.sendMessage).setOnClickListener(this);
        findViewById(R.id.removeFriend).setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        Log.i("My profile: ", "button clicked");
        switch (view.getId()){
            case R.id.addFriend:
                Log.i("My profile:","add friend button clicked");
                addFriend();
                break;
            case R.id.sendMessage:
                Log.i("My profile:","remove friend button clicked");
                sendMessage();
                break;
            case R.id.removeFriend:
                Log.i("My profile:","remove friend button clicked");
                removeFriend();
                break;
            //This shouldn't happen
            default:
                break;
        }

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
                            Toast.makeText(FriendProfile.this, "Friend request sent", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(FriendProfile.this, "Friend request failed", Toast.LENGTH_SHORT).show();
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
