package nieman.josh.lineup4;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by joshnieman on 10/14/16.
 */
public class BoardGame3PlayerActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    public static final String Name1 = "name1Key";
    public static final String Name2 = "name2Key";
    public static final String Name3 = "name3Key";
    public static final String colorKey = "colorKey";

    private GameBoard3Player mGameboard;
    //private View mLinearLayout;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String name1 = sharedpreferences.getString(Name1, "Player");
        String name2 = sharedpreferences.getString(Name2, "Guest 1");
        String name3 = sharedpreferences.getString(Name3, "Guest 2");

        String color = sharedpreferences.getString(colorKey,"black");

        int player1Color = Color.RED; // default
        if(color.equals("black")) {
            player1Color = Color.BLACK;
        }else if(color.equals("blue")){
            player1Color = Color.BLUE;
        }else if(color.equals("green")){
            player1Color = Color.GREEN;
        }

        //setContentView(R.layout.activity_game_board);
        mGameboard = new GameBoard3Player(getApplicationContext());
        mGameboard.setPlayer1Name(name1);
        mGameboard.setPlayer1Color(player1Color);
        mGameboard.setSizeOfBoard(9,8);

        mGameboard.setPlayer2Name(name2);
        mGameboard.setPlayer3Name(name3);

        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        TextView player1Name = new TextView(getApplicationContext());
        player1Name.setText(mGameboard.getPlayer1Name());
        player1Name.setTextColor(mGameboard.getPlayer1Color());
        player1Name.setTextSize(24);
        player1Name.setPadding(10,10,100,10);
        //player1Name.setWidth(700);

        TextView player2Name = new TextView(getApplicationContext());
        player2Name.setText(mGameboard.getPlayer2Name());
        player2Name.setTextColor(mGameboard.getPlayer2Color());
        player2Name.setTextSize(24);
        player2Name.setPadding(100,10,10,10);

        TextView player3Name = new TextView(getApplicationContext());
        player3Name.setText(mGameboard.getPlayer3Name());
        player3Name.setTextColor(mGameboard.getPlayer3Color());
        player3Name.setTextSize(24);
        player3Name.setPadding(100,10,10,10);

        LinearLayout namesLayout = new LinearLayout(getApplicationContext());

        namesLayout.setOrientation(LinearLayout.HORIZONTAL);
        namesLayout.addView(player1Name);
        namesLayout.addView(player2Name);
        namesLayout.addView(player3Name);

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
