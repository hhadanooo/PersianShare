package ir.hhadanooo.persianshare.add_friend;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Objects;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import ir.hhadanooo.persianshare.MainActivity;
import ir.hhadanooo.persianshare.R;
import ir.hhadanooo.persianshare.bluetooth.BluetoothShare;

public class addFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        QRGEncoder qrgEncoder = new QRGEncoder("https://www.google.com/"
                , null, QRGContents.Type.TEXT,200);



        if (ActivityCompat.checkSelfPermission(this , Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.READ_EXTERNAL_STORAGE} , 123);
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_addfriends);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();

        ImageView iv_back_customAction = view.findViewById(R.id.iv_back_customAction);
        TextView text_customAction = view.findViewById(R.id.text_customAction);

        iv_back_customAction.getLayoutParams().width = (int)(dm.widthPixels*0.1);
        iv_back_customAction.getLayoutParams().height = (int)(dm.widthPixels*0.1);
        text_customAction.setTextSize((int)(dm.widthPixels*0.016));
        iv_back_customAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });



        ImageView bt_button =findViewById(R.id.bt_button);
        ImageView iv_QR_code =findViewById(R.id.iv_QR_code);
        TextView textHelpInvited =findViewById(R.id.textHelpInvited);
        TextView textHelpDown =findViewById(R.id.textHelpDown);
        TextView link_Down =findViewById(R.id.link_Down);
        TextView textHelpQR =findViewById(R.id.textHelpQR);
        View view_bottom_link =findViewById(R.id.view_bottom_link);
        View view_bottom_btn_bluetooth =findViewById(R.id.view_bottom_btn_bluetooth);
        View view_bottom_qrcode =findViewById(R.id.view_bottom_qrcode);
        Button btn_more_add_friends =findViewById(R.id.btn_more_add_friends);


        try {
            // Getting QR-Code as Bitmap
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            // Setting Bitmap to ImageView
            iv_QR_code.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.v("bina", e.toString());
        }


        bt_button.getLayoutParams().width = (int)(dm.widthPixels*0.4);
        bt_button.getLayoutParams().height = (int)(dm.widthPixels*0.12);

        view_bottom_btn_bluetooth.getLayoutParams().width = (int)(dm.widthPixels*0.75);

        view_bottom_qrcode.getLayoutParams().width = (int)(dm.widthPixels*0.47);

        view_bottom_link.getLayoutParams().width = (int)(dm.widthPixels*0.25);


        btn_more_add_friends.getLayoutParams().height = (int)(dm.widthPixels*0.13);
        btn_more_add_friends.setTextSize((int)(dm.widthPixels*0.015));

        iv_QR_code.getLayoutParams().width = (int)(dm.widthPixels*0.45);
        iv_QR_code.getLayoutParams().height = (int)(dm.widthPixels*0.45);


        textHelpInvited.setTextSize((int)(dm.widthPixels*0.013));
        textHelpDown.setTextSize((int)(dm.widthPixels*0.015));
        textHelpQR.setTextSize((int)(dm.widthPixels*0.013));
        link_Down.setTextSize((int)(dm.widthPixels*0.015));




        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> apps = getPackageManager().queryIntentActivities(intent, 0);
        ResolveInfo appinfo = null;
        for (ResolveInfo app : apps ){
            if (app.toString().contains("ir.hhadanooo.persianshare")){
               // Toast.makeText(this, ""+app.toString(), Toast.LENGTH_SHORT).show();
                appinfo = app;
            }

        }

        assert appinfo != null;
        File oldFile = new File(appinfo.activityInfo.applicationInfo.publicSourceDir);
        final File newFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/bas.apk");
        try {
            copyFile(oldFile ,newFile );
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Toast.makeText(this, ""+apps.get(50), Toast.LENGTH_SHORT).show();

        //apps.get(50).activityInfo.applicationInfo.publicSourceDir
        bt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("*/*");
                sharingIntent.setPackage("com.android.bluetooth");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(newFile));
                startActivity(Intent.createChooser(sharingIntent, "Share image"));
                //Toast.makeText(addFriendActivity.this, ""+apps.get(50).activityInfo.applicationInfo.publicSourceDir, Toast.LENGTH_SHORT).show();
            }
        });

        btn_more_add_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = "this is link downLoad: https://www.google.com/";
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
            }
        });
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
