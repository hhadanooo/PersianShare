package ir.hhadanooo.persianshare.ContentReceive;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import ir.hhadanooo.persianshare.ContentTransfer.PortalReceiver.ActivityPortalReceiver;
import ir.hhadanooo.persianshare.ContentTransfer.PortalReceiver.CustomItemPortal;
import ir.hhadanooo.persianshare.ContentTransfer.PortalSender.ActivityPortalSender;
import ir.hhadanooo.persianshare.R;

public class ReceiveActivity extends AppCompatActivity {

    /////////////////////////////////////////////////////////////////
    ImageView img_barcode;
    TextView tv_name_wifi;



    DisplayMetrics dm;
    TextView help_barcode_connect_receiver , help_text_connect_receiver;

    View view_top_tv_barcode , view_top_barcode , view_top_view_center , view_top_tv_receive;

    Handler handler;

    int total_all;



    TextView tv_status , tv_size_all , text_PasSize , text_sizeReceived , text_PasTime , text_timePassed , tv_total_send;

    RelativeLayout relativeLayout , lay_tv_time_server , lay_tv_size_all_server;

    View solidEndItem,spaceBelowSeek;




    RelativeLayout ley_portal_receiver;
    LinearLayout ley_receiver;



    ///////////////////////////////////////////////////////////////

    public static String HOTSPOT_NAME = "Default name";
    public static String HOTSPOT_PASSWORD = "Default password";
    public static String NAME_LOG = "matiooo";

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
    public static List<CustomItemPortal> list_item;

    LinearLayout linearLayout;
    FileOutputStream file_out;
    WifiManager.LocalOnlyHotspotReservation mReservation;
    float size_receive;
    int i = 0;
    boolean check_while = true;
    long n = 0;

    long size_all_received = 0;
    String count_all;

    Handler handler_timer_1;
    Handler handler_timer_2;

    long data_transfer = 0;

    long time = 0;
    boolean check_time = false;
    long size_all = 0;
    TextView tv_time;
    long dt_all = 0;



    public static Socket socket_cancel;
    public static InputStream inputStream_cancel;
    public static OutputStream outputStream_cancel;
    boolean check_cancel = false;
    Handler handler_cancel;
    String ip_cancel = "";
    int int_cancel_1 = 0;

    Runnable run_time;
    boolean ch = false;
    RelativeLayout rel;
    WifiManager.LocalOnlyHotspotReservation reservation1;

    String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);



        path = Environment.getExternalStorageDirectory().getAbsolutePath();









        Random random = new Random();
        int numrand = random.nextInt(8999);
        numrand+= 1000;
        HOTSPOT_NAME = "AndroidShare_"+numrand;
        rel = findViewById(R.id.rel);
        HOTSPOT_PASSWORD = "";
        Objects.requireNonNull(getSupportActionBar()).hide();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);

        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        img_barcode = findViewById(R.id.img_barcode_receive);
        tv_name_wifi = findViewById(R.id.tv_name_wifi_receive);
        help_barcode_connect_receiver = findViewById(R.id.help_barcode_connect_receiver);
        help_text_connect_receiver = findViewById(R.id.help_text_connect_receiver);
        view_top_tv_barcode = findViewById(R.id.view_top_tv_barcode);
        view_top_barcode = findViewById(R.id.view_top_barcode);
        view_top_view_center = findViewById(R.id.view_top_view_center);
        view_top_tv_receive = findViewById(R.id.view_top_tv_receive);

        help_barcode_connect_receiver.setTextSize((int)(dm.widthPixels*0.015));



        //tv_name_wifi.getLayoutParams().width = (int)(dm.widthPixels*0.55);
        //tv_name_wifi.getLayoutParams().height = (int)(dm.widthPixels*0.11);
        tv_name_wifi.setTextSize((int)(dm.widthPixels*0.017));


        img_barcode.getLayoutParams().width = (int)(dm.widthPixels*0.58);
        img_barcode.getLayoutParams().height = (int)(dm.widthPixels*0.58);

        /*view_center.getLayoutParams().width = (int)(dm.widthPixels*0.85);
        view_center.getLayoutParams().height = (int)(dm.widthPixels*0.00001);*/

        help_text_connect_receiver.setTextSize((int)(dm.widthPixels*0.015));

        view_top_tv_barcode.getLayoutParams().height = (int)(dm.widthPixels*0.1);

        view_top_barcode.getLayoutParams().height = (int)(dm.widthPixels*0.1);

        view_top_view_center.getLayoutParams().height = (int)(dm.widthPixels*0.1);

        view_top_tv_receive.getLayoutParams().height = (int)(dm.widthPixels*0.1);



        run_time = new Timer();


        /////////////////////////
        tv_size_all = findViewById(R.id.tv_size_all_server);
        tv_time = findViewById(R.id.tv_time_server);
        text_PasSize = findViewById(R.id.text_PasSize);
        text_sizeReceived = findViewById(R.id.text_sizeReceived);
        text_PasTime = findViewById(R.id.text_PasTime);
        text_timePassed = findViewById(R.id.text_timePassed);
        tv_total_send = findViewById(R.id.tv_total_send);

        tv_status = findViewById(R.id.tv_status_receive);

        linearLayout = findViewById(R.id.lin);
        relativeLayout = findViewById(R.id.rel);


        lay_tv_time_server = findViewById(R.id.lay_tv_time_server);
        lay_tv_size_all_server = findViewById(R.id.lay_tv_size_all_server);
        solidEndItem = findViewById(R.id.solidEndItem);
        spaceBelowSeek = findViewById(R.id.spaceBelowSeek);

        lay_tv_time_server.getLayoutParams().height = (int) (dm.widthPixels*.2);
        lay_tv_size_all_server.getLayoutParams().height = (int) (dm.widthPixels*.2);

        tv_size_all.setTextSize((int) (dm.widthPixels*.035));

        tv_time.setTextSize((int) (dm.widthPixels*.035));

        text_PasSize.setTextSize((int) (dm.widthPixels*.009));
        text_sizeReceived.setTextSize((int) (dm.widthPixels*.009));
        text_PasTime.setTextSize((int) (dm.widthPixels*.009));
        text_timePassed.setTextSize((int) (dm.widthPixels*.009));

        solidEndItem.getLayoutParams().width = (int) (dm.widthPixels*.9);

        spaceBelowSeek.getLayoutParams().width = (int) (dm.widthPixels*.01);
        spaceBelowSeek.getLayoutParams().height = (int) (dm.widthPixels*.01);


        tv_total_send.setTextSize((int) (dm.widthPixels*.013));



        ley_portal_receiver = findViewById(R.id.ley_portal_receiver);
        ley_receiver = findViewById(R.id.ley_receiver);
        /////////////////////////


        init();
        handler = new Handler();

        Create_defualt_dir();
        TurnOnHotspot();




    }
    public void Create_defualt_dir()
    {
        File file = new File(path + "/PershianShare");
        if(!file.exists())
        {
            file.mkdir();
        }
        File file1 = new File(path + "/PershianShare/Image");
        if(!file1.exists())
        {
            file1.mkdir();
        }
        File file2 = new File(path + "/PershianShare/Music");
        if(!file2.exists())
        {
            file2.mkdir();
        }
        File file3 = new File(path + "/PershianShare/Documents");
        if(!file3.exists())
        {
            file3.mkdir();
        }

        File file4 = new File(path + "/PershianShare/Video");
        if(!file4.exists())
        {
            file4.mkdir();
        }
        File file5 = new File(path + "/PershianShare/App");
        if(!file5.exists())
        {
            file5.mkdir();
        }
    }


    public void init()
    {

        list_item = new ArrayList<>();
        // initialize object activity receive


        manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        socket = new Socket();
        socket_cancel = new Socket();
        handler_cancel = new Handler();


        handler_check_connecting = new Handler();
        handler_check_disconnecting = new Handler();
        handler_timer_1 = new Handler();
        handler_timer_2 = new Handler();

    }

    public void TurnOnHotspot()
    {

        //turn on hotspot in android lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            WifiManager wifiManager;
            wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            WifiConfiguration wifiConfig = new WifiConfiguration();
            wifiConfig.SSID = HOTSPOT_NAME;
            try {
                if (wifiManager.isWifiEnabled()) { //disables wifi hotspot if it's already enabled
                    wifiManager.setWifiEnabled(false);
                }else {
                    wifiManager.setWifiEnabled(true);
                }
                if (wifiManager.isWifiEnabled()) { //disables wifi hotspot if it's already enabled
                    wifiManager.setWifiEnabled(false);
                }
                Method method = wifiManager.getClass()
                        .getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
                method.invoke(wifiManager, wifiConfig, true);

            } catch (Exception e) {
                Log.e(NAME_LOG, "", e);
            }
        }
        //Turn on hotspot in android vesrion m
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {

            //request permission write setting
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                if(!Settings.System.canWrite(this)) {
                    Log.v(NAME_LOG, " " + !Settings.System.canWrite(this));
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + this.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent,1);
                }
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true)
                        {
                            if(Settings.System.canWrite(ReceiveActivity.this)) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        WifiManager wifiManager;
                                        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                                        WifiConfiguration wifiConfig = new WifiConfiguration();
                                        wifiConfig.SSID = HOTSPOT_NAME;
                                        try {
                                            if (wifiManager.isWifiEnabled()) { //disables wifi hotspot if it's already enabled
                                                wifiManager.setWifiEnabled(false);
                                            }else {
                                                wifiManager.setWifiEnabled(true);
                                            }
                                            if (wifiManager.isWifiEnabled()) { //disables wifi hotspot if it's already enabled
                                                wifiManager.setWifiEnabled(false);
                                            }
                                            Method method = wifiManager.getClass()
                                                    .getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
                                            method.invoke(wifiManager, wifiConfig, true);
                                            start_server();
                                        } catch (Exception e) {
                                            Log.e(NAME_LOG, "", e);
                                        }
                                    }

                                });
                                break;

                            }
                        }

                    }
                });
                thread.start();

            }

            //disable wifi and enable hotsput


        }
        //Turn on hotspot in android oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            manager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {

                @Override
                public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
                    super.onStarted(reservation);
                    reservation1 = reservation;
                    mReservation = reservation;
                    Log.i(NAME_LOG, "onStarted: ");
                    Log.d(NAME_LOG, "Wifi Hotspot is on now");
                    HOTSPOT_NAME = reservation.getWifiConfiguration().SSID;
                    HOTSPOT_PASSWORD = reservation.getWifiConfiguration().preSharedKey;
                    Log.i(NAME_LOG, "SSID : " + HOTSPOT_NAME);
                    Log.i(NAME_LOG, "PASS : " + HOTSPOT_PASSWORD);

                    start_server();
                }
                @Override
                public void onStopped() {
                    super.onStopped();
                    Log.i(NAME_LOG, "onStopped: ");
                }

                @Override
                public void onFailed(int reason) {
                    super.onFailed(reason);
                    Log.i(NAME_LOG, "onFailed: ");
                }
            }, new Handler());

        }
    }

    public void start_server()
    {
        //run on server socket
        new Async_connect().execute();
        //run hander check connecting
        handler_check_connecting.postDelayed(new check_connecting(),1);

        //show hotsput name and hotsput key
        tv_name_wifi.setText(HOTSPOT_NAME + HOTSPOT_PASSWORD);

        try {
            if(HOTSPOT_PASSWORD.equals("Default password"))
            {
                img_barcode.setImageBitmap(TextToImageEncode(HOTSPOT_NAME + ":"));
            }else {
                img_barcode.setImageBitmap(TextToImageEncode(HOTSPOT_NAME + ":" + HOTSPOT_PASSWORD));
            }

        } catch (WriterException e) {
            e.printStackTrace();
        }
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

    public class Async_connect_cancel extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                socket_cancel = new Socket();
                socket_cancel.connect(new InetSocketAddress(ip_cancel,5555));
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

                            ley_receiver.setVisibility(View.GONE);
                            ley_portal_receiver.setVisibility(View.VISIBLE);




                            ////////////////////////

                            tv_status.setText("Connected");
                            ip_cancel = socket.getInetAddress().getHostAddress();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    new Async_connect_cancel().execute();
                                    handler_cancel.postDelayed(new check_connecting_cancel(),1);
                                }
                            },2000);



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


    public class check_connecting_cancel implements Runnable
    {

        @Override
        public void run() {

            if(socket_cancel.isConnected())
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("raminmaleki1234", "cancel " );
                    }
                });
                try {
                    inputStream_cancel = socket_cancel.getInputStream();
                    outputStream_cancel = socket_cancel.getOutputStream();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //tv status set connected cancel
                Thread thread = new Thread(new Read_cancel());
                thread.start();
            }
            else {
                handler_cancel.postDelayed(this,100);
            }
        }
    }

    public static class Async_write_cancel extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                String text = strings[0];
                String st = "rererrefsdfdsf@"+text;
                outputStream_cancel.write(st.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
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

                        if(inputStream.read() != -1) {
                            if (inputStream.available() > 0) {


                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            byte[] buffer = new byte[inputStream.available()];
                            final StringBuilder sb = new StringBuilder();

                            inputStream.read(buffer);

                            for (byte b : buffer) {
                                sb.append((char) b);
                            }







                            String[] vvv = sb.toString().split("#");

                            count_all = vvv[1];
                            s = vvv[2].split("!");


                            if (s.length > 0)//0
                            {


                                for (int z = 0; z < s.length; z++) {
                                    total_all++;

                                    final String[] sp = s[z].split(":");
                                    final CustomItemPortal custom_item = new CustomItemPortal(ReceiveActivity.this, dm, 1);

                                    custom_item.SetNum(z);
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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_total_send.setText(String.format("File  (0/%d)", total_all));
                                    }
                                });


                                new Async_write_cancel().execute("start");
                                check = false;
                            }
                        }
                        }
                    }else {

                        if(check_for)
                        {
                            check_while = false;

                            if(size_all > GetFreeSize())
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        final Snackbar snackbar = Snackbar.make(rel, "storage space not enough!", Snackbar.LENGTH_INDEFINITE);
                                        snackbar.setAction("ok", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                snackbar.dismiss();
                                            }
                                        }).show();

                                        for(CustomItemPortal l:list_item)
                                        {
                                            l.GetButton().setBackground(getDrawable(R.drawable.btncancelled));
                                        }
                                        try {
                                            socket_cancel.close();
                                            socket.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });

                                break;
                            }
                            handler_timer_1.postDelayed(run_time,100);
                            for (i = 0; i < s.length; i++) {







                                if (check_for) {


                                    if(!dis)
                                    {
                                        check_time = true;
                                        break;
                                    }


                                    if(list_item.get(i).GetBoolCancel())
                                    {
                                        continue;
                                    }


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tv_total_send.setText(String.format("File  (%d/%d)",i+1,total_all));
                                        }
                                    });




                                    String[] b = s[i].split(":");
                                    f_name = b[0];
                                    String g = b[1];
                                    f_size = Long.parseLong(g);




                                    if (f_name.contains(".") && (f_name.endsWith(".jpg") ||
                                            f_name.endsWith(".JPG") ||
                                            f_name.endsWith(".png") ||
                                            f_name.endsWith(".PNG") ||
                                            f_name.endsWith(".webp") ||
                                            f_name.endsWith(".jpeg") ||
                                            f_name.endsWith(".JPEG")
                                    )) {
                                        dir_out = new File(path + "/PershianShare/Image/" + f_name);
                                        if(dir_out.exists())
                                        {
                                            String name =  f_name.substring(0,f_name.lastIndexOf("."));
                                            String passname =  f_name.substring(f_name.lastIndexOf("."));
                                            Random random = new Random();
                                            int rand = random.nextInt(899);
                                            rand+= 100;
                                            dir_out = new File(path + "/PershianShare/Image/" + name + "(Copy"+rand+")" + passname);
                                        }


                                    }else if (f_name.contains(".") && (f_name.endsWith(".mp4") ||
                                            f_name.endsWith(".MP4") ||
                                            f_name.endsWith(".mkv")
                                    )) {

                                        dir_out = new File(path + "/PershianShare/Video/" + f_name);
                                        if(dir_out.exists())
                                        {
                                            String name =  f_name.substring(0,f_name.lastIndexOf("."));
                                            String passname =  f_name.substring(f_name.lastIndexOf("."));
                                            Random random = new Random();
                                            int rand = random.nextInt(899);
                                            rand+= 100;
                                            dir_out = new File(path + "/PershianShare/Video/" + name + "(Copy"+rand+")" + passname);
                                        }


                                    }else if (f_name.contains(".") && (f_name.endsWith(".mp3") ||
                                            f_name.endsWith(".MP3") ||
                                            f_name.endsWith(".ogg")
                                    )){
                                        dir_out = new File(path + "/PershianShare/Music/" + f_name);
                                        if(dir_out.exists())
                                        {
                                            String name =  f_name.substring(0,f_name.lastIndexOf("."));
                                            String passname =  f_name.substring(f_name.lastIndexOf("."));
                                            Random random = new Random();
                                            int rand = random.nextInt(899);
                                            rand+= 100;
                                            dir_out = new File(path + "/PershianShare/Music/" + name + "(Copy"+rand+")" + passname);
                                        }
                                    }else  if (f_name.contains(".") && (f_name.endsWith(".apk"))){
                                        dir_out = new File(path + "/PershianShare/App/" + f_name);
                                        if(dir_out.exists())
                                        {
                                            String name =  f_name.substring(0,f_name.lastIndexOf("."));
                                            String passname =  f_name.substring(f_name.lastIndexOf("."));
                                            Random random = new Random();
                                            int rand = random.nextInt(899);
                                            rand+= 100;
                                            dir_out = new File(path + "/PershianShare/App/" + name + "(Copy"+rand+")" + passname);
                                        }
                                    } else {
                                        dir_out = new File(path + "/PershianShare/Documents/" + f_name);
                                        if(dir_out.exists())
                                        {
                                            String name =  f_name.substring(0,f_name.lastIndexOf("."));
                                            String passname =  f_name.substring(f_name.lastIndexOf("."));
                                            Random random = new Random();
                                            int rand = random.nextInt(899);
                                            rand+= 100;
                                            dir_out = new File(path + "/PershianShare/Documents/" + name + "(Copy"+rand+")" + passname);
                                        }
                                    }







                                    try {
                                        file_out = new FileOutputStream(dir_out);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    list_item.get(i).GetButton().setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {


                                            int_cancel_1 = i;
                                            new Async_write_cancel().execute("cancel:"+int_cancel_1);
                                            check_cancel = true;


                                        }
                                    });

                                    int count;


                                    n = 0;

                                    final byte[] buffer = new byte[524288]; // or 4096, or more

                                    while ((count = inputStream.read(buffer)) != -1) {
                                        if(check_cancel)
                                        {
                                            if(int_cancel_1 == i)
                                            {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        list_item.get(i).GetButton().setEnabled(false);
                                                        list_item.get(i).GetButton().setBackground(getDrawable(R.drawable.btncancelled));
                                                    }
                                                });

                                                break;
                                            }
                                            else {
                                                check_cancel = false;
                                            }

                                        }

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
                                                    String sizeall;
                                                    String pasSize;
                                                    float sizeByte = (float) size_all_received;
                                                    sizeByte = sizeByte/1024/1024/1024;
                                                    if (sizeByte < 1.0){
                                                        sizeByte = sizeByte*1024;
                                                        if (sizeByte < 1.0){
                                                            sizeByte = sizeByte*1024;
                                                            @SuppressLint("DefaultLocale") String size = String.format("%.2f" , sizeByte);
                                                            sizeall = size;
                                                            pasSize = "KB";
                                                        }else {
                                                            @SuppressLint("DefaultLocale") String size = String.format("%.2f" , sizeByte);

                                                            sizeall = size;
                                                            pasSize = "MB";
                                                        }
                                                    }else {
                                                        @SuppressLint("DefaultLocale") String size = String.format("%.2f" , sizeByte);

                                                        sizeall = size;
                                                        pasSize = "GB";
                                                    }
                                                    tv_size_all.setText(""+ sizeall);
                                                    text_PasSize.setText(pasSize);
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
                                                    list_item.get(i).GetButton().setBackground(getDrawable(R.drawable.btnfinish));
                                                    list_item.get(i).GetButton().setEnabled(false);
                                                    list_item.get(i).SetBoolCheckEnd(true);

                                                }
                                            });


                                            break;
                                        }

                                    }

                                    if(check_cancel)
                                    {
                                        check_cancel = false;
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        while (true)
                                        {
                                            if(inputStream.available() > 0)
                                            {
                                                byte[] bytes = new byte[inputStream.available()];
                                                inputStream.read(bytes);
                                                String s = new String(bytes);
                                                s = "";
                                            }else {
                                                break;
                                            }

                                        }

                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        while (true)
                                        {
                                            if(inputStream.available() > 0)
                                            {
                                                byte[] bytes = new byte[inputStream.available()];
                                                inputStream.read(bytes);
                                                String s = new String(bytes);
                                                s = "";
                                            }else {
                                                break;
                                            }

                                        }

                                        new Async_write_cancel().execute("Confirmation");

                                    }else {

                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }




                                    if(!dis || dir_out.length() == 0)
                                    {
                                        dis = true;
                                        dir_out.delete();
                                    }
                                    if(!check_cancel && dir_out.length() == 0)
                                    {
                                        break;
                                    }

                                }
                            }


                        }
                    }
                }catch (IOException e)
                {
                    e.fillInStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_status.setText("DisConnected");
                            Toast.makeText(ReceiveActivity.this,"offline",Toast.LENGTH_LONG).show();
                        }
                    });

                    for (CustomItemPortal l:list_item)
                    {
                        if(!l.GetCheckEnd())
                        {
                            l.GetButton().setBackground(getDrawable(R.drawable.btncancelled));
                        }
                    }
                    check_time = true;

                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            if(!check_while)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_status.setText("DisConnected");
                        Toast.makeText(ReceiveActivity.this,"offline",Toast.LENGTH_LONG).show();
                    }
                });

                for (CustomItemPortal l:list_item)
                {
                    if(!l.GetCheckEnd())
                    {
                        l.GetButton().setBackground(getDrawable(R.drawable.btncancelled));
                    }
                }
                check_time = true;
                try {
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public class Read_cancel implements Runnable
    {

        @Override
        public void run() {
            while (true)
            {
                try {
                    if(inputStream_cancel.read() != -1)
                    {
                        byte[] bytes = new byte[inputStream_cancel.available()];
                        inputStream_cancel.read(bytes);
                        final StringBuilder sb = new StringBuilder();
                        for(byte b: bytes)
                        {
                            sb.append((char) b);
                        }
                        sb.append("\n");

                        final String[] strings = sb.toString().split("@");

                        if(strings[1].contains("canceled"))
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("raminmaelki1234321", "run: "+strings[1]);
                                }
                            });
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String s1 = strings[1];
                                    list_item.get(Integer.parseInt(s1.replaceAll("[\\D]", ""))).SetBoolCancel(true);
                                    s1 = strings[1];
                                    list_item.get(Integer.parseInt(s1.replaceAll("[\\D]", ""))).GetButton().setEnabled(false);
                                    s1 = strings[1];
                                    list_item.get(Integer.parseInt(s1.replaceAll("[\\D]", ""))).GetButton().setBackground(getDrawable(R.drawable.btncancelled));
                                }
                            });

                        }else if(strings[1].contains("cancel")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("raminmaelki1234321", "run: "+strings[1]);
                                }
                            });
                            check_cancel = true;
                            int_cancel_1 = Integer.parseInt(strings[1].replaceAll("[\\D]", ""));

                        }





                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public Bitmap TextToImageEncode(String Value) throws WriterException {

        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    350, 350, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.colorPrimary);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 350, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }



    public long GetFreeSize()
    {
        StatFs stat = new StatFs(path);
        long bytesAvailable;
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        }
        else {
            bytesAvailable = (long)stat.getBlockSize() * (long)stat.getAvailableBlocks();
        }
        long megAvailable = bytesAvailable;
        return megAvailable;
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            try {
                socket.close();
                socket_cancel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                reservation1.close();
            }


            System.exit(0);
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





    @Override
    protected void onDestroy() {
        super.onDestroy();
        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }

    public class Timer implements Runnable
    {

        @Override
        public void run() {
            time++;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_time.setText(""+time);
                }
            });
            if(!check_time)
            {
                handler.postDelayed(this,1000);
            }


        }
    }

}