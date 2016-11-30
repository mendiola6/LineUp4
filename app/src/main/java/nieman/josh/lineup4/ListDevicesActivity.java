package nieman.josh.lineup4;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import nieman.josh.lineup4.R;

/**
 * Created by joshnieman on 11/2/16.
 * Modified from developer.android.com
 */
public class ListDevicesActivity extends Activity {

    private static final String TAG = "LIST_DEVICES";

    private UUID ourUUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a65");

    private BluetoothAdapter mBTAdapter;

    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);

        //request a pop up new window
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_devices_list);

        //set result to canelled in case the user backs out
        setResult(Activity.RESULT_CANCELED);

        //initialize a button to perorm device discovery
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                doDiscovery();
                v.setVisibility(View.GONE);
            }
        });

        //set up the array adapter.
        //we need to make a new one too
        ArrayAdapter<String> pairedDevicesArrayAdapter =
                new ArrayAdapter<String>(this, R.layout.device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this,R.layout.device_name);

        //find a set up the ListView for paired devices
        ListView pairedListView = (ListView)findViewById(R.id.paired_devices);
        pairedListView.setAdapter(pairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        //do the same thing for the newly discovered devices
        ListView newDevicesListView = (ListView)findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        //register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver,filter);

        //register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        //get local bluetooth adapter
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();

        //get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBTAdapter.getBondedDevices();

        //if there are paired devices, add each one to the ArrayAdapter
        if(pairedDevices.size() > 0) {
            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else{
            String noDevices = "No devices have been paired";
            pairedDevicesArrayAdapter.add(noDevices);
        }

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //make sure everything is destroyed and we're no longer doing discover
        if(mBTAdapter != null){
            mBTAdapter.cancelDiscovery();
        }

        //unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    //start a discovery with the bluetooth adapter
    private void doDiscovery(){
        Log.d(TAG, "Doing Discover");

        //indicate scanning in the title
        setProgressBarIndeterminateVisibility(true);
        setTitle("scanning for some devices here...");

        //turn on sub-title for new devices
        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        //if we are already discovering, then stop
        if(mBTAdapter.isDiscovering()){
            mBTAdapter.cancelDiscovery();
        }

        //request bluetooth adapter to discover
        mBTAdapter.startDiscovery();
    }

    //make onclick listener for all of the devices in the lists
    private AdapterView.OnItemClickListener mDeviceClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //we can cancel discovery cus we're about to connect, and discovery is costly
            mBTAdapter.cancelDiscovery();

            //get device mac address, the last 17 chars in View
            String info = ((TextView) view).getText().toString();
            String address = info.substring(info.length() - 17);
            Log.d(TAG, "Attmpting to connect to: " + info + " \n" +address+"\n");

            //make result intent and include mac address
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

            //maybe try to connect as a client here
            //might have to do this in a different thread
//            BluetoothDevice btDevice = mBTAdapter.getRemoteDevice(address);
//            try {
//                BluetoothSocket btSocket = btDevice.createRfcommSocketToServiceRecord(ourUUID);
//                //initiate connection by calling connect()
//                //this is a blocking call that will time out after 12 seconds
//                //should be done in new thread
//                btSocket.connect();
//                //that will throw an error if we can't connect
//            } catch(IOException e){
//                Log.d(TAG, "Error opening socket: " + e);
//            }
            //set results and finish this activity
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };

    //the broadcast receiver the listens for discovered devices and changes the
    //title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent){
            String action = intent.getAction();

            Log.d(TAG,"Checking in onReceive(), action: " + action.toString());
            //when discovery finds a device
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                Log.d(TAG,"found a bluetooth device");
                //get the bluetoothdevice object from the intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //skip if it's already paired, it's already been listed
                if(device.getBondState() != BluetoothDevice.BOND_BONDED){
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
                //when discovery is finished, change the activity title
            } else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                setProgressBarIndeterminateVisibility(false);
                setTitle("Select ah device to connect");
                if(mNewDevicesArrayAdapter.getCount() == 0){
                    String noDevices = "no devices found";
                    mNewDevicesArrayAdapter.add(noDevices);
                }
            }
        }
    };

}
