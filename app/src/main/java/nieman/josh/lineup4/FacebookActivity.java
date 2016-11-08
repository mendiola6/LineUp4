package nieman.josh.lineup4;

/**
 * Created by katem on 11/7/16.
 */
import android.app.Activity;
import android.os.Bundle;
import com.facebook.appevents.AppEventsLogger;



import com.facebook.FacebookSdk;

public class FacebookActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.newLogger(this);
    }
}
