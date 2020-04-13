package ir.hhadanooo.persianshare.ContentTransfer.PortalReceiver;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import ir.hhadanooo.persianshare.R;

public class CustomItemPortal extends RelativeLayout {
    View rootview;
    ImageView img;
    TextView tv_name;
    TextView tv_size;
    TextView tv_size_receive;
    SeekBar seek;
    ImageView btn_cancel;
    View solidEndItem ,spaceBelowSeek ,spaceLeftButton ;
    DisplayMetrics  dm;


    public CustomItemPortal(Context context , DisplayMetrics  dm) {
        super(context);
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



    public void init(Context context)
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

        tv_name.setTextSize((int) (dm.widthPixels*.015));
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
    }
    public void SetText_name(String text)
    {
        tv_name.setText(text);
    }
    public void SetText_size(String text)
    {
        tv_size.setText(text);
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
    public String GetSize()
    {
        return tv_size.getText().toString().trim();
    }
    public String GetSize_receive()
    {
        return tv_size_receive.getText().toString().trim();
    }

    public void SetMaxValue(int max) {
        seek.setMax(max);
    }

    public void SetProgress(int progress) {seek.setProgress(progress);}



}
