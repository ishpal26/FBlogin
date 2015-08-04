package ishpal.hungrystillmustwait;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Ishpal on 26/6/2015.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button home, nearby, search, logout;

    //just added
    private TextView mTextDetails;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;

    public static final String FRAGMENT_TAG = "fragment_tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        home = (Button) findViewById(R.id.bHome);
        home.setOnClickListener(this);
        nearby = (Button) findViewById(R.id.bNearby);
        nearby.setOnClickListener(this);
        search = (Button) findViewById(R.id.bSearch);
        search.setOnClickListener(this);
        logout = (Button) findViewById(R.id.bLogout);
        logout.setOnClickListener(this);

        mCallbackManager = CallbackManager.Factory.create();
        //setupTokenTracker();
        //setupProfileTracker();

        //mTokenTracker.startTracking();
        //mProfileTracker.startTracking();


        /*FragmentManager mFragmentManager;
        mFragmentManager = getSupportFragmentManager();

        Fragment fragment = mFragmentManager.findFragmentByTag(FRAGMENT_TAG);
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(android.R.id.content, new FragmentLoginPage(), FRAGMENT_TAG);
        transaction.commit();*/
    }



    /*public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_page, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupTextDetails(view);
        setupLoginButton(view);
    }*/

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
       // mTokenTracker.stopTracking();
       // mProfileTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /*private void setupTextDetails(View view) {
        mTextDetails = (TextView) view.findViewById(R.id.text_details);
    }

    private void setupTokenTracker() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("CURR ACES TOK Changed", "" + currentAccessToken);
            }
        };
    }

    private void setupProfileTracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d("AT CURR PROF CHANGED", "" + currentProfile);
            }
        };
    }

    /*private void setupLoginButton(View view) {
        LoginButton mButtonLogin = (LoginButton) view.findViewById(R.id.login_button);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bHome:          // when logout button is pressed we clear the user data and set logged in as false
                break;
            case R.id.bNearby:
                break;
            case R.id.bSearch:
                startActivity(new Intent(this, Search.class));
                break;
            case R.id.bLogout:
                finish();
                break;
        }
    }
}
