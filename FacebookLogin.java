package com.example.driveusers.Fragments;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.driveusers.HomeActivity;
import com.example.driveusers.ModelClass.RegisterPojo;
import com.example.driveusers.Utils.App;
import com.example.driveusers.Utils.Constants;
import com.example.driveusers.Utils.MyMVVM;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.omninos.util_data.CommonUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;

public class FacebookLogin {
    public static CallbackManager callbackManager;
    private FragmentActivity activity;
    String fbId = "", fbFirstName = "", fbLastName = "", fbPhoneNumber = "", fbEmail = "", fbGender = "", fbDateOfBirth = "", fbCountry = "", fbProfilePicture = "";
    private URL fbProfilePicturenew;
    private int loginType = Constants.LOGIN_VIDEO;
    private MyMVVM myMVVM;
    View view;

    public FacebookLogin(FragmentActivity activity, int loginType, Application application) {
        this.activity = activity;
        FacebookSdk.sdkInitialize(application);
        AppEventsLogger.activateApp(application);
        myMVVM = ViewModelProviders.of(activity).get(MyMVVM.class);
        this.loginType = loginType;
        callbackManager = CallbackManager.Factory.create();

    }

    public void FBLogin() {
        if (CommonUtils.isNetworkConnected(activity)) {

            LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile","email"));
            LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Toast.makeText(activity, "sucess", Toast.LENGTH_SHORT).show();
                    Log.e("facebook", loginResult.getAccessToken().getToken());
                    getFacebookData(loginResult);
                }

                @Override
                public void onCancel() {
                    Log.e("facebook", "cancel");
                    Toast.makeText(activity, "Cancel", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Log.e("facebook", error.getMessage());
                    if (error instanceof FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(activity, "Network Issue", Toast.LENGTH_SHORT).show();
        }
    }

    private void getFacebookData(LoginResult loginResult) {

        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                Log.e("facebook", "completed");

                try {
                    if (object.has("id")) {
                        fbId = object.getString("id");
                        Toast.makeText(activity, fbId, Toast.LENGTH_SHORT).show();
                        Log.e("LoginActivity", "id" + fbId);
                    }

                    //check permission first userName

                    if (object.has("first_name")) {
                        fbFirstName = object.getString("first_name");
                        Log.e("LoginActivity", "first_name" + fbFirstName);
                    }

                    //check permisson last userName

                    if (object.has("last_name")) {
                        fbLastName = object.getString("last_name");
                        Log.e("LoginActivity", "last_name" + fbLastName);
                    }

                    //check permisson email

                    if (object.has("email")) {
                        fbEmail = object.getString("email");
                        Log.e("LoginActivity", "email" + fbEmail);
                    }
                    if (object.has("phoneNumber")) {
                        fbPhoneNumber = object.getString("phoneNumber");
                        Log.e("LoginActivity", "email" + fbPhoneNumber);
                    }

                    if (object.has("gender")) {
                        fbGender = object.getString("gender");
                        Log.e("LoginActivity", "email" + fbGender);
                    }

                    if (object.has("dateofbirth")) {
                        fbDateOfBirth = object.getString("dateofbirth");
                        Log.e("LoginActivity", "email" + fbDateOfBirth);
                    }

                    if (object.has("country")) {
                        fbCountry = object.getString("country");
                        Log.e("LoginActivity", "email" + fbCountry);
                    }

                    JSONObject jsonObject = new JSONObject(object.getString("picture"));
                    if (jsonObject != null) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        Log.e("LoginActivity", "json oject get picture" + dataObject);
                        fbProfilePicturenew = new URL("https://graph.facebook.com/" + fbId + "/picture?width=500&height=500");
                        Log.e("LoginActivity", "json object=>" + object.toString());
                    }

                    if (fbProfilePicturenew != null) {
                        fbProfilePicture = String.valueOf(fbProfilePicturenew);
                    } else {
                        fbProfilePicture = "";
                    }

                    socialLogin();

                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        });

        Bundle bundle = new Bundle();
        Log.e("LoginActivity", "bundle set");
        bundle.putString("fields", "id, first_name, last_name,email,picture,gender,location");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }


    public void fbLogout() {
        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        if (LoginManager.getInstance().getLoginBehavior() != null) {
            LoginManager.getInstance().logOut();
        }
    }


    private void socialLogin() {
        myMVVM.UserSocialLogin(activity,fbId,fbEmail , fbFirstName+" "+fbLastName, "1234567", "android", "", "").observe(activity, new Observer<RegisterPojo>() {
            @Override
            public void onChanged(RegisterPojo registerPojo) {
                if (registerPojo.getSuccess().equalsIgnoreCase("1")) {
                    Toast.makeText(activity, "Login Successfully", Toast.LENGTH_SHORT).show();
                    App.getSharedpref().saveModel(Constants.User_Register, registerPojo);
                    App.getSharedpref().saveString(Constants.USER_Login_STATUS, "1");
                    Intent intent = new Intent(activity, HomeActivity.class);
                    activity.startActivity(intent);

                } else {
                    Toast.makeText(activity, "Login Failed..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
