package com.example.ifriend2;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button signUpBtn, loginBtn;

    public void enterMainPage(){
        Intent intent = new Intent(getApplicationContext(), MainPage.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Log.i("button clicked: ", " in main activity");
        EditText username = (EditText)findViewById(R.id.usernameEditText);
        EditText password = (EditText) findViewById(R.id.passwordEditText);

        if(username.getText().toString().matches("") || password.getText().toString().matches("")){
            Toast.makeText(this, "Both username and password are required", Toast.LENGTH_SHORT).show();
        }else{
            if(view.getId() == R.id.signUpButton){
                Log.i("button clicked: ", "signup");
//        // create a new user
                ParseUser user = new ParseUser();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                // check if sign up successed
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Log.i("sign up ", "Successed");
                            Toast.makeText(MainActivity.this, "You have signed up! please log in again", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }else{
                Log.i("button clicked: ", "login");
                // to do : check if the user/password matches DB
                // log in user
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user != null){
                            Log.i("success ", "loged in ");
                            enterMainPage();
                        }else {
                            Log.i("failed ", "loged in ");
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "wrong user or/and password, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("activity main ", "oncreate");
        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.signUpButton).setOnClickListener(this);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
