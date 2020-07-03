package com.example.cs246;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class DatabaseManager {
    public static DatabaseReference mDatabase;
    public static FirebaseAuth mAuth;

    private static FirebaseFirestore db;
    public static void init() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
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

    public static boolean addUser(String email, String password, final String firstname, final String lastname, final List<String> allergies) throws Exception {
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

                                        Map<String, Object> userData = new HashMap<String, Object>();
                                        userData.put("firstname", firstname);
                                        userData.put("lastname", lastname);
                                        userData.put("allergies", allergies);
                                        userData.put("uid", u.ID);
                                        addData(userData, "users");
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

        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                }
            }
        });

        return true;
    }

    public static List<Item> getItemsFromRestaurantID (String ID) throws Exception {
        if(ID=="" || ID==null) {
            throw new Exception ("ID is null or empty");
        }

        db.collection("restaurants").document(ID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                //List of items
                task.getResult().get("items");
            }
        });

        return new ArrayList<Item>();
    }

    public static void addItemToRestaurant(Item item, String restaurantID) throws Exception {
        if(restaurantID=="" || restaurantID==null)
            throw new Exception("ID is null or empty");

        db.collection("restaurants").document(restaurantID).update("items", FieldValue.arrayUnion(item));
    }

    public static void addData(Object data, String collectionName) {
        // Add a new document with a generated ID
        db.collection(collectionName)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public static void readData(String collectionName) {
        db.collection(collectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}
