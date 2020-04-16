package ir.hhadanooo.persianshare.ContentTransfer.PortalSender;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.hhadanooo.persianshare.ContentSend.sendActivity;
import ir.hhadanooo.persianshare.ContentTransfer.PortalReceiver.ActivityPortalReceiver;
import ir.hhadanooo.persianshare.ContentTransfer.PortalReceiver.CustomItemPortal;
import ir.hhadanooo.persianshare.R;

public class ActivityPortalSender extends AppCompatActivity {
    Button btn_connect;
    TextView tv_status;
    Socket socket;
    InputStream inputStream;
    OutputStream outputStream;
    Handler handler;
    BufferedReader bfd;
    InputStream file_in;
    int size;
    long size1;
    List<File> fileList;
    List<String> pathList;
    LinearLayout linearLayout_custom_item;
    int counter;

    int time = 0;

    public String HOTSPOT_NAME = "";
    String NumSize;
    long n = 0;
    long size_all_file = 0;
    TextView tv_size_all;
    int count_all= 0 ;
    Handler handler_timer;
    TextView tv_time , text_PasSize , text_sizeSent ,  text_PasTime , text_timeLeft ,tv_total_send;
    Runnable run_time;
    DisplayMetrics dm;
    RelativeLayout lay_tv_time_server , lay_tv_size_all_server;
    View solidEndItem , spaceBelowSeek;


    public static List<CustomItemPortal> list_custom;
    ServerSocket serverSocket_cancel;
    public static Socket socket_cancel;
    public static InputStream inputStream_cancel;
    public static OutputStream outputStream_cancel;
    Handler handler_cancel;
    boolean check_cancel;
    int int_cancel;
    int int_cancel1;
    int i = 0;
    boolean check_start = false;

    int total_all ,total_received;

    boolean Confirmation_send = false;

    boolean check_time = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_sender);
        Objects.requireNonNull(getSupportActionBar()).hide();
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        init();
        fileList = new ArrayList<>();
        pathList = new ArrayList<>();

        List<String> list = sendActivity.list;




        for(String s:list)
        {


            File file = new File(s);
            fileList.add(file);

        }


        Log.i("matiooo123", "onCreate:");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Async_connect().execute("192.168.43.1","8080");
            }
        },2000);

        handler.postDelayed(new run_connecting(),100);



    }

    public void init()
    {
        list_custom = new ArrayList<>();
        linearLayout_custom_item = findViewById(R.id.layout_custom_item_send);

        text_PasSize = findViewById(R.id.text_PasSize);
        text_sizeSent = findViewById(R.id.text_sizeSent);
        text_PasTime = findViewById(R.id.text_PasTime);
        text_timeLeft = findViewById(R.id.text_timeLeft);
        tv_total_send = findViewById(R.id.tv_total_send);

        lay_tv_time_server = findViewById(R.id.lay_time_client);
        lay_tv_size_all_server = findViewById(R.id.lay_size_all_client);
        solidEndItem = findViewById(R.id.solidEndItem);
        spaceBelowSeek = findViewById(R.id.spaceBelowSeek);

        tv_status = findViewById(R.id.tv_Status_client);
        socket = new Socket();
        handler = new Handler();

        tv_size_all = findViewById(R.id.size_all_client);
        handler_timer = new Handler();
        tv_time = findViewById(R.id.time_client);
        run_time = new Timer();

        lay_tv_time_server.getLayoutParams().height = (int) (dm.widthPixels*.2);
        lay_tv_size_all_server.getLayoutParams().height = (int) (dm.widthPixels*.2);

        tv_size_all.setTextSize((int) (dm.widthPixels*.035));

        tv_time.setTextSize((int) (dm.widthPixels*.035));

        text_PasSize.setTextSize((int) (dm.widthPixels*.009));
        text_sizeSent.setTextSize((int) (dm.widthPixels*.009));
        text_PasTime.setTextSize((int) (dm.widthPixels*.009));
        text_timeLeft.setTextSize((int) (dm.widthPixels*.009));

        solidEndItem.getLayoutParams().width = (int) (dm.widthPixels*.9);

        spaceBelowSeek.getLayoutParams().width = (int) (dm.widthPixels*.01);
        spaceBelowSeek.getLayoutParams().height = (int) (dm.widthPixels*.01);


        tv_total_send.setTextSize((int) (dm.widthPixels*.013));


        socket_cancel = new Socket();
        handler_cancel = new Handler();

    }public class Async_connect extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("matiooo12345", "run: ");
                }
            });
            String ip = strings[0];
            int port = Integer.valueOf(strings[1]);
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(ip,port));

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
                serverSocket_cancel = new ServerSocket(5555);
                socket_cancel = serverSocket_cancel.accept();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public class run_connecting implements Runnable
    {
        @Override
        public void run() {
            if(socket.isConnected())
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_status.setText("Connected");
                    }
                });

                try {
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                    bfd = new BufferedReader(new InputStreamReader(inputStream));
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new Async_write().execute();
                        }
                    },1000);

                    new Async_connect_cancel().execute();
                    handler.postDelayed(new run_connecting_cancel(),10);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                handler.postDelayed(this,100);
            }
        }
    }

    public class run_connecting_cancel implements Runnable
    {
        @Override
        public void run() {
            if(socket_cancel.isConnected())
            {
                //tv status set connected cancel

                try {
                    inputStream_cancel = socket_cancel.getInputStream();
                    outputStream_cancel = socket_cancel.getOutputStream();

                    Thread thread = new Thread(new Read_cancel());
                    thread.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                handler_cancel.postDelayed(this,100);
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("mtiooo323", sb.toString());
                            }
                        });
                        if(strings[1].contains("Confirmation"))
                        {
                            Confirmation_send = true;
                        }else if(strings[1].contains("canceled"))
                        {
                            final String[] sf = strings[1].split(":");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String s1 = sf[1];
                                    list_custom.get(Integer.parseInt(s1.replaceAll("[\\D]", ""))).SetBoolCancel(true);
                                    s1 = sf[1];
                                    list_custom.get(Integer.parseInt(s1.replaceAll("[\\D]", ""))).GetButton().setEnabled(false);
                                    s1 = sf[1];
                                    list_custom.get(Integer.parseInt(s1.replaceAll("[\\D]", ""))).GetButton().setBackground(getDrawable(R.drawable.btncancelled));

                                }
                            });

                        }
                        else  if(strings[1].contains("cancel"))  {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("raminmaleki1234321", "run: "+strings[1]);
                                }
                            });
                            int_cancel1 = Integer.parseInt(strings[1].replaceAll("[\\D]", ""));
                            check_cancel = true;
                        }else if(strings[1].contains("start")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("raminmaleki1234", "run: ramin");
                                }
                            });
                            check_start = true;
                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    public class write_cancel implements Runnable{

        @Override
        public void run() {
            try {
                String st = "rererre@cancel :" + int_cancel;
                outputStream_cancel.write(st.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class Async_write extends AsyncTask<String,String,String>
    {
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... strings) {

            handler_timer.postDelayed(run_time,100);
            try{
                count_all = fileList.size();
                NumSize = "ffhsdufshduh#" + count_all +"#";

                counter = 0;
                int c = 0;
                for(File f:fileList)
                {

                    String sourcePath = f.getPath();
                    String appName = "";

                    if (sourcePath.endsWith(".apk")) {
                        PackageInfo packageInfo = getPackageManager()
                                .getPackageArchiveInfo(sourcePath, PackageManager.GET_ACTIVITIES);
                        if(packageInfo != null) {
                            ApplicationInfo appInfo = packageInfo.applicationInfo;
                            if (Build.VERSION.SDK_INT >= 8) {
                                appInfo.sourceDir = sourcePath;
                                appInfo.publicSourceDir = sourcePath;
                            }
                            appName = String.valueOf(appInfo.loadLabel(getPackageManager()));
                            /*File file1 = new File(s , appName+".apk");
                            fileList.add(file1);*/

                            total_all++;
                            size_all_file+= f.length();
                            NumSize += appName+".apk"+ ":" + f.length() + "!";
                            final CustomItemPortal custom_item = new CustomItemPortal(ActivityPortalSender.this,dm,2);
                            custom_item.SetText_name(appName+".apk");
                            custom_item.SetText_size(String.valueOf(f.length()));
                            custom_item.SetMaxValue((int) f.length());
                            custom_item.SetNum(c);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    linearLayout_custom_item.addView(custom_item);
                                }
                            });
                            list_custom.add(custom_item);
                            c++;
                        }
                    }else {
                        total_all++;
                        size_all_file+= f.length();
                        NumSize += f.getName() + ":" + f.length() + "!";
                        final CustomItemPortal custom_item = new CustomItemPortal(ActivityPortalSender.this,dm,2);
                        custom_item.SetText_name(f.getName());
                        custom_item.SetText_size(String.valueOf(f.length()));
                        custom_item.SetMaxValue((int) f.length());
                        custom_item.SetNum(c);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                linearLayout_custom_item.addView(custom_item);
                            }
                        });
                        list_custom.add(custom_item);
                        c++;
                    }



                }









                outputStream.write(NumSize.getBytes());


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("raminmaleki123456", "run: "+NumSize.length());
                    }
                });

                while (true)
                {
                    if(check_start)
                    {
                        break;
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_total_send.setText(String.format("File  (0/%d)",total_all));
                    }
                });


                for(i = 0;i<fileList.size();i++)
                {
                    if(list_custom.get(i).GetBoolCancel())
                    {
                        continue;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_total_send.setText(String.format("File  (%d/%d)",i+1,total_all));
                        }
                    });

                    final File f= fileList.get(i);
                    counter = i;

                    size1 = 0;

                    Thread.sleep(1000);

                    list_custom.get(i).GetButton().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int_cancel1 = i;
                            int_cancel = i;
                            Thread thread = new Thread(new write_cancel());
                            thread.start();
                            list_custom.get(i).GetButton().setBackground(getDrawable(R.drawable.btncancelled));
                            list_custom.get(i).GetButton().setEnabled(false);
                            check_cancel = true;
                        }
                    });

                    try {
                        file_in = new FileInputStream(f);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    int count;

                    byte[] buffer = new byte[524288];// or 4096, or more

                    n = 0;
                    while ((count = file_in.read(buffer)) > 0)
                    {
                        if(check_cancel)
                        {
                            if(int_cancel1 == i)
                            {
                                int_cancel = i;
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        list_custom.get(i).GetButton().setBackground(getDrawable(R.drawable.btncancelled));;
                                        list_custom.get(i).GetButton().setEnabled(false);
                                    }
                                });
                                break;
                            }else {
                                check_cancel = false;
                            }

                        }
                        outputStream.write(buffer, 0, count);

                        size_all_file-=count;
                        n+= count;

                        size1+=buffer.length;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                float num2 = (n * 100) / Long.valueOf(list_custom.get(counter).GetSize());
                                list_custom.get(counter).SetText_size_receive(num2+"");
                                list_custom.get(counter).SetProgress((int) size1);

                                String sizeall;
                                String pasSize;
                                float sizeByte = (float) size_all_file;
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

                    if(!check_cancel)
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list_custom.get(i).GetButton().setEnabled(false);
                                list_custom.get(i).GetButton().setBackground(getDrawable(R.drawable.btnfinish));

                            }
                        });

                    }

                    Thread.sleep(1000);

                    if(check_cancel)
                    {
                        check_cancel = false;
                        while (true)
                        {
                            if(Confirmation_send)
                            {
                                Confirmation_send = false;
                                break;
                            }
                        }

                    }


                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_status.setText("DisConnected");
                        Toast.makeText(ActivityPortalSender.this,"offline",Toast.LENGTH_LONG).show();
                    }
                });
                check_time = true;
            }catch (IOException e)
            {
                e.fillInStackTrace();
                check_time = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_status.setText("DisConnected");
                        Toast.makeText(ActivityPortalSender.this,"offline",Toast.LENGTH_LONG).show();
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
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



    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            try {
                socket_cancel.close();
                socket.close();
                for(CustomItemPortal l:list_custom)
                {
                   if(l.GetSize_receive().isEmpty())
                   {
                       l.GetButton().setBackground(getDrawable(R.drawable.btncancelled));
                   }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            finish();
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


}
