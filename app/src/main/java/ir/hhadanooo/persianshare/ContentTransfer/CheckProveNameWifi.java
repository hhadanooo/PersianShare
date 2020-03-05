package ir.hhadanooo.persianshare.ContentTransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import ir.hhadanooo.persianshare.ConnectToReciever.ConnectToReciever;
import ir.hhadanooo.persianshare.ContentTransfer.PortalSender.ActivityPortalSender;
import ir.hhadanooo.persianshare.R;

public class CheckProveNameWifi extends AppCompatActivity {

    WifiManager wifiManager;
    Handler handler;
    boolean check_connecting = false;
    ConnectivityManager connManager;
    NetworkInfo mWifi;
    String wifiName;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_prove_name_wifi);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        handler = new Handler();
        connManager =  (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        Bundle bundle = getIntent().getExtras();

        wifiName = bundle.getString("WifiName");
        handler.postDelayed(new run(),3000);


    }
    public class run implements Runnable
    {

        @Override
        public void run() {


            if(wifiManager.getConnectionInfo().getSSID().contains(wifiName))
            {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CheckProveNameWifi.this, "ramin", Toast.LENGTH_SHORT).show();
                        check_connecting = true;

                    }
                });

                    startActivity(new Intent(CheckProveNameWifi.this, ActivityPortalSender.class));
                    finish();


            }
            if(counter == 3)
            {
                check_connecting = true;
                startActivity(new Intent(CheckProveNameWifi.this, ConnectToReciever.class));
                finish();
            }
            counter++;
           if(!check_connecting)
           {
               handler.postDelayed(this,3000);
           }
        }
    }
}
