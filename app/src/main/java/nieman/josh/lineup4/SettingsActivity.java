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
    EditText ed1,ed2,ed3;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name1 = "name1Key";
    public static final String Name2 = "name2Key";
    public static final String Name3 = "name3Key";
    public static final String Color = "colorKey";


    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ed1=(EditText)findViewById(R.id.editText);
        ed2=(EditText)findViewById(R.id.editText3);
        ed3=(EditText)findViewById(R.id.editText4);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String name1 = sharedpreferences.getString(Name1, "");
        String name2 = sharedpreferences.getString(Name2, "");
        String name3 = sharedpreferences.getString(Name3, "");

        ed1.setText(name1);
        ed2.setText(name2);
        ed3.setText(name3);



        SaveButton = (Button)findViewById(R.id.save_button);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n1  = ed1.getText().toString();
                String n2  = ed2.getText().toString();
                String n3  = ed3.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(Name1, n1);
                editor.putString(Name2, n2);
                editor.putString(Name3, n3);
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
