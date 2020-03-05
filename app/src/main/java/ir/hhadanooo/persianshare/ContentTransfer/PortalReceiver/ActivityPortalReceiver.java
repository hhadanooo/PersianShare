package ir.hhadanooo.persianshare.ContentTransfer.PortalReceiver;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import ir.hhadanooo.persianshare.R;

public class ActivityPortalReceiver extends AppCompatActivity {

    public static String HOTSPOT_NAME = "Default name";
    public static String HOTSPOT_PASSWORD = "Default password";
    public static String NAME_LOG = "matiooo";

    boolean disconnect = false;

    boolean dis = true;

    WifiManager manager;
    ServerSocket serverSocket;
    Socket socket;
    InputStream inputStream;
    Handler handler_check_connecting,handler_check_disconnecting;
    File dir_out;
    boolean check = true;
    String f_name = "";
    long f_size = 0;
    String[] s;
    boolean check_for = true;
    List<CustomItemPortal> list_item;
    LinearLayout linearLayout;
    RelativeLayout relativeLayout;
    FileOutputStream file_out;
    int sss = 0;
    WifiManager.LocalOnlyHotspotReservation mReservation;
    float size_receive;
    int i = 0;
    boolean check_while = true;
    long n = 0;
    TextView tv_status;
    TextView tv_size_all;
    long size_all_received = 0;
    String count_all;
    int c_time1 = 0;
    Handler handler_timer_1;
    Handler handler_timer_2;

    long data_transfer = 0;
    long data_transfer_hand = 0;
    long current_speed = 0;
    boolean check_handler_time1 = false;
    long time = 0;
    boolean check_end_timer2 = false;
    long size_all = 0;
    TextView tv_time;
    int counter_time = 0;
    long dt_all = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_receiver);
        init();

        start_server();




    }


    public void init()
    {
        // initialize object activity receive

        tv_size_all = findViewById(R.id.tv_size_all_server);

        tv_time = findViewById(R.id.tv_time_server);
        manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        socket = new Socket();


        handler_check_connecting = new Handler();
        handler_check_disconnecting = new Handler();
        handler_timer_1 = new Handler();
        handler_timer_2 = new Handler();
        tv_status = findViewById(R.id.tv_status_receive);


        list_item = new ArrayList<>();

        linearLayout = findViewById(R.id.lin);
        relativeLayout = findViewById(R.id.rel);
        Create_defualt_dir();

        //start_server();
    }
    public void Create_defualt_dir()
    {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/share");
        if(!file.exists())
        {
            file.mkdir();
        }
    }


    public void start_server()
    {
        //run on server socket
        new Async_connect().execute();
        //run hander check connecting
        handler_check_connecting.postDelayed(new check_connecting(),100);
        //show hotsput name and hotsput key

    }

    // creae Async task,run server socket,Ready to connect
    public class Async_connect extends AsyncTask<String,String,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            try {
                serverSocket = new ServerSocket(8080);
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    //async task check connecting
    public class check_connecting implements Runnable
    {

        @Override
        public void run() {

            if(socket.isConnected())
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            inputStream = socket.getInputStream();
                            tv_status.setText("Connected");


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                Thread thread = new Thread(new thread_read());
                thread.start();




            }
            else {
                handler_check_connecting.postDelayed(this,100);
            }
        }
    }


    public class thread_read implements Runnable
    {
        @Override
        public void run() {

            while(check_while)
            {

                try {

                    if(check)
                    {

                        if(inputStream.read() != -1)
                        {
                            byte[] buffer = new byte[inputStream.available()];
                            inputStream.read(buffer);
                            final StringBuilder sb = new StringBuilder();

                            for(byte b:buffer)
                            {
                                sb.append((char) b);
                            }

                            String[] vvv = sb.toString().split("#");

                            count_all = vvv[1];
                            s = vvv[2].split("!");

                            if(s.length > 0)//0
                            {
                                for(int z = 0;z<s.length;z++)
                                {
                                    final String[] sp = s[z].split(":");
                                    final CustomItemPortal custom_item = new CustomItemPortal(ActivityPortalReceiver.this);

                                    custom_item.SetText_name(sp[0]);
                                    String str = sp[1];

                                    long size = Long.valueOf(str);

                                    size_all += size;
                                    custom_item.SetText_size(String.valueOf((size)));
                                    custom_item.SetMaxValue((int) size);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            linearLayout.addView(custom_item);
                                        }
                                    });
                                    list_item.add(custom_item);

                                }

                                check = false;
                            }
                        }
                    }else {

                        if(check_for)
                        {
                            check_while = false;
                            for (i = 0; i < s.length; i++) {

                                if (check_for) {

                                    if(!dis)
                                    {
                                        check_end_timer2 = true;
                                        break;
                                    }

                                    String[] b = s[i].split(":");
                                    f_name = b[0];
                                    String g = b[1];
                                    f_size = Long.parseLong(g);

                                    dir_out = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/socket/" + f_name);

                                    try {
                                        file_out = new FileOutputStream(dir_out);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                    int count;

                                    if(!check_handler_time1)
                                    {
                                        handler_timer_1.postDelayed(new Timer1(),100);
                                        check_handler_time1 = true;
                                    }
                                    n = 0;
                                    final byte[] buffer = new byte[524288]; // or 4096, or more

                                    while ((count = inputStream.read(buffer)) != -1) {

                                        data_transfer+=count;
                                        dt_all += count;
                                        dis = false;
                                        file_out.write(buffer, 0, count);
                                        n+= count;

                                        if (i < s.length) {
                                            long num1 =n / 1024;
                                            long f =  Long.parseLong(list_item.get(i).GetSize());
                                            long sssss =f / 1024;
                                            size_receive = (num1* 100) /sssss;

                                            size_all_received += count;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    list_item.get(i).SetProgress((int) dir_out.length());
                                                    list_item.get(i).SetText_size_receive(size_receive+"");
                                                    tv_size_all.setText(""+ size_all_received);
                                                }
                                            });

                                        }

                                        if(f_size == n)
                                        {
                                            dis = true;

                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    list_item.get(i).SetProgress((int) f_size);
                                                    list_item.get(i).SetText_size_receive("100.0");
                                                }
                                            });


                                            break;
                                        }

                                    }

                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    if(!dis || dir_out.length() == 0)
                                    {
                                        dis = false;
                                        dir_out.delete();
                                    }

                                }
                            }


                        }
                    }
                }catch (IOException e)
                {
                    e.fillInStackTrace();
                }
            }

            if(!check_while)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_status.setText("DisConnected");
                    }
                });
                check_end_timer2 = true;
                try {
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

   /*
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }

    public class Timer1 implements Runnable
    {
        @Override
        public void run() {
            if(c_time1 == 3)
            {
                data_transfer_hand = data_transfer;
                current_speed = data_transfer_hand / 3;
                time = size_all / current_speed;
                size_all -= data_transfer;
                //handler_timer_2.postDelayed(new Timer2(),10);
            }else {
                handler_timer_1.postDelayed(this,1000);
            }
            c_time1++;
        }
    }
    public class Timer2 implements Runnable
    {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_time.setText(""+time);
                }
            });
            time -= 1;
            if(counter_time == 0)
            {
                data_transfer = 0;
            }
            counter_time++;
            if(counter_time == 5)
            {
                current_speed = data_transfer / 10;
                time = (size_all - dt_all) / current_speed;
                counter_time = 0;
            }
            if(!check_end_timer2)
            {
                handler_timer_2.postDelayed(this,1000);
            }
        }
    }
}