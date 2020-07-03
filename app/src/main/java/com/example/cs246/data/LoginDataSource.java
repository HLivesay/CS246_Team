package com.example.cs246.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cs246.DatabaseManager;
import com.example.cs246.data.model.LoggedInUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import static android.content.ContentValues.TAG;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        LoggedInUser myUser =
                new LoggedInUser(
                        "","Jane Doe");
        return new Result.Success<>(myUser);
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
