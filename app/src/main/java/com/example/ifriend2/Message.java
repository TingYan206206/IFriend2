package com.example.ifriend2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTitle("Your Message");

        final ListView listView = findViewById(R.id.listView);

        final ArrayList<String> friendList = new ArrayList<>();

        final List<Map<String, String>> messageData = new ArrayList<>();

        for( int i = 0; i < 5 ; i++){
            Map<String, String> messageInfo = new HashMap<>();
            messageInfo.put("content", "Message " + Integer.toString(i));
            messageInfo.put("user_time", "user " + Integer.toString(i) + " at time " + Integer.toString(i));
            messageData.add(messageInfo);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, messageData, android.R.layout.simple_list_item_2, new String[]{"content", "user_time"}, new int[]{android.R.id.text1, android.R.id.text2});

        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // to do go to Profile page to load friend info
                Intent intent = new Intent(getApplicationContext(), MyProfile.class);
                intent.putExtra("name", friendList.get(i));
                startActivity(intent);
            }
        });
    }
}
