package com.example.cs246;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DatabaseManager {
    private static DatabaseReference mDatabase;
    private static FirebaseAuth mAuth;
    public static void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    //Will return null if the user isn't logged in
    public static FirebaseUser getCurrentUser() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user;
    }

    public static boolean addUser(String email, String password, String firstname, String lastname, List<String> allergies, OnCompleteListener<AuthResult> finishFunction) throws Exception {
       if(email=="" || email==null)
           throw new Exception("Username is null or empty");

       if(password=="" || password==null)
           throw new Exception("Password is null or empty");

        final User u = new User(firstname, lastname, allergies);
        Task v = mAuth.createUserWithEmailAndPassword(email, password);
        v.addOnCompleteListener(finishFunction);
        v.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    //Get the unique token for this user and store their data in the users database
                    user.getIdToken(true).addOnCompleteListener(
                            new OnCompleteListener<GetTokenResult>() {
                                @Override
                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                    if(task.isSuccessful()){
                                        mDatabase.child("users").child(task.getResult().getToken()).setValue(u);
                                    }
                                    else {
                                        Log.w(TAG, "getIdToken:failure", task.getException());
                                    }

                                }
                            }
                    );
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                }
            }
        });
        return true;
    }

    public static boolean addUser(String email, String password, String firstname, String lastname, List<String> allergies) throws Exception {
        if(email=="" || email==null)
            throw new Exception("Username is null or empty");

        if(password=="" || password==null)
            throw new Exception("Password is null or empty");

        final User u = new User(firstname, lastname, allergies);
        Task v = mAuth.createUserWithEmailAndPassword(email, password);
        v.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    //Get the unique token for this user and store their data in the users database
                    user.getIdToken(true).addOnCompleteListener(
                            new OnCompleteListener<GetTokenResult>() {
                                @Override
                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                    if(task.isSuccessful()){
                                        u.ID = task.getResult().getToken();
                                        String id = mDatabase.child("users").push().getKey();
                                        mDatabase.child("users").setValue(u);
                                        PreferencesManager.storeString("uid", u.ID);
                                    }
                                    else {
                                        Log.w(TAG, "getIdToken:failure", task.getException());
                                    }

                                }
                            }
                    );
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                }
            }
        });
        return true;
    }

    public static boolean verifyUser(String username, String password) throws Exception {
        if(username=="" || username==null)
            throw new Exception("Username is null or empty");

        if(password=="" || password==null)
            throw new Exception("Password is null or empty");

        //TODO verify user

        return true;
    }

    public static List<Item> getItemsFromRestaurantID (String ID) throws Exception {
        if(ID=="" || ID==null) {
            throw new Exception ("ID is null or empty");
        }

        //TODO get data from database

        return new ArrayList<Item>();
    }

    public static void addItemToRestaurant(Item item, String restaurantID) throws Exception {
        if(restaurantID=="" || restaurantID==null)
            throw new Exception("ID is null or empty");

        //TODO add item to database
    }

    public static void AddData(Object data, String path) {
        String ID = mDatabase.child(path).push().getKey();
        mDatabase.child(path).child(ID).setValue(data);
    }

}
