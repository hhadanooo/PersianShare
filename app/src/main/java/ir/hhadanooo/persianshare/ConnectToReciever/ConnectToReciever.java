package ir.hhadanooo.persianshare.ConnectToReciever;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.Objects;

import ir.hhadanooo.persianshare.CheckGPS.CheckGPS;
import ir.hhadanooo.persianshare.ContentTransfer.CheckProveNameWifi;
import ir.hhadanooo.persianshare.R;

public class ConnectToReciever extends AppCompatActivity {

    SurfaceView barcode;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    DisplayMetrics dm;
    TextView help_scan_connect_receiver , help_write_connect_receiver;
    EditText et_enter_pass_name;
    boolean check = false;


    View view_center , view_top_tv_scaner , view_top_scaner , view_top_view_center , view_top_tv_write , view_top_et_write;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_reciever);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);

        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        help_scan_connect_receiver = findViewById(R.id.help_scan_connect_receiver);
        barcode = findViewById(R.id.barcode);
        //view_center = findViewById(R.id.view_center);
        help_write_connect_receiver = findViewById(R.id.help_write_connect_receiver);
        et_enter_pass_name = findViewById(R.id.et_enter_pass_name);
        view_top_tv_scaner = findViewById(R.id.view_top_tv_scaner);
        view_top_scaner = findViewById(R.id.view_top_scaner);
        view_top_view_center = findViewById(R.id.view_top_view_center);
        view_top_et_write = findViewById(R.id.view_top_et_write);

        help_scan_connect_receiver.setTextSize((int)(dm.widthPixels*0.015));


        et_enter_pass_name.setBackgroundResource(R.drawable.border_button);

        et_enter_pass_name.getLayoutParams().width = (int)(dm.widthPixels*0.55);
        et_enter_pass_name.getLayoutParams().height = (int)(dm.widthPixels*0.11);



        barcode.getLayoutParams().width = (int)(dm.widthPixels*0.55);
        barcode.getLayoutParams().height = (int)(dm.widthPixels*0.55);

        /*view_center.getLayoutParams().width = (int)(dm.widthPixels*0.85);
        view_center.getLayoutParams().height = (int)(dm.widthPixels*0.00001);*/

        help_write_connect_receiver.setTextSize((int)(dm.widthPixels*0.015));

        view_top_tv_scaner.getLayoutParams().height = (int)(dm.widthPixels*0.08);

        view_top_scaner.getLayoutParams().height = (int)(dm.widthPixels*0.07);

        view_top_view_center.getLayoutParams().height = (int)(dm.widthPixels*0.07);

        view_top_et_write.getLayoutParams().height = (int)(dm.widthPixels*0.07);

        et_enter_pass_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 16){

                    String name = s.toString().substring(0,4);
                    String pass = s.toString().substring(4);

                    Log.i("matiooo12345", "name : " + name + ":: pass : " + pass);



                        Toast.makeText(ConnectToReciever.this, "16", Toast.LENGTH_SHORT).show();
                        WifiConfiguration wifiConfig = new WifiConfiguration();
                        wifiConfig.SSID = String.format("\"%s\"","AndroidShare_"+ name);
                        wifiConfig.preSharedKey = String.format("\"%s\"", pass);


                        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
                        if (!wifiManager.isWifiEnabled()){
                            wifiManager.setWifiEnabled(true);
                        }
                        wifiManager.disconnect();
                        int netId = wifiManager.addNetwork(wifiConfig);

                        wifiManager.enableNetwork(netId, true);
                        wifiManager.reconnect();

                        Intent intent =new Intent(ConnectToReciever.this, CheckProveNameWifi.class);
                        intent.putExtra("WifiName","AndroidShare_"+ name);
                        startActivity(intent);
                        finish();




                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();



        cameraSource = new CameraSource.Builder(this , barcodeDetector)
                .setRequestedPreviewSize(768 , 1366

                ).setAutoFocusEnabled(true)
                .build();

        barcode.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcode = detections.getDetectedItems();

                if (qrcode.size() != 0){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if(!check)
                                    {
                                        check = true;
                                        String[] s = qrcode.valueAt(0).displayValue.split(":");
                                        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                                        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                                        if(s.length == 2)
                                        {

                                            WifiConfiguration wifiConfig = new WifiConfiguration();
                                            wifiConfig.SSID = String.format("\"%s\"", s[0]);
                                            wifiConfig.preSharedKey = String.format("\"%s\"", s[1]);
                                            //wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

                                            WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
                                            if (!wifiManager.isWifiEnabled()){
                                                wifiManager.setWifiEnabled(true);
                                            }
                                            wifiManager.disconnect();
                                            int netId = wifiManager.addNetwork(wifiConfig);
                                            //Toast.makeText(ConnectToReciever.this, ""+s[0], Toast.LENGTH_SHORT).show();

                                            wifiManager.enableNetwork(netId, true);
                                            wifiManager.reconnect();

                                            Intent intent =new Intent(ConnectToReciever.this, CheckProveNameWifi.class);
                                            intent.putExtra("WifiName",s[0]);
                                            startActivity(intent);
                                            finish();


                                        }else {

                                            WifiConfiguration wifiConfig = new WifiConfiguration();
                                            wifiConfig.SSID = String.format("\"%s\"", s[0]);

                                            wifiConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

                                            WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
                                            if (!wifiManager.isWifiEnabled()){
                                                wifiManager.setWifiEnabled(true);
                                            }
                                            wifiManager.disconnect();
                                            int netId = wifiManager.addNetwork(wifiConfig);
                                            // Toast.makeText(ConnectToReciever.this, ""+s[0], Toast.LENGTH_SHORT).show();

                                            wifiManager.enableNetwork(netId, true);
                                            wifiManager.reconnect();
                                            if (mWifi.isConnected()) {
                                                WifiManager wifiName = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
                                                if (wifiName.getConnectionInfo().getBSSID().equals(s[0])){
                                                    startActivity(new Intent(ConnectToReciever.this , CheckGPS.class));
                                                    ;                                                Toast.makeText(ConnectToReciever.this, "connect", Toast.LENGTH_SHORT).show();
                                                }
                                            }else{
                                                //Toast.makeText(ConnectToReciever.this, "pass is false", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    }


                                    //Toast.makeText(ConnectToReciever.this, ""+qrcode.valueAt(0).displayValue, Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).start();

                }
            }
        });


    }
}
