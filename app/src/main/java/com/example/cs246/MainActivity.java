package com.example.cs246;

import android.content.Intent;
import android.os.Bundle;

import com.example.cs246.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialize Database and Preferences
        DatabaseManager.init();
        PreferencesManager.init(this);

        //Check if the user is logged in
        final Intent intent = new Intent(this, LoginActivity.class); //If we have to load the login activity we pre initialize it
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null) {
                    //Continue with this activity
                } else {
                    //Load the login activity
                    Log.i("Activity", "Creating Login activity" );
                    startActivity(intent);
                }
            }
        };

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);




        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                try {
                    Map<String, String> myData = new HashMap<String, String>();
                    myData.put("MyVar", "test");

                    DatabaseManager.addData(myData, "allergens");
                    DatabaseManager.readData("users");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });*/


    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseManager.mAuth.addAuthStateListener(authStateListener);
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
