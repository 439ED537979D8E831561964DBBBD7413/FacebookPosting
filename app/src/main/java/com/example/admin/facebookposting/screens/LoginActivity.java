package com.example.admin.facebookposting.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.facebookposting.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.setApplicationId("155395255054237");
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton)findViewById(R.id.loginButton);
        List< String > permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile", "AccessToken","publish_actions");

        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("user_photos");
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("user_birthday");
        //loginButton.setReadPermissions("AccessToken");
        //loginButton.setReadPermissions("publish_actions");*/

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LoginActivity.this, "successfull", Toast.LENGTH_SHORT).show();
                Log.e("msg","successfully");
                GraphRequestAsyncTask request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("obj",object.toString());
                                Intent intent = new Intent(LoginActivity.this, ShowButtonsActivity.class);
                                startActivity(intent);
                            }
                        }).executeAsync();

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "cancle", Toast.LENGTH_SHORT).show();
                Log.e("msg","cancle");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                Log.e("msg",error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

