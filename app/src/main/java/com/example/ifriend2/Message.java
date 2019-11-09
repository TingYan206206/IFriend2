package com.example.ifriend2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setTitle("Your Message");
        Log.i("Message", "on create");

        final ListView listView = findViewById(R.id.listView);

        final ArrayList<String> friendList = new ArrayList<>();

        final List<Map<String, String>> messageData = new ArrayList<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
        query.whereEqualTo("to",ParseUser.getCurrentUser().getUsername());
        query.orderByDescending("createdAt");
        query.setLimit(20);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    Log.i("Message", "query success");

                    for (ParseObject message : objects){
                        Map<String, String> messageInfo = new HashMap<>();
                        Date date = message.getCreatedAt();
                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        String messageDate = df.format(date);
                        messageInfo.put("content", message.getString("message"));
                        messageInfo.put("user_time", message.getString("from") + "  " + messageDate);
                        friendList.add(message.getString("from"));
                        messageData.add(messageInfo);
                    }
                    SimpleAdapter simpleAdapter = new SimpleAdapter(Message.this, messageData, android.R.layout.simple_list_item_2, new String[]{"content", "user_time"}, new int[]{android.R.id.text1, android.R.id.text2});
                    listView.setAdapter(simpleAdapter);

                }
            }
        });

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
