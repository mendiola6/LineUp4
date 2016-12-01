package nieman.josh.lineup4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import org.json.JSONException;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import java.util.Arrays;
import org.json.JSONObject;
import android.widget.ImageView;
import android.widget.TextView;
import android.net.Uri;
import com.squareup.picasso.Picasso;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mStartButton;
    private Button SettingsButton;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView info;
    Uri imageuri;
    AccessTokenTracker accessTokenTracker;
    String username, firstname, lastname;
    TextView tv_profile_name;
    ImageView iv_profile_pic;
    public static final String NAMES = "Names";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_welcome);
        mStartButton = (Button) findViewById(R.id.welcome_button);
        mStartButton.setOnClickListener(this);
        SettingsButton = (Button) findViewById(R.id.settings_button);
        SettingsButton.setOnClickListener(this);
        info = (TextView) findViewById(R.id.info);
        tv_profile_name = (TextView) findViewById(R.id.tv_profile_name);
       // iv_profile_pic = (ImageView) findViewById(R.id.iv_profile_pic);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
              //  info.setText("Login attempt successful.");
                   /*info.setText("User ID:  " +
                        loginResult.getAccessToken().getUserId() + "\n" +
                        "Auth Token: " + loginResult.getAccessToken().getToken());*/

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                try {
                                    String email = object.getString("email");
                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    // We need an Editor object to make preference changes.
                                    // All objects are from android.context.Context
                                    SharedPreferences settings = getSharedPreferences("NAMES", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("name",name);
                                    // Commit the edits!
                                    editor.commit();
                                    tv_profile_name.setText(name);

                                    String imageurl = "https://graph.facebook.com/" + id + "/picture?type=large";

                                  //  Picasso.with(WelcomeActivity.this).load(imageurl).into(iv_profile_pic);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();



/**
 * AccessTokenTracker to manage logout
 */
                accessTokenTracker = new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                               AccessToken currentAccessToken) {
                        if (currentAccessToken == null) {
                            tv_profile_name.setText("");
                           // iv_profile_pic.setImageResource(R.drawable.maleicon);
                        }
                    }
                };
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.welcome_button:
                //Log.d("TAG","called the welcome button");
                startActivity(new Intent(this, FindGameActivity.class));
                break;
            case R.id.settings_button:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
