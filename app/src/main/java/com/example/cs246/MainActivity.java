package com.example.cs246;

import android.content.Intent;
import android.os.Bundle;

import com.example.cs246.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //Initialize Database and Preferences
        DatabaseManager.init();
        PreferencesManager.init(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * Open the Login activity,
     * Note: I am using this to see the design of the login activity
     * @author Nathan
     */
    public void loginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        Log.i("Activity", "Creating Login activity" );

        startActivity(intent);
    }
    /**
     * Open the signup activity,
     * Note: I am using this to see the design of the sign up activity
     * @author Nathan
     */
    public void signupActivity(View view) {
        Intent intent = new Intent(this, signupActivity.class);
        Log.i("Activity", "Creating signup activity" );
        startActivity(intent);
    }
    /**
     * Open the signup activity,
     * Note: I am using this to see the design of the sign up activity
     * @author Nathan
     */
    public void restaurantActivity(View view) {
        Intent intent = new Intent(this, Restaurant.class);
        startActivity(intent);
    }
}
