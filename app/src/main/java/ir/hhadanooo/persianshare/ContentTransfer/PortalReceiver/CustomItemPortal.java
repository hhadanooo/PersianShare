package ir.hhadanooo.persianshare.ContentTransfer.PortalReceiver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import ir.hhadanooo.persianshare.ContentReceive.ReceiveActivity;
import ir.hhadanooo.persianshare.ContentTransfer.PortalSender.ActivityPortalSender;
import ir.hhadanooo.persianshare.R;

public class CustomItemPortal extends RelativeLayout {
    View rootview;
    ImageView img;
    TextView tv_name;
    TextView tv_size;
    TextView tv_size_receive;
    SeekBar seek;
    String sizeFile;

    View solidEndItem ,spaceBelowSeek ,spaceLeftButton ;
    DisplayMetrics  dm;
    ImageView btn_cancel;
    boolean bool_cancel = false;
    int num = 0;
    int check = 0;

    public CustomItemPortal(Context context) {
        super(context);

        init(context);
    }

    public CustomItemPortal(Context context , DisplayMetrics  dm,int check) {
        super(context);
        this.check = check;
        this.dm = dm;
        init(context);
    }

    public CustomItemPortal(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomItemPortal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    public void init(final Context context)
    {
        rootview = inflate(context,R.layout.layout_custom_item_portal,this);
        img = rootview.findViewById(R.id.img);
        tv_name = rootview.findViewById(R.id.tv_name);
        tv_size = rootview.findViewById(R.id.tv_size);
        tv_size_receive = rootview.findViewById(R.id.tv_size_receive);
        seek = rootview.findViewById(R.id.seek);
        solidEndItem = rootview.findViewById(R.id.solidEndItem);
        spaceBelowSeek = rootview.findViewById(R.id.spaceBelowSeek);
        spaceLeftButton = rootview.findViewById(R.id.spaceLeftButton);
        btn_cancel = rootview.findViewById(R.id.btn_cancel);

        seek.getLayoutParams().width = (int) (dm.widthPixels*.7);

        img.getLayoutParams().width = (int) (dm.widthPixels*.12);
        img.getLayoutParams().height = (int) (dm.widthPixels*.12);

        tv_name.setTextSize((int) (dm.widthPixels*.013));
        tv_size.setTextSize((int) (dm.widthPixels*.012));
        tv_size_receive.setTextSize((int) (dm.widthPixels*.012));

        btn_cancel.getLayoutParams().width = (int) (dm.widthPixels*.18);
        btn_cancel.getLayoutParams().height = (int) (dm.widthPixels*.08);

        solidEndItem.getLayoutParams().width = (int) (dm.widthPixels*.9);

        spaceBelowSeek.getLayoutParams().width = (int) (dm.widthPixels*.01);
        spaceBelowSeek.getLayoutParams().height = (int) (dm.widthPixels*.01);

        spaceLeftButton.getLayoutParams().width = (int) (dm.widthPixels*.03);
        spaceLeftButton.getLayoutParams().height = (int) (dm.widthPixels*.03);








        seek.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_cancel.setEnabled(false);
                btn_cancel.setBackground(context.getDrawable(R.drawable.btncancelled));


                if(check == 1)
                {
                    ReceiveActivity.list_item.get(num).SetBoolCancel(true);

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String st = "rererrefsdfdsf@"+"canceled:" + num;
                                ReceiveActivity.outputStream_cancel.write(st.getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }else if (check == 2)
                {
                    ActivityPortalSender.list_custom.get(num).SetBoolCancel(true);

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String st = "rererrefsdfdsf@"+"canceled:" + num;
                                ActivityPortalSender.outputStream_cancel.write(st.getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }








            }
        });
    }
    public void SetText_name(String text)
    {
        if (text.contains(".") && (text.endsWith(".jpg") ||
                text.endsWith(".JPG") ||
                text.endsWith(".png") ||
                text.endsWith(".PNG") ||
                text.endsWith(".webp") ||
                text.endsWith(".jpeg") ||
                text.endsWith(".JPEG")
        )) {

            img.setImageResource(R.drawable.iconimg);


        }else if (text.contains(".") && (text.endsWith(".mp4") ||
                text.endsWith(".MP4") ||
                text.endsWith(".mkv")
        )) {

            img.setImageResource(R.drawable.iconmovi);


        }else if (text.contains(".") && (text.endsWith(".mp3") ||
                text.endsWith(".MP3") ||
                text.endsWith(".ogg")
        )){
            img.setImageResource(R.drawable.icon_m);
        }else {
            img.setImageResource(R.drawable.doc_icon2);
        }



        String nameFile = text;
        String endName;

        if (nameFile.length() > 20){
            endName = nameFile.substring(nameFile.length()-7);

            String startName = nameFile.substring(0 ,7 );

            tv_name.setText(startName+" ... "+endName);
        }else {
            tv_name.setText(nameFile);
        }



    }
    public void SetText_size(String text)
    {
        sizeFile = text;
        String sizeFile;
        float sizeByte = Float.parseFloat(text);
        sizeByte = sizeByte/1024/1024/1024;
        if (sizeByte < 1.0){
            sizeByte = sizeByte*1024;
            if (sizeByte < 1.0){
                sizeByte = sizeByte*1024;
                @SuppressLint("DefaultLocale") String size = String.format("%.2f" , sizeByte);
                sizeFile = size+" KB";
            }else {
                @SuppressLint("DefaultLocale") String size = String.format("%.2f" , sizeByte);

                sizeFile = size+" MB";
            }
        }else {
            @SuppressLint("DefaultLocale") String size = String.format("%.2f" , sizeByte);

            sizeFile = size+" GB";
        }


        tv_size.setText(sizeFile);
    }


    public void SetText_size_receive(String text)
    {

        int r = text.lastIndexOf(".");

        String t = "";
        for(int i = 0;i<r;i++)
        {
            t += String.valueOf(text.charAt(i));
        }
        tv_size_receive.setText(t+"%");

    }
    public ImageView GetButton()
    {
        return btn_cancel;
    }
    public String GetSize()
    {
        return sizeFile;
    }
    public String GetSize_receive()
    {
        return tv_size_receive.getText().toString().trim();
    }

    public void SetMaxValue(int max) {
        seek.setMax(max);
    }

    public void SetProgress(int progress) {seek.setProgress(progress);}

    public void SetBoolCancel(boolean bool_cancel)
    {
        this.bool_cancel = bool_cancel;
    }
    public boolean GetBoolCancel()
    {
        return this.bool_cancel;
    }


    public void SetNum(int num)
    {
        this.num = num;
    }
    public int GetNum()
    {
        return this.num;
    }





}
