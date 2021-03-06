package ir.hhadanooo.persianshare.ContentSend;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.hhadanooo.persianshare.CheckGPS.CheckGPS;
import ir.hhadanooo.persianshare.ConnectToReciever.ConnectToReciever;
import ir.hhadanooo.persianshare.ContentSend.Slider.PagerAdapterFrag;
import ir.hhadanooo.persianshare.ContentSend.Slider.SlideFileManager;
import ir.hhadanooo.persianshare.R;
import ir.hhadanooo.persianshare.ContentSend.bottomSheet.DialogBottomSheetAdapter;
import ir.hhadanooo.persianshare.ContentSend.bottomSheet.ModelItemBottomSheet;


public class sendActivity extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    ExtendedFloatingActionButton exfb;
    public static ExtendedFloatingActionButton ex_counter;
    View cent;
    public static List<String> list;
    PagerAdapterFrag adapterFrag;
    SlideFileManager fragment;
    public static DisplayMetrics dm;
    RelativeLayout lay_viewPager;
    AVLoadingIndicatorView a;
    Handler handler = new Handler();
    List<ModelItemBottomSheet> listFile;
    RecyclerView rv_bottom_sheet;
    TextView tv_plz;
    float sizeAll;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_layout);
        Objects.requireNonNull(getSupportActionBar()).hide();


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);

        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        lay_viewPager = findViewById(R.id.lay_viewPager);
        tv_plz = findViewById(R.id.tv_plz);
        tv_plz.setTextSize((int)(dm.widthPixels*0.013));



        new Thread(new Runnable() {
            @Override
            public void run() {
                a =  findViewById(R.id.avi);
                handler.postDelayed(new loop() , 100);
                a.getLayoutParams().width = (int)(dm.widthPixels*0.4);
                a.getLayoutParams().height = (int)(dm.widthPixels*0.4);

            }
        }).start();





        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.getLayoutParams().height = (int)(dm.widthPixels*0.15);
        list = new ArrayList<>();

        viewPager = findViewById(R.id.pager);


        exfb = findViewById(R.id.exFb);
        cent = findViewById(R.id.cent);
        ex_counter = findViewById(R.id.ex_counter);

        exfb.getLayoutParams().width = (int)(dm.widthPixels*0.4);
        exfb.setTextSize((int)(dm.widthPixels*0.015));

        ex_counter.getLayoutParams().width = (int)(dm.widthPixels*0.4);
        ex_counter.setTextSize((int)(dm.widthPixels*0.015));


        ex_counter.setText(""+list.size());

        cent.getLayoutParams().width = (int)(dm.widthPixels*0.1);


            exfb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (list.size() > 0) {

                        //Toast.makeText(sendActivity.this, list.size() + "\n" + list, Toast.LENGTH_SHORT).show();
                        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                        if (provider != null) {
                            Log.i("Tat", " Location providers: " + provider);
                            if (provider.equals("")) {

                                // Toast.makeText(sendActivity.this, " Location providers: "+provider, Toast.LENGTH_SHORT).show();
                                Intent inten = new Intent(sendActivity.this, CheckGPS.class);
                                inten.putExtra("name" , "send");
                                startActivity(inten);
                            } else {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (checkSelfPermission(Manifest.permission.CAMERA) !=
                                            PackageManager.PERMISSION_GRANTED){
                                        requestPermissions(new String[]{Manifest.permission.CAMERA} , 564);
                                    }else {
                                        startActivity(new Intent(sendActivity.this , ConnectToReciever.class ));
                                    }
                                }
                            }

                        }

                    }else {
                        Toast.makeText(sendActivity.this, "plz choose ", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        final BottomSheetDialog bottomSheerDialog = new BottomSheetDialog(sendActivity.this);
        ex_counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (list.size() > 0) {


                    View parentView = getLayoutInflater().inflate(R.layout.dialogsheet, null);
                    rv_bottom_sheet = parentView.findViewById(R.id.rv_bottom_sheet);

                    TextView tv_selected_bottom_sheet = parentView.findViewById(R.id.tv_selected_bottom_sheet);
                    TextView tv_size_length_bottom_sheet = parentView.findViewById(R.id.tv_size_length_bottom_sheet);




                    listFile = new ArrayList<>();
                    prepareData();
                    String sizeapp;
                    sizeAll = sizeAll / 1024 / 1024 / 1024;
                    if (sizeAll < 1.0) {
                        sizeAll = sizeAll * 1024;
                        if (sizeAll < 1.0) {
                            sizeAll = sizeAll * 1024;
                            @SuppressLint("DefaultLocale") String size = String.format("%.2f", sizeAll);
                            sizeapp = size + " KB";
                        } else {
                            @SuppressLint("DefaultLocale") String size = String.format("%.2f", sizeAll);

                            sizeapp = size + " MB";
                        }
                    } else {
                        @SuppressLint("DefaultLocale") String size = String.format("%.2f", sizeAll);

                        sizeapp = size + " GB";
                    }
                    tv_size_length_bottom_sheet.setText(list.size() + " of " + sizeapp);
                    refreshDisplay();

                    bottomSheerDialog.setContentView(parentView);
                    //BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) parentView.getParent());

                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
                    bottomSheerDialog.show();

                }
            }
        });



        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                adapterFrag = new PagerAdapterFrag(getSupportFragmentManager(), 2);

                                viewPager.setAdapter(adapterFrag);

                                tabLayout.setupWithViewPager(viewPager);

                                tabLayout.setOnTabSelectedListener(sendActivity.this);

                                fragment = (SlideFileManager) Objects
                                        .requireNonNull(viewPager.getAdapter()).instantiateItem(viewPager, viewPager.getCurrentItem());


                            }
                        },1000);
                    }
                });



            }
        }).start();





    }

    private void prepareData() {
        if (list.size() > 0){
            for (int i = 0 ; i < list.size() ; i++){

                File file = new File(list.get(i));
                String sizeapp;
                float sizeByte = (float) file.length();
                sizeAll = sizeAll+sizeByte;
                sizeByte = sizeByte/1024/1024/1024;
                if (sizeByte < 1.0){
                    sizeByte = sizeByte*1024;
                    if (sizeByte < 1.0){
                        sizeByte = sizeByte*1024;
                        @SuppressLint("DefaultLocale") String size = String.format("%.2f" , sizeByte);
                        sizeapp = size+" KB";
                    }else {
                        @SuppressLint("DefaultLocale") String size = String.format("%.2f" , sizeByte);

                        sizeapp = size+" MB";
                    }
                }else {
                    @SuppressLint("DefaultLocale") String size = String.format("%.2f" , sizeByte);

                    sizeapp = size+" GB";
                }
                //String nameFile = list.get(i).substring(list.get(i).lastIndexOf("/")+1);
                listFile.add(new ModelItemBottomSheet(list.get(i) , sizeapp , R.drawable.doc_icon2 , R.drawable.xicon));
            }
        }

    }
    private void refreshDisplay() {
        int numberOfColumns = 1;
        rv_bottom_sheet.setLayoutManager(new GridLayoutManager(sendActivity.this,numberOfColumns ));
        DialogBottomSheetAdapter adapter = new DialogBottomSheetAdapter(sendActivity.this, listFile,dm.widthPixels , list);
        rv_bottom_sheet.setAdapter(adapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public static void listPublic(String path){
        list.add(path);
    }
    public static void listPublicRemove(String path){
        list.remove(path);
    }



    public class loop implements Runnable{
        @Override
        public void run() {
            if (adapterFrag != null){
               a.hide();
               tv_plz.setVisibility(View.GONE);
            }
            handler.postDelayed(this , 300);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 564 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
           // Toast.makeText(this, "ramin", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(sendActivity.this , ConnectToReciever.class ));
        }

    }


    @Override
    public void onBackPressed() {
        List<String> dir = fragment.sharePath();
        if (!dir.get(dir.size() - 1).equals(dir.get(0))){
            fragment.back();
        }else {
            super.onBackPressed();
        }


    }
}
