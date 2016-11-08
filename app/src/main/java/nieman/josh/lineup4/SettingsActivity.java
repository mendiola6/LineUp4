package nieman.josh.lineup4;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import android.app.Activity;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.content.Context;

/**
 * Created by mattm on 11/7/2016.
 */
public class SettingsActivity extends Activity  {

    private Button SaveButton;
    EditText ed1,ed2;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Color = "colorKey";
    public static final String Email = "emailKey";

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText3);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String name = sharedpreferences.getString(Name, "Insert Name");
        String email = sharedpreferences.getString(Email, "Insert Email");

        ed1.setText(name);
        ed2.setText(email);



        SaveButton = (Button)findViewById(R.id.save_button);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n  = ed1.getText().toString();
                String e  = ed2.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(Name, n);
                editor.putString(Email, e);
                editor.commit();
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        SharedPreferences.Editor editor = sharedpreferences.edit();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.blue:
                if (checked) {
                    editor.putString(Color, "blue");
                    editor.apply();
                }
                break;
            case R.id.red:
                if (checked) {
                    editor.putString(Color,"red");
                    editor.apply();
                }
                break;
            case R.id.black:
                if (checked) {
                    editor.putString(Color, "black");
                    editor.apply();
                }
                break;

        }
    }



}
