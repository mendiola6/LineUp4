package nieman.josh.lineup4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;




public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mStartButton;
    private Button SettingsButton;
    private LoginButton loginButton;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_welcome);

        mStartButton = (Button)findViewById(R.id.welcome_button);
        mStartButton.setOnClickListener(this);
        SettingsButton = (Button)findViewById(R.id.settings_button);
        SettingsButton.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.welcome_button:
                //Log.d("TAG","called the welcome button");
                startActivity(new Intent(this,FindGameActivity.class));
                break;
            case R.id.settings_button:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
        }
    }


}
