package ir.hhadanooo.persianshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import java.util.Objects;

import ir.hhadanooo.persianshare.ContentSend.sendActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        ImageView sendButton = findViewById(R.id.SendButton);
        ImageView receiveButton = findViewById(R.id.ReceiveButton);
        ImageView setting = findViewById(R.id.setting);
        ImageView addFriends = findViewById(R.id.addFriends);
        ImageView icon_bg = findViewById(R.id.icon_bg);
        View view_left = findViewById(R.id.view_left);
        View view_top = findViewById(R.id.view_top);


        sendButton.getLayoutParams().width = (int)(dm.widthPixels*0.6);
        sendButton.getLayoutParams().height = (int)(dm.widthPixels*0.17);

        receiveButton.getLayoutParams().width = (int)(dm.widthPixels*0.6);
        receiveButton.getLayoutParams().height = (int)(dm.widthPixels*.17);

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
    }
}
