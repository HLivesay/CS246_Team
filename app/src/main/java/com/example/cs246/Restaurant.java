package com.example.cs246;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cs246.ui.login.LoginActivity;
import com.example.cs246.ui.restaurant.RestaurantAddItems;
import com.example.cs246.ui.restaurant.RestaurantFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class Restaurant extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RestaurantFragment.newInstance())
                    .commitNow();
        }

        FloatingActionButton fab = findViewById(R.id.addItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "TODO: open up the Restaurant add item activity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Intent intent = new Intent(context, RestaurantAddItems.class);
//                startActivity(intent);
            }
        });
    }
}
