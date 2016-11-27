package nieman.josh.lineup4;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

/**
 * Created by joshnieman on 11/8/16.
 */
public class BluetoothActivity extends Activity implements View.OnClickListener{

    private static final int DISCOVERABLE_REQUEST_CODE = 0x1;
    private UUID ourUUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a65");
    private Button mSeachAndListDiscoveredDevices;
    private Button mDiscoverButton;


    @Override
    public void onCreate(Bundle savedBundle) {
        super.onCreate(savedBundle);
        //set up some buttons
        setContentView(R.layout.activity_bluetooth);
        //mBTAdapter = BluetoothAdapter.getDefaultAdapter();

        //discover
        mDiscoverButton = (Button) findViewById(R.id.make_discoverable_button);
        mDiscoverButton.setOnClickListener(this);


        //search and list button
        mSeachAndListDiscoveredDevices = (Button) findViewById(R.id.see_dicovered_devices_button);
        mSeachAndListDiscoveredDevices.setOnClickListener(this);

        //whatever, we'll just start looking right away
//        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
//        startActivityForResult(discoverableIntent, DISCOVERABLE_REQUEST_CODE);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.make_discoverable_button:
                //do discovery
                Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(discoverableIntent, DISCOVERABLE_REQUEST_CODE);
                break;

            case R.id.see_dicovered_devices_button:
                //see the devices that we've found
                Intent listDevicesIntent = new Intent(getApplicationContext(), ListDevicesActivity.class);
                startActivity(listDevicesIntent);
                break;

        }

    }
}
