package com.example.ifriend2;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MyFriends extends AppCompatActivity {
    ArrayList<String> myFriends = new ArrayList<>();
    ArrayAdapter myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        setTitle("My Friends List");

        ListView myFriendsListView = findViewById(R.id.listView);

        myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myFriends);
        myFriendsListView.setAdapter(myAdapter);

        retriveFriends();






    }


    private void retriveFriends(){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseUser user : objects) {
                        myFriends.add(user.getUsername());
                    }

                    myAdapter.notifyDataSetChanged();

                }
            }
        });

        myFriends.add("a");
        myFriends.add("b");
        myFriends.add("c");

        myAdapter.notifyDataSetChanged();
    }
}
