package ir.hhadanooo.persianshare.CheckGPS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Objects;

import ir.hhadanooo.persianshare.ConnectToReciever.ConnectToReciever;
import ir.hhadanooo.persianshare.ContentReceive.ReceiveActivity;
import ir.hhadanooo.persianshare.ContentSend.sendActivity;
import ir.hhadanooo.persianshare.R;

public class CheckGPS extends AppCompatActivity {

    DisplayMetrics dm;
    boolean check = true;
    ImageView btn_next_gps;
    ImageView btn_item_gps_on;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkgps);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);

        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);






        ImageView ic_check_gps = findViewById(R.id.ic_check_gps);
        TextView tv_check_gps = findViewById(R.id.tv_check_gps);
        ImageView iv_item_gps_on = findViewById(R.id.iv_item_gps_on);
        TextView title_gps_on = findViewById(R.id.title_gps_on);
        TextView iv_item_help_gps_on = findViewById(R.id.iv_item_help_gps_on);
        btn_item_gps_on = findViewById(R.id.btn_item_gps_on);
        btn_next_gps = findViewById(R.id.btn_next_gps);
        RelativeLayout lay_gps_item = findViewById(R.id.lay_gps_item);

        RelativeLayout.LayoutParams lp_ic_check_gps = new RelativeLayout.LayoutParams(
                (int) (dm.widthPixels*.3), (int) (dm.widthPixels*.3));
        lp_ic_check_gps.setMargins((int) (dm.widthPixels*.03) , (int) (dm.heightPixels*.065) , 0 ,0);
        ic_check_gps.setLayoutParams(lp_ic_check_gps);

        RelativeLayout.LayoutParams lp_tv_check_gps = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp_tv_check_gps.setMargins((int) (dm.widthPixels*.43) , (int) (dm.heightPixels*.005) , 0 ,0);
        tv_check_gps.setLayoutParams(lp_tv_check_gps);
        tv_check_gps.setTextSize((int) (dm.widthPixels*.024));
        tv_check_gps.setText("Preparations\nSend");

        RelativeLayout.LayoutParams lp_iv_item_gps_on = new RelativeLayout.LayoutParams(
                (int) (dm.widthPixels*.1), (int) (dm.widthPixels*.1));
        lp_iv_item_gps_on.setMargins((int) (dm.widthPixels*.03) , (int) (dm.widthPixels*.03)
                , (int) (dm.widthPixels*.03)  ,(int) (dm.widthPixels*.03) );
        iv_item_gps_on.setLayoutParams(lp_iv_item_gps_on);

        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(provider != null) {
            Log.i("Tat", " Location providers: " + provider);
            if (provider.equals("")) {

                //Toast.makeText(CheckGPS.this, "plz on the gps ", Toast.LENGTH_SHORT).show();
                btn_item_gps_on.setEnabled(true);
                btn_item_gps_on.setImageResource(R.drawable.buttonopen);
                btn_next_gps.setEnabled(false);
                btn_next_gps.setImageResource(R.drawable.buttonnextoff);

            } else {
                Toast.makeText(this, "on button", Toast.LENGTH_SHORT).show();
                btn_item_gps_on.setEnabled(false);
                btn_item_gps_on.setImageResource(R.drawable.checkbox);
                btn_next_gps.setEnabled(true);
                btn_next_gps.setImageResource(R.drawable.buttonnext);
            }
        }
        title_gps_on.setTextSize((int) (dm.widthPixels*.015));


        iv_item_help_gps_on.setTextSize((int) (dm.widthPixels*.011));
        iv_item_help_gps_on.setMaxWidth((int) (dm.widthPixels*.5));




        btn_item_gps_on.getLayoutParams().width = (int) (dm.widthPixels*.17);
        btn_item_gps_on.getLayoutParams().height = (int) (dm.widthPixels*.09);


        btn_item_gps_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, 1234);
            }
        });

        btn_next_gps.getLayoutParams().width = (dm.widthPixels);
        btn_next_gps.getLayoutParams().height = (int) (dm.widthPixels*.12);
        btn_next_gps.setEnabled(false);
        btn_next_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(CheckGPS.this, "click", Toast.LENGTH_SHORT).show();

                String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if(provider != null){
                    Log.i("Tat", " Location providers: "+provider);
                    if (provider.equals("")){

                        Toast.makeText(CheckGPS.this, "plz on the gps ", Toast.LENGTH_SHORT).show();
                        btn_item_gps_on.setEnabled(true);
                        btn_item_gps_on.setImageResource(R.drawable.buttonopen);
                        btn_next_gps.setEnabled(false);
                        btn_next_gps.setImageResource(R.drawable.buttonnextoff);

                    }else {
                        //Toast.makeText(CheckGPS.this, "checkPer", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.CAMERA) !=
                                    PackageManager.PERMISSION_GRANTED){
                                requestPermissions(new String[]{Manifest.permission.CAMERA} , 564);
                            }else {
                                Intent intent = getIntent();
                                String name = intent.getStringExtra("name");
                                if (name.equals("send")){

                                    startActivity(new Intent(CheckGPS.this , ConnectToReciever.class ));
                                    finish();

                                }else if (name.equals("main")){

                                    startActivity(new Intent(CheckGPS.this , ReceiveActivity.class ));
                                    finish();

                                }

                            }
                        }

                    }

                }

            }
        });



        RelativeLayout.LayoutParams lp_lay_gps_item = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,    ViewGroup.LayoutParams.WRAP_CONTENT);
        lp_lay_gps_item.setMargins(0 , (int) (dm.heightPixels*.4) , 0 ,0);
        lay_gps_item.setLayoutParams(lp_lay_gps_item);





    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 564 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
           // Toast.makeText(this, "ramin", Toast.LENGTH_SHORT).show();
             startActivity(new Intent(CheckGPS.this , ConnectToReciever.class ));
             finish();
        }

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 && resultCode == 0) {
            String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if(provider != null){
                Log.i("Tat", " Location providers: "+provider);
                if (provider.equals("")){

                    Toast.makeText(this, "plz on the gps ", Toast.LENGTH_SHORT).show();

                }else {
                    btn_next_gps.setEnabled(true);
                    btn_item_gps_on.setEnabled(false);
                    btn_item_gps_on.setImageResource(R.drawable.checkbox);
                    btn_next_gps.setImageResource(R.drawable.buttonnext);
                }

            }


        }
    }
}
