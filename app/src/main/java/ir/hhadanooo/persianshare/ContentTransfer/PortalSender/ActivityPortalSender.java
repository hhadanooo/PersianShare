package ir.hhadanooo.persianshare.ContentTransfer.PortalSender;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

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
    TextView tv_time;
    Runnable run_time;

    boolean check_time = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal_sender);
        init();
        fileList = new ArrayList<>();
        pathList = new ArrayList<>();
        /*File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/socket");
        File[] files = file.listFiles();
        for(File s:files)
        {
            fileList.add(s);
        }

         */
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
        linearLayout_custom_item = findViewById(R.id.layout_custom_item_send);

        tv_status = findViewById(R.id.tv_Status_client);
        socket = new Socket();
        handler = new Handler();

        tv_size_all = findViewById(R.id.size_all_client);
        handler_timer = new Handler();
        tv_time = findViewById(R.id.time_client);
        run_time = new Timer();
    }
    public class Async_connect extends AsyncTask<String,String,String>
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





                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                handler.postDelayed(this,100);
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
                final List<CustomItemPortal> list_custom = new ArrayList<>();
                for(File f:fileList)
                {
                    size_all_file+= f.length();
                    NumSize += f.getName() + ":" + f.length() + "!";
                    final CustomItemPortal custom_item = new CustomItemPortal(ActivityPortalSender.this);
                    custom_item.SetText_name(f.getName());
                    custom_item.SetText_size(String.valueOf(f.length()));
                    custom_item.SetMaxValue((int) f.length());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            linearLayout_custom_item.addView(custom_item);
                        }
                    });
                    list_custom.add(custom_item);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_size_all.setText(""+size_all_file);
                    }
                });


                outputStream.write(NumSize.getBytes());

                for(int i = 0;i<fileList.size();i++)
                {
                    File f= fileList.get(i);
                    counter = i;

                    size1 = 0;

                    Thread.sleep(1000);

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
                                tv_size_all.setText(""+size_all_file);
                            }
                        });
                    }
                    Thread.sleep(1000);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_status.setText("DisConnected");
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

    /*
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
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


}
