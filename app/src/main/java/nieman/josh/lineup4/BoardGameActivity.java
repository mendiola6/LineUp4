package nieman.josh.lineup4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by joshnieman on 10/14/16.
 */
public class BoardGameActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    public static final String Name = "nameKey";
    public static final String colorKey = "colorKey";

    private GameBoard mGameboard;
    //private View mLinearLayout;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String name = sharedpreferences.getString(Name, "Player");
        String color = sharedpreferences.getString(colorKey,"black");

        int player1Color = Color.RED; // default
        if(color.equals("black")) {
            player1Color = Color.BLACK;
        }else if(color.equals("blue")){
            player1Color = Color.BLUE;
        }

        //setContentView(R.layout.activity_game_board);
        mGameboard = new GameBoard(getApplicationContext());
        mGameboard.setPlayer1Name(name);
        mGameboard.setPlayer1Color(player1Color);
        mGameboard.setSizeOfBoard(7,7);

        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView player1Name = new TextView(getApplicationContext());
        player1Name.setText(mGameboard.getPlayer1Name());
        player1Name.setTextColor(mGameboard.getPlayer1Color());
        player1Name.setTextSize(24);
        TextView player2Name = new TextView(getApplicationContext());
        player2Name.setText(mGameboard.getPlayer2Name());
        player2Name.setTextColor(mGameboard.getPlayer2Color());
        player2Name.setTextSize(24);


        LinearLayout namesLayout = new LinearLayout(getApplicationContext());
        linearLayout.setGravity(Gravity.CENTER);
        namesLayout.setOrientation(LinearLayout.HORIZONTAL);
        namesLayout.addView(player1Name);
        namesLayout.addView(player2Name);

        //linearLayout.addView(player1Name, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(namesLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getApplicationContext(),Gravity.CENTER_VERTICAL);
        linearLayout.addView(mGameboard,1000,1000);//, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        setContentView(linearLayout);

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
