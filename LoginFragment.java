

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.driveusers.HomeActivity;
import com.example.driveusers.ModelClass.RegisterPojo;
import com.example.driveusers.R;
import com.example.driveusers.Utils.App;
import com.example.driveusers.Utils.Constants;
import com.example.driveusers.Utils.MyMVVM;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

import afu.org.checkerframework.checker.nullness.qual.Nullable;

import static com.facebook.FacebookSdk.getApplicationContext;

public class LoginFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private GoogleSignInOptions gso;
    private GoogleApiClient googleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 2;
    private FirebaseAuth mAuth;
    private String userSocialEmail;
    private String socialID;
    private String social_name;
    private View view;
    private TextView signup, tv_forget_pass;
    private Button btn_login;
    private MyMVVM myMVVM;
    private EditText et_email, et_password;
    private ImageButton google_login,img_fb;
    private String userEmail, userPassword;
    private CallbackManager callbackManager;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
    private static final String EMAIL = "email";

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        myMVVM = ViewModelProviders.of(LoginFragment.this).get(MyMVVM.class);
        mAuth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        FacebookSdk.sdkInitialize(getActivity().getApplication());
        AppEventsLogger.activateApp(getActivity().getApplication());
        callbackManager = CallbackManager.Factory.create();
        findids();
        setup();
        return view;
    }

    private void setup() {
   
        google_login.setOnClickListener(this);
        img_fb.setOnClickListener(this);
    }

    private void findids() {
 
        google_login = view.findViewById(R.id.google_login);
        img_fb = view.findViewById(R.id.img_fb);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
       

            case R.id.google_login:
                signIn();
                break;

            case R.id.img_fb:
                FacebookLogin facebookLogin = new FacebookLogin(getActivity(), Constants.LOGIN_VIDEO, getActivity().getApplication());
                facebookLogin.FBLogin();
                break;
        }
    }

    private void signIn() {
        googleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();
            userSocialEmail = account.getEmail();
            socialID = account.getId();
            social_name = account.getGivenName();
//            socialImage = account.getPhotoUrl();
//            Toast.makeText(getActivity(), "sucess", Toast.LENGTH_SHORT).show();
            Log.d("id", socialID);
            socialLogin(socialID, userSocialEmail, social_name);

            Toast.makeText(getContext(), socialID, Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getContext(), "" + result.getStatus(), Toast.LENGTH_LONG).show();
        }
    }

    private void socialLogin(String socialID, String userSocialEmail, String social_name) {
        myMVVM.UserSocialLogin(getActivity(), socialID, userSocialEmail, social_name, "1234567", "android", "", "").observe(getActivity(), new Observer<RegisterPojo>() {
            @Override
            public void onChanged(RegisterPojo registerPojo) {
                if (registerPojo.getSuccess().equalsIgnoreCase("1")) {
                    Toast.makeText(getActivity(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    App.getSharedpref().saveModel(Constants.User_Register, registerPojo);
                    App.getSharedpref().saveString(Constants.USER_Login_STATUS, "1");
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "Login Failed..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), connectionResult.getErrorMessage(), Toast.LENGTH_SHORT);
    }
}
