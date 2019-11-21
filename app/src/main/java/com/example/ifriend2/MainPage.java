package com.example.ifriend2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.parse.ParseUser;

public class MainPage extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainPage";
    VideoView videoView;
    @Override
    public void onClick(View view) {
        Log.i("main page", "button clicked" );
        switch (view.getId()){
            case R.id.myFriendsBtn:
                //If new Note, call createNewNote()
                Log.i(TAG," mu friends button clicked");
                Intent intent = new Intent(getApplicationContext(), MyFriends.class);
                intent.putExtra("isMyFriend", "true");
                startActivity(intent);
                break;
            case R.id.recommandBtn:
                //If new Note, call createNewNote()
                Log.i(TAG," mu friends button clicked");
                intent = new Intent(getApplicationContext(), MyFriends.class);
                startActivity(intent);
                break;
            case R.id.searchBtn:
                Log.i(TAG,"search button clicked");
                intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
                break;
            case R.id.messageBtn:
                Log.i(TAG,"message button clicked");
                Intent messageIntent = new Intent(getApplicationContext(), Message.class);
                startActivity(messageIntent);
                break;
            //This shouldn't happen
            default:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.setting_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout){
            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }else if(item.getItemId() == R.id.myProfile){
            Intent intent = new Intent(getApplicationContext(), MyProfile.class);
//            intent.putExtra("name", ParseUser.getCurrentUser().getUsername());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        videoView= (VideoView) findViewById(R.id.videoView);
        videoView.setVideoPath(("android.resource://" + getPackageName() + "/" + R.raw.school_viedo2));
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();

        //setTitle("iFriend");
        findViewById(R.id.searchBtn).setOnClickListener(this);
        findViewById(R.id.myFriendsBtn).setOnClickListener(this);
        findViewById(R.id.messageBtn).setOnClickListener(this);
        findViewById(R.id.recommandBtn).setOnClickListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("main page:", "restart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        videoView.start();
        Log.i("main page:", "resume");
    }
}
