package nieman.josh.lineup4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.content.SharedPreferences;

/**
 * Created by joshnieman on 10/14/16.
 */
public class BoardGameActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    public static final String Name = "nameKey";
    public static final String Color = "colorKey";

    private GameBoard mGameboard;
    //private View mLinearLayout;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        //setContentView(R.layout.activity_game_board);

        mGameboard = new GameBoard(this);
        mGameboard.setSizeOfBoard(7, 7);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String name = sharedpreferences.getString(Name, "Player");
        String color = sharedpreferences.getString(Color, "Red");


        //want to put board in view defined in xml
        //setContentView(R.layout.activity_game_board);
        setContentView(mGameboard);

    }

    @Override
    public void onStart() {
        super.onStart();



    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
