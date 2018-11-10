package com.example.gnar.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.SeekBar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.UUID;

import com.example.gnar.myapplication.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.bluetooth.BluetoothProfile.ServiceListener;
import android.bluetooth.BluetoothProfile;

public class MainActivity extends AppCompatActivity {
    static Button btn01, btn00, btn02, btn03, btn04, btn05, btn06, btn07, btn08, btn09, btnStar, btnPound, btnG;

    TextView whiteOneProgressText;
    TextView redOneProgressText;
    TextView blueOneProgressText;
    TextView whiteTwoProgressText;
    TextView redTwoProgressText;
    TextView blueTwoProgressText;


    //----Bluetooth Stuff----
    private static final String TAG = "bluetooth2";
    static TextView txtAlarm;
    static TextView txtGarage;
    static TextView txtArduino;
    //static TextView txtDebug;
    //static TextView txtIndex;
    static TextView txtHumidity;
    static TextView txtTemperature;
    static SwitchCompat switchSecurity;
    static SwitchCompat switchPIR;
    static SwitchCompat switchLaser;
    static SwitchCompat switchDoor;
    public static String currentUser;

    //Handler h;

    final static int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    //private ServiceListener btListener = null;
    //private BluetoothProfile btProfile = btAdapter.getProfileProxy(MainActivity,btListener,BluetoothProfile.A2DP)

    public static StringBuilder sb = new StringBuilder();

    private ConnectedThread mConnectedThread;



    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module (you must edit this line)
    private static String address = "00:14:03:06:72:7B";
    //----End Bluetooth Stuff-----



    //connector.uuidCandidates;



        /**
         * Instances of static inner classes do not hold an implicit
         * reference to their outer class.
         */
    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            MainActivity activity = mActivity.get();
            //WeakReference<TextView> txtAlarm = (TextView)findViewById
            if (activity != null) {
                switch (msg.what) {

                    case RECIEVE_MESSAGE:                                                   // if receive massage
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);                 // create string from bytes array
                        Log.d(TAG, "...strIncom: "+ strIncom);
                        sb.append(strIncom);                                                  // append string
                        int endOfLineIndex = sb.indexOf("\r\n");                            // determine the end-of-line
                        //int endOfLineIndex = sb.indexOf("\n");                            //DO NOT USE THIS
                        if (endOfLineIndex > 0) {                                            // if end-of-line,
                            String sbprint = sb.substring(0, endOfLineIndex);
                            String[] chopchop = sbprint.split(":");// extract string
                            sb.delete(0, sb.length());                                      // and clear
                            if(chopchop.length == 6) {
                                String theString = sbprint + " " + chopchop.length;
                                String first = chopchop[3];
                                String second = chopchop[4];
                                String third = chopchop[5];
                                Boolean numeric = true;
                                try {
                                    Double x = Double.parseDouble(first);
                                    Double y = Double.parseDouble(second);
                                    Double z = Double.parseDouble(third);
                                }catch (NumberFormatException e){
                                    numeric = false;
                                }
                                if (first.equals("a")) {
                                    if (second.equals("t")) {
                                        if (third.equalsIgnoreCase("p")) {
                                            String theMessage = "ALERT! PIR Motion Detected";
                                            txtAlarm.setText(theMessage);
                                            currentUser = "";
                                            switchSecurity.setEnabled(false);
                                            switchSecurity.setChecked(true);
                                            switchDoor.setEnabled(false);
                                            switchDoor.setChecked(true);
                                            switchLaser.setEnabled(false);
                                            switchLaser.setChecked(true);
                                            switchPIR.setEnabled(false);
                                            switchPIR.setChecked(true);
                                            btnG.setEnabled(false);
                                            break;
                                        }
                                        else if (third.equals("u")) {
                                            String theMessage = "ALERT! Unauthorized User";
                                            txtAlarm.setText(theMessage);
                                            //txtAlarm.setText("Security System Armed");
                                            currentUser = "";
                                            switchSecurity.setEnabled(false);
                                            switchSecurity.setChecked(true);
                                            switchDoor.setEnabled(false);
                                            switchDoor.setChecked(true);
                                            switchLaser.setEnabled(false);
                                            switchLaser.setChecked(true);
                                            switchPIR.setEnabled(false);
                                            switchPIR.setChecked(true);
                                            btnG.setEnabled(false);
                                        }
                                        else if (third.equals("l")) {
                                            String theMessage = "ALERT! Laser Tripped";
                                            txtAlarm.setText(theMessage);
                                            //txtAlarm.setText("Security System Armed");
                                            currentUser = "";
                                            switchSecurity.setEnabled(false);
                                            switchSecurity.setChecked(true);
                                            switchDoor.setEnabled(false);
                                            switchDoor.setChecked(true);
                                            switchLaser.setEnabled(false);
                                            switchLaser.setChecked(true);
                                            switchPIR.setEnabled(false);
                                            switchPIR.setChecked(true);
                                            btnG.setEnabled(false);
                                        }
                                        else if(third.equals("d")) {
                                            String theMessage = "ALERT! Front Door Open";
                                            txtAlarm.setText(theMessage);
                                            Log.d(TAG, "Debug atd");
                                            //txtAlarm.setText("Security System Armed");
                                            currentUser = "";
                                            switchSecurity.setEnabled(false);
                                            switchSecurity.setChecked(true);
                                            switchDoor.setEnabled(false);
                                            switchDoor.setChecked(true);
                                            switchLaser.setEnabled(false);
                                            switchLaser.setChecked(true);
                                            switchPIR.setEnabled(false);
                                            switchPIR.setChecked(true);
                                            btnG.setEnabled(false);
                                        }
                                    }
                                    else if (second.equals("u")) {
                                        currentUser = chopchop[5];
                                        String theMessage = "Security System Disarmed by " + currentUser;
                                        txtAlarm.setText(theMessage);
                                        switchSecurity.setEnabled(true);
                                        switchSecurity.setChecked(false);
                                        switchDoor.setEnabled(true);
                                        switchDoor.setChecked(false);
                                        switchLaser.setEnabled(true);
                                        switchLaser.setChecked(false);
                                        switchPIR.setEnabled(true);
                                        switchPIR.setChecked(false);
                                        btnG.setEnabled(true);
                                    }
                                    else{
                                        String theMessage = "Security System Armed";
                                        txtAlarm.setText(theMessage);
                                        currentUser = "";
                                        switchSecurity.setEnabled(false);
                                        switchSecurity.setChecked(true);
                                        switchDoor.setEnabled(false);
                                        switchDoor.setChecked(true);
                                        switchLaser.setEnabled(false);
                                        switchLaser.setChecked(true);
                                        switchPIR.setEnabled(false);
                                        switchPIR.setChecked(true);
                                        btnG.setEnabled(false);
                                    }
                                }
                                else if (first.equals("g")) {
                                //else {
                                    Log.d(TAG, "Debug G" + chopchop[4] + chopchop[5]);
                                    if (second.equals("a")) {
                                        if (third.equals("o")) {
                                            String theMessage = "Opening";
                                            txtGarage.setText(theMessage);
                                            Log.d(TAG, "Debug GAO");
                                            btnG.setEnabled(false);
                                        } //else if (chopchop[5].equals("c")) {
                                        else {
                                            String theMessage = "Closing";
                                            txtGarage.setText(theMessage);
                                            Log.d(TAG, "Debug GAC");
                                            btnG.setEnabled(false);
                                        }
                                    }
                                    else if (second.equals("i")) {
                                        Log.d(TAG, "Debug I");
                                        if (third.equals("o")) {
                                            Log.d(TAG, "Debug O");
                                            String theMessage = "Open";
                                            txtGarage.setText(theMessage);
                                            Log.d(TAG, "Debug GOC");
                                            if(currentUser.length()>=1)
                                                btnG.setEnabled(true);
                                            else
                                                btnG.setEnabled(false);
                                        } //else if (chopchop[5].equals("c")) {
                                        else {
                                            Log.d(TAG, "Debug C");
                                            String theMessage = "Closed";
                                            txtGarage.setText(theMessage);
                                            Log.d(TAG, "Debug GIC");
                                            if(currentUser.length()>=1)
                                                btnG.setEnabled(true);
                                            else
                                                btnG.setEnabled(false);
                                        }

                                    }
                                }
                                else if(numeric){
                                    txtHumidity.setText(chopchop[3]);
                                    txtTemperature.setText(chopchop[4]);
                                    //txtIndex.setText(chopchop[5]);
                                    //Log.d(TAG, "...String:"+ sb.toString() +  "Byte:" + msg.arg1 + "...");
                                }
                            }
                            //chopchop = new String[chopchop.length];
                        }
                        //Log.d(TAG, "...String:"+ sb.toString() +  "Byte:" + msg.arg1 + "...");
                        //break;
                }
            }
        }
    }
    private final MyHandler h = new MyHandler(this);












    SeekBar.OnSeekBarChangeListener whiteOneBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar whiteOneBar, int whiteOneProgress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            whiteOneProgressText.setText("White: " + whiteOneProgress);
            //whiteOneProgressText.setText(whiteOneProgress);
            mConnectedThread.write("W1"+whiteOneProgress+",");
        }

        @Override
        public void onStartTrackingTouch(SeekBar whiteOneBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar whiteOneBar) {
            // called after the user finishes moving the SeekBar
        }
    };
    SeekBar.OnSeekBarChangeListener whiteTwoBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar whiteTwoBar, int whiteTwoProgress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            whiteTwoProgressText.setText("White: " + whiteTwoProgress);
            //whiteTwoProgressText.setText(whiteTwoProgress);
            mConnectedThread.write("W2"+whiteTwoProgress+",");
        }

        @Override
        public void onStartTrackingTouch(SeekBar whiteTwoBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar whiteTwoBar) {
            // called after the user finishes moving the SeekBar
        }
    };
    SeekBar.OnSeekBarChangeListener redOneBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar redOneBar, int redOneProgress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            redOneProgressText.setText("Red: " + redOneProgress);
            //redOneProgressText.setText(redOneProgress);
            mConnectedThread.write("R1"+redOneProgress+",");
        }

        @Override
        public void onStartTrackingTouch(SeekBar redOneBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar redOneBar) {
            // called after the user finishes moving the SeekBar
        }
    };
    SeekBar.OnSeekBarChangeListener redTwoBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar redTwoBar, int redTwoProgress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            redTwoProgressText.setText("Red: " + redTwoProgress);
            //redTwoProgressText.setText(redTwoProgress);
            mConnectedThread.write("R2"+redTwoProgress+",");
        }

        @Override
        public void onStartTrackingTouch(SeekBar redTwoBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar redTwoBar) {
            // called after the user finishes moving the SeekBar
        }
    };
    SeekBar.OnSeekBarChangeListener blueOneBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar blueOneBar, int blueOneProgress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            blueOneProgressText.setText("Blue: " + blueOneProgress);
            //blueOneProgressText.setText(blueOneProgress);
            mConnectedThread.write("B1"+blueOneProgress+",");
        }

        @Override
        public void onStartTrackingTouch(SeekBar blueOneBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar blueOneBar) {
            // called after the user finishes moving the SeekBar
        }
    };
    SeekBar.OnSeekBarChangeListener blueTwoBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar blueTwoBar, int blueTwoProgress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            blueTwoProgressText.setText("Blue: " + blueTwoProgress);
            //blueTwoProgressText.setText(blueTwoProgress);
            mConnectedThread.write("B2"+blueTwoProgress+",");
        }

        @Override
        public void onStartTrackingTouch(SeekBar blueTwoBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar blueTwoBar) {
            // called after the user finishes moving the SeekBar
        }
    };



    //----More Bluetooth stuff
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // set a change listener on the SeekBar
        SeekBar whiteOneBar = findViewById(R.id.whiteOneBar);
        whiteOneBar.setOnSeekBarChangeListener(whiteOneBarChangeListener);

        int whiteOneProgress = whiteOneBar.getProgress();
        whiteOneProgressText = findViewById(R.id.whiteOneProgressText);
        whiteOneProgressText.setText("White: " + whiteOneProgress);
        //whiteOneProgressText.setText(whiteOneProgress);




        SeekBar redOneBar = findViewById(R.id.redOneBar);
        redOneBar.setOnSeekBarChangeListener(redOneBarChangeListener);

        int redOneProgress = redOneBar.getProgress();
        redOneProgressText = findViewById(R.id.redOneProgressText);
        redOneProgressText.setText("Red: " + redOneProgress);
        //redOneProgressText.setText(redOneProgress);




        SeekBar blueOneBar = findViewById(R.id.blueOneBar);
        blueOneBar.setOnSeekBarChangeListener(blueOneBarChangeListener);

        int blueOneProgress = blueOneBar.getProgress();
        blueOneProgressText = findViewById(R.id.blueOneProgressText);
        blueOneProgressText.setText("Blue: " + blueOneProgress);
        //blueOneProgressText.setText(blueOneProgress);









        SeekBar whiteTwoBar = findViewById(R.id.whiteTwoBar);
        whiteTwoBar.setOnSeekBarChangeListener(whiteTwoBarChangeListener);

        int whiteTwoProgress = whiteTwoBar.getProgress();
        whiteTwoProgressText = findViewById(R.id.whiteTwoProgressText);
        whiteTwoProgressText.setText("White: " + whiteTwoProgress);
        //whiteTwoProgressText.setText(whiteTwoProgress);




        SeekBar redTwoBar = findViewById(R.id.redTwoBar);
        redTwoBar.setOnSeekBarChangeListener(redTwoBarChangeListener);

        int redTwoProgress = redTwoBar.getProgress();
        redTwoProgressText = findViewById(R.id.redTwoProgressText);
        redTwoProgressText.setText("Red: " + redTwoProgress);
        //redTwoProgressText.setText(redTwoProgress);




        SeekBar blueTwoBar = findViewById(R.id.blueTwoBar);
        blueTwoBar.setOnSeekBarChangeListener(blueTwoBarChangeListener);

        int blueTwoProgress = blueTwoBar.getProgress();
        blueTwoProgressText = findViewById(R.id.blueTwoProgressText);
        blueTwoProgressText.setText("Blue: " + blueTwoProgress);
        //blueTwoProgressText.setText(blueTwoProgress);










        /*----------PIN PAD EVENT LISTENERS ------------------*/
        btn01 = findViewById(R.id.btn01);
        btn02 = findViewById(R.id.btn02);
        btn03 = findViewById(R.id.btn03);

        btn04 = findViewById(R.id.btn04);
        btn05 = findViewById(R.id.btn05);
        btn06 = findViewById(R.id.btn06);

        btn07 = findViewById(R.id.btn07);
        btn08 = findViewById(R.id.btn08);
        btn09 = findViewById(R.id.btn09);

        btnStar = findViewById(R.id.btnStar);
        btn00 = findViewById(R.id.btn00);
        btnPound = findViewById(R.id.btnPound);

        btnG = findViewById(R.id.btnG);


        switchPIR = findViewById(R.id.switchPIR);
        switchLaser = findViewById(R.id.switchLaser);
        switchDoor = findViewById(R.id.switchDoor);
        switchSecurity = findViewById(R.id.switchSecurity);


        btn01.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("1,");
            }
        });
        btn02.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("2,");
            }
        });
        btn03.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("3,");
            }
        });

        btn04.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("4,");
            }
        });
        btn05.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("5,");
            }
        });
        btn06.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("6,");
            }
        });

        btn07.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("7,");
            }
        });
        btn08.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("8,");
            }
        });
        btn09.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("9,");
            }
        });

        btnStar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("*,");
            }
        });
        btn00.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("0,");
            }
        });
        btnPound.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("#,");
            }
        });
        btnG.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("g,");
            }
        });
        /*----------PIN PAD EVENT LISTENERS ------------------*/

        switchSecurity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    //mConnectedThread.write("SDON,");
                    //mConnectedThread.write("SLON,");
                    //mConnectedThread.write("SPON,");
                    if(currentUser.length()>0)
                        txtAlarm.setText("Security System Armed by " + currentUser);
                    //switchDoor.setChecked(true);
                    //switchLaser.setChecked(true);
                    //switchPIR.setChecked(true);
                    switchDoor.setEnabled(true);
                    switchLaser.setEnabled(true);
                    switchPIR.setEnabled(true);
                }
                else{

                    mConnectedThread.write("SDOFF,");
                    mConnectedThread.write("SLOFF,");
                    mConnectedThread.write("SPOFF,");
                    txtAlarm.setText("Security System Disarmed by " + currentUser);
                    switchDoor.setChecked(false);
                    switchLaser.setChecked(false);
                    switchPIR.setChecked(false);
                    switchDoor.setEnabled(false);
                    switchLaser.setEnabled(false);
                    switchPIR.setEnabled(false);
                }
            }
        });
        switchDoor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) {
                    mConnectedThread.write("SDON,");
                    switchSecurity.setChecked(true);
                }
                else
                    mConnectedThread.write("SDOFF,");
            }
        });
        switchLaser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) {
                    mConnectedThread.write("SLON,");
                    switchSecurity.setChecked(true);
                }
                else
                    mConnectedThread.write("SLOFF,");
            }
        });
        switchPIR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) {
                    mConnectedThread.write("SPON,");
                    switchSecurity.setChecked(true);
                }
                else
                    mConnectedThread.write("SPOFF,");
            }
        });







        //btnOn = (Button) findViewById(R.id.btnOn);                  // button LED ON
        //btnOff = (Button) findViewById(R.id.btnOff);                // button LED OFF
        //txtArduino = (TextView) findViewById(R.id.txtArduino);      // for display the received data from the Arduino
        txtAlarm = findViewById(R.id.txtAlarm);
        txtGarage = findViewById(R.id.txtGarage);
        //txtDebug = (TextView) findViewById(R.id.txtDebug);
        //txtIndex = (TextView) findViewById(R.id.txtIndex);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtTemperature = findViewById(R.id.txtTemperature);


        switchSecurity = findViewById(R.id.switchSecurity);
        switchPIR = findViewById(R.id.switchPIR);
        switchLaser = findViewById(R.id.switchLaser);
        switchDoor = findViewById(R.id.switchDoor);

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

        //mConnectedThread.write("RE");



    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        //if(Build.VERSION.SDK_INT >= 10){
        try {
            final Method  m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, MY_UUID);
            //mConnectedThread.write("RE");
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        //}
        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "...onResume - try connect...");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting...");
        try {
            btSocket.connect();
            Log.d(TAG, "....Connection ok...");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Create Socket...");

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        mConnectedThread.write("RE");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "...In onPause()...");

        try     {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }

    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private void errorExit(String title, String message){
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);        // Get number of bytes and message in "buffer"
                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();     // Send to message queue Handler
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        private void write(String message) {
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }
    }
    //----End More Bluetooth stuff----



}



