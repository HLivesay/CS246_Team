package com.example.cs246;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cs246.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class RestaurantDisplay extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
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
        setContentView(R.layout.activity_restaurant_display);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.restaurant, R.id.login, R.id.settingsActivity)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Populate list view
        try {
            final List<String> myPlaces = PlacesManager.getRestaurantIDinRange(15); //This is currently not fully functional
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myPlaces);
            ListView lv = (ListView)findViewById(R.id.restaurantDisplayList);
            lv.setAdapter(arrayAdapter);
            final Activity selfReference = this;
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                // argument position gives the index of item which is clicked
                public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
                {
                    String selectedRestaurant=myPlaces.get(position);
                    Toast.makeText(getApplicationContext(), "Restaurant Selected : "+selectedRestaurant,   Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(selfReference, Restaurant.class);
                    intent.putExtra("rID", selectedRestaurant);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseManager.mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.restaurant_display, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}