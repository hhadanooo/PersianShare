package ir.hhadanooo.persianshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;
import java.util.prefs.Preferences;

import ir.hhadanooo.persianshare.ContentReceive.ReceiveActivity;
import ir.hhadanooo.persianshare.ContentSend.sendActivity;
import ir.hhadanooo.persianshare.add_friend.addFriendActivity;
import ir.hhadanooo.persianshare.setting.settingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        soft_key();

        ImageView sendButton = findViewById(R.id.SendButton);
        ImageView receiveButton = findViewById(R.id.ReceiveButton);
        ImageView setting = findViewById(R.id.setting);
        ImageView addFriends = findViewById(R.id.addFriends);
        ImageView icon_bg = findViewById(R.id.icon_bg);
        View view_left = findViewById(R.id.view_left);
        View view_top = findViewById(R.id.view_top);


        sendButton.getLayoutParams().width = (int)(dm.widthPixels*0.5);
        sendButton.getLayoutParams().height = (int)(dm.widthPixels*0.14);

        receiveButton.getLayoutParams().width = (int)(dm.widthPixels*0.5);
        receiveButton.getLayoutParams().height = (int)(dm.widthPixels*.14);

        setting.getLayoutParams().width = (int)(dm.widthPixels*0.08);
        setting.getLayoutParams().height = (int)(dm.widthPixels*0.08);

        addFriends.getLayoutParams().width = (int)(dm.widthPixels*0.08);
        addFriends.getLayoutParams().height = (int)(dm.widthPixels*0.08);

        view_left.getLayoutParams().width = (int)(dm.widthPixels*0.01);
        view_top.getLayoutParams().height = (int)(dm.heightPixels*0.065);


        icon_bg.getLayoutParams().width = (int)(dm.widthPixels*0.35);
        icon_bg.getLayoutParams().height = (int)(dm.widthPixels*0.35);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , sendActivity.class));

            }
        });

        receiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , ReceiveActivity.class));

                //Toast.makeText(MainActivity.this, "there is not activity ", Toast.LENGTH_SHORT).show();
            }
        });

        addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , addFriendActivity.class));
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , settingActivity.class));
            }
        });




        SharedPreferences pref_setting = PreferenceManager.getDefaultSharedPreferences(this);

        boolean vibration = pref_setting.getBoolean("vibration" , true);

        //Toast.makeText(this, ""+vibration, Toast.LENGTH_SHORT).show();


    }


    public void soft_key(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
    }
}
