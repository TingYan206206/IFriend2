package com.example.ifriend2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyProfile extends AppCompatActivity implements View.OnClickListener{

    TextView mName, mFriendsCount;
    EditText mDescription, mEmail, mMajor, mHobby, mOrg, mlevel;
    Boolean isMyProfile, isMyFriend;
    String name;

    public void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage = data.getData();
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ImageView imageView = findViewById(R.id.ivProfile);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        setTitle("My Profile");

        initializeComponents();
        name = ParseUser.getCurrentUser().getUsername();



//        Intent intent =getIntent();
//        if(intent.hasExtra("name")) {
//            // see other's file
//            isMyProfile = false;
//            name = intent.getStringExtra("name");
//            isMyFriend = intent.getBooleanExtra("isfriendList", false);
////            isMyFriend = false;
////            setIsMyFriend(name);
//            Log.i("profile:isFriend?", isMyFriend.toString());
//            disableMyProfile();
//
//        }else{
//            // see user's own profile
//            isMyProfile = true;
//
//            enableMyProfile();
////            Toast.makeText(this, "Cannot load Profile information", Toast.LENGTH_SHORT).show();
//        }


        Log.i("My profile: ", "on create");
        retriveProfile();
    }


    private void initializeComponents(){
        mName = findViewById(R.id.name);
        mDescription = findViewById(R.id.description);
        mEmail = findViewById(R.id.email);
        mMajor = findViewById(R.id.major);
        mHobby = findViewById(R.id.hobby);
        mOrg = findViewById(R.id.org);
        mlevel = findViewById(R.id.level);
        mFriendsCount = findViewById(R.id.friendsCount);


        findViewById(R.id.save).setOnClickListener(this);
        findViewById(R.id.ivProfile).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Log.i("My profile: ", "button clicked");
        switch (view.getId()){
            case R.id.save:
                Log.i("My profile:"," save button clicked");
                saveChanges();
                break;
            case R.id.ivProfile:
                Log.i("My profile:","profile pic clicked");
                getPhoto();
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
                       ParseUser user = objects.get(0);
                       mDescription.setText(user.getString("description"));
                        mEmail.setText(user.getString("email"));
                        mMajor.setText(user.getString("major"));
                        String hobby = user.getList("hobby").toString();
                        mHobby.setText(hobby.substring(1,hobby.length() -1));
                       String org = user.getList("organization").toString();
                       mOrg.setText(org.substring(1, org.length()-1));
                       mlevel.setText(user.getString("level"));
                       mFriendsCount.setText(Integer.toString(user.getList("friends").size()));

                   }else{
                       Log.i("get user profile error:", "more than one record");
                   }
               }
            }
        });
    }

    private void saveChanges(){

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username",name);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if( e == null && objects.size() > 0){
                    if(objects.size() == 1){
                        // update changes to DB
                        ParseUser user = objects.get(0);
                        user.put("description", mDescription.getText().toString());
                        user.put("email", mEmail.getText().toString());
                        user.put("major", mMajor.getText().toString().toUpperCase());
                        user.put("level", mlevel.getText().toString().toUpperCase());
                        List hobby = new ArrayList<String>(Arrays.asList(mHobby.getText().toString().split(",")));
                        user.put("hobby", hobby);
                        List org = new ArrayList<String>(Arrays.asList(mOrg.getText().toString().toUpperCase().split(",")));
                        user.put("organization", org);
                        user.saveInBackground();
                        Log.i("update profile ", "info changed");


                    }else{
                        Log.i("get user profile error:", "more than one record");
                    }
                }
            }
        });

    }




//    private void setIsMyFriend(String name){
//        final String searchName = name;
//
//        Log.i("search friend1", name);
//        Log.i("search friend", searchName);
//        ParseQuery<ParseUser> query = ParseUser.getQuery();
//        query.whereEqualTo("username",ParseUser.getCurrentUser().getUsername());
//        query.findInBackground(new FindCallback<ParseUser>() {
//            @Override
//            public void done(List<ParseUser> objects, ParseException e) {
//                if( e == null && objects.size() > 0){
//                    if(objects.size() == 1 && objects.get(0).getList("friends").size() > 0){
//
//                        for (Object friend : objects.get(0).getList("friends")) {
//                            Log.i("friend list", friend.toString());
//                            if(friend.toString().equals(searchName)){
//                                isMyFriend = true;
//                                Log.i("inside:", "is my friend");
//                            }
//                        }
//
//                    }else{
//                        Log.i("get user profile error:", "more than one record");
//                    }
//                }
//            }
//        });
//
//    }
//
//    private void enableMyProfile(){
//        findViewById(R.id.save).setEnabled(true);
//        findViewById(R.id.cancle).setEnabled(true);
//        findViewById(R.id.save).setVisibility(View.VISIBLE);
//        findViewById(R.id.cancle).setVisibility(View.VISIBLE);
//
//        findViewById(R.id.addFriend).setEnabled(false);
//        findViewById(R.id.addFriend).setVisibility(View.INVISIBLE);
//        findViewById(R.id.sendMessage).setEnabled(false);
//        findViewById(R.id.sendMessage).setVisibility(View.INVISIBLE);
//
//    }
//
//    private void disableMyProfile(){
//        findViewById(R.id.save).setEnabled(false);
//        findViewById(R.id.cancle).setEnabled(false);
//        findViewById(R.id.save).setVisibility(View.INVISIBLE);
//        findViewById(R.id.cancle).setVisibility(View.INVISIBLE);
//
//        findViewById(R.id.addFriend).setVisibility(View.VISIBLE);
//        findViewById(R.id.sendMessage).setVisibility(View.VISIBLE);
//        findViewById(R.id.addFriend).setEnabled(true);
//        findViewById(R.id.sendMessage).setEnabled(true);
////        if(isMyFriend){
////
////        }else{
////            findViewById(R.id.addFriend).setEnabled(true);
////            findViewById(R.id.sendMessage).setEnabled(false);
////        }
//
//    }





}
