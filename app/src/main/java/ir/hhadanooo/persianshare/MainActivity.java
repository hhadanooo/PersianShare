package ir.hhadanooo.persianshare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.MailTo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.pushpole.sdk.PushPole;

import java.io.File;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Objects;
import java.util.prefs.Preferences;

import ir.hhadanooo.persianshare.CheckGPS.CheckGPS;
import ir.hhadanooo.persianshare.ConnectToReciever.ConnectToReciever;
import ir.hhadanooo.persianshare.ContentReceive.ReceiveActivity;
import ir.hhadanooo.persianshare.ContentSend.sendActivity;

import ir.hhadanooo.persianshare.add_friend.addFriendActivity;
import ir.hhadanooo.persianshare.setting.settingActivity;

public class MainActivity extends AppCompatActivity {

    DisplayMetrics dm;
    Dialog diaVpn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        PushPole.initialize(this,true);









        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        diaVpn = new Dialog(this);
        diaVpn.setContentView(R.layout.layout_dialog_vpn);
        ImageView iv_warning , iv_btn ;
        TextView tv_title ,tv_message;
        iv_warning = diaVpn.findViewById(R.id.iv_warning);
        iv_btn = diaVpn.findViewById(R.id.iv_btn);
        tv_title = diaVpn.findViewById(R.id.tv_title);
        tv_message = diaVpn.findViewById(R.id.tv_message);


        iv_warning.getLayoutParams().width = (int)(dm.widthPixels*.12);
        iv_warning.getLayoutParams().height = (int)(dm.widthPixels*.12);

        iv_btn.getLayoutParams().width = (int)(dm.widthPixels*.25);
        iv_btn.getLayoutParams().height = (int)(dm.widthPixels*.09);

        tv_title.setTextSize((int)(dm.widthPixels*.018));
        tv_message.setTextSize((int)(dm.widthPixels*.014));

        diaVpn.setCancelable(false);
        Objects.requireNonNull(diaVpn.getWindow()).setLayout((int)(dm.widthPixels*.7)
                , (int)(dm.widthPixels*.55));
        iv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.net.vpn.SETTINGS");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent , 325);
                diaVpn.dismiss();
            }
        });
        if (vpn()){
            diaVpn.show();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE  ) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.ACCESS_FINE_LOCATION   } , 564);
            }

        }


        final DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        soft_key();

        ImageView sendButton = findViewById(R.id.SendButton);
        ImageView receiveButton = findViewById(R.id.ReceiveButton);
        ImageView menu_main = findViewById(R.id.menu_main);

        ImageView icon_bg = findViewById(R.id.icon_bg);
        View view_left = findViewById(R.id.view_left);
        View view_top = findViewById(R.id.view_top);


        menu_main.getLayoutParams().width = (int)(dm.widthPixels*0.1);
        menu_main.getLayoutParams().height = (int)(dm.widthPixels*.1);


        sendButton.getLayoutParams().width = (int)(dm.widthPixels*0.5);
        sendButton.getLayoutParams().height = (int)(dm.widthPixels*0.14);

        receiveButton.getLayoutParams().width = (int)(dm.widthPixels*0.5);
        receiveButton.getLayoutParams().height = (int)(dm.widthPixels*.14);


        view_left.getLayoutParams().width = (int)(dm.widthPixels*0.01);
        view_top.getLayoutParams().height = (int)(dm.heightPixels*0.065);


        icon_bg.getLayoutParams().width = (int)(dm.widthPixels*0.35);
        icon_bg.getLayoutParams().height = (int)(dm.widthPixels*0.35);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                            PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} , 564);
                    }else {
                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION  ) !=
                                PackageManager.PERMISSION_GRANTED  ){
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION  } , 564);
                        }else {
                            startActivity(new Intent(MainActivity.this , sendActivity.class));
                        }

                    }
                }



            }
        });

        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (provider != null) {
                    Log.i("Tat", " Location providers: " + provider);
                    if (provider.equals("")) {

                        // Toast.makeText(sendActivity.this, " Location providers: "+provider, Toast.LENGTH_SHORT).show();
                        Intent inten = new Intent(MainActivity.this, CheckGPS.class);
                        inten.putExtra("name" , "main");
                        startActivity(inten);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                                    PackageManager.PERMISSION_GRANTED){
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION} , 564);
                            }else {

                                startActivity(new Intent(MainActivity.this , ReceiveActivity.class ));

                            }
                        }
                    }

                }


                //Toast.makeText(MainActivity.this, "there is not activity ", Toast.LENGTH_SHORT).show();
            }
        });



        menu_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu pop = new PopupMenu(MainActivity.this , v);
                pop.getMenuInflater().inflate(R.menu.pop_menu , pop.getMenu());
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.Invite){

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                                        PackageManager.PERMISSION_GRANTED){
                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} , 564);
                                }else {
                                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION  ) !=
                                            PackageManager.PERMISSION_GRANTED  ){
                                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION  } , 564);
                                    }else {
                                        startActivity(new Intent(MainActivity.this , addFriendActivity.class));
                                    }

                                }
                            }

                        }else if (item.getItemId() == R.id.Setting){

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=
                                        PackageManager.PERMISSION_GRANTED){
                                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} , 564);
                                }else {
                                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION  ) !=
                                            PackageManager.PERMISSION_GRANTED  ){
                                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION  } , 564);
                                    }else {
                                        startActivity(new Intent(MainActivity.this , settingActivity.class));
                                    }

                                }
                            }

                        }else if (item.getItemId() == R.id.About){

                           Dialog dia_about = new Dialog(MainActivity.this);
                           dia_about.setContentView(R.layout.dia_about);
                           Objects.requireNonNull(dia_about.getWindow()).setLayout(
                                   (int)(dm.widthPixels*0.9) , ViewGroup.LayoutParams.WRAP_CONTENT);
                           TextView Policy_Title = dia_about.findViewById(R.id.Policy_Title);
                           TextView Policy_Text = dia_about.findViewById(R.id.Policy_Text);
                           ImageView btn_rating = dia_about.findViewById(R.id.btn_rating);

                            Policy_Title.setTextSize((int)(dm.widthPixels*0.016));
                            Policy_Text.setTextSize((int)(dm.widthPixels*0.012));

                            btn_rating.getLayoutParams().width = (int)(dm.widthPixels*0.52);
                            btn_rating.getLayoutParams().height = (int)(dm.widthPixels*0.12);

                            btn_rating.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //////
                                }
                            });


                           dia_about.show();
                        }

                        return false;
                    }
                });
                pop.show();
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 325 && resultCode == 0) {
            if (vpn()){
                diaVpn.show();
            }
        }
    }

    public boolean vpn() {
        String iface = "";
        try {
            for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (networkInterface.isUp())
                    iface = networkInterface.getName();
                Log.d("DEBUG", "IFACE NAME: " + iface);
                if ( iface.contains("tun") || iface.contains("ppp") || iface.contains("pptp")) {
                    return true;
                }
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!vpn()){
            if (diaVpn.isShowing()){
                diaVpn.dismiss();
            }

        }
    }

    public void soft_key(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
    }



}
