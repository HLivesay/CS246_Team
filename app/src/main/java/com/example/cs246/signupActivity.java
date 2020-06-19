package com.example.cs246;


import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

public class signupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Activity", "Calling signup activity class" );

        super.onCreate(savedInstanceState);
        Log.i("Activity", "Calling signup activity class 2" );

        setContentView(R.layout.activity_signup);
    }
}
