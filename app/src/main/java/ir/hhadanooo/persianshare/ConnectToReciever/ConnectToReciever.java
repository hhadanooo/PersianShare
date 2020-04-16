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


import com.google.zxing.Result;

import java.io.IOException;
import java.util.Objects;

import ir.hhadanooo.persianshare.CheckGPS.CheckGPS;
import ir.hhadanooo.persianshare.ContentSend.sendActivity;
import ir.hhadanooo.persianshare.ContentTransfer.CheckProveNameWifi;
import ir.hhadanooo.persianshare.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ConnectToReciever extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView barcode;

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

        //Toast.makeText(this, ""+ sendActivity.list, Toast.LENGTH_SHORT).show();


        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        help_scan_connect_receiver = findViewById(R.id.help_scan_connect_receiver);
        barcode = findViewById(R.id.barcode);
        barcode.setResultHandler(this);
        barcode.startCamera();
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



        barcode.getLayoutParams().width = (int)(dm.widthPixels*0.7);
        barcode.getLayoutParams().height = (int)(dm.widthPixels*0.7);

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




    }

    @Override
    protected void onPause() {
        super.onPause();
        barcode.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {

        final String nameWifi = rawResult.getText();

        if (rawResult.getText().length() != 0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(!check)
                            {
                                barcode.setVisibility(View.INVISIBLE);
                                check = true;
                                String[] s = nameWifi.split(":");
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

                                    Intent intent =new Intent(ConnectToReciever.this, CheckProveNameWifi.class);
                                    intent.putExtra("WifiName",s[0]);
                                    startActivity(intent);
                                    finish();
                                }

                            }


                            //Toast.makeText(ConnectToReciever.this, ""+qrcode.valueAt(0).displayValue, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }).start();

        }

    }
}
