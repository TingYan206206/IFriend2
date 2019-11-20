package com.example.ifriend2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MyFriends extends AppCompatActivity {
    ArrayList<String> friendList = new ArrayList<>();
    ArrayAdapter myAdapter;
    
    boolean isfriendList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        Intent intent = getIntent();
        if(intent.hasExtra("isMyFriend")){
            setTitle("My Friends List");
            isfriendList = true;
        }else{
            setTitle("Recommend Friends");
            isfriendList = false;
        }

        ListView friendsListView = findViewById(R.id.listView);

        myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, friendList);
        friendsListView.setAdapter(myAdapter);
        friendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // to do go to Profile page to load friend info
                Intent intent = new Intent(getApplicationContext(), FriendProfile.class);
                intent.putExtra("name", friendList.get(i));
                startActivity(intent);
            }
        });

        retriveFriends();
    }


    private void retriveFriends(){
        // if is retriveing my friendList
        if(isfriendList){
            Log.i("mY friends:", "my friends");
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if( e == null && objects.size() > 0){

                        if(objects.size() == 1 && objects.get(0).getList("friends").size() > 0) {
                            for (Object friend : objects.get(0).getList("friends")) {
                                friendList.add(friend.toString());
                            }
                        }

                        myAdapter.notifyDataSetChanged();

                    }
                }
            });
        }
        // send recommend Friend
        else{

            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
            Log.i("mY friends:", "recommand friends");

            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null && objects.size() > 0) {
                        for (ParseUser user : objects) {
                            friendList.add(user.getUsername());
                        }
                    }
                    myAdapter.notifyDataSetChanged();
                }
            });
        }

    }
}
