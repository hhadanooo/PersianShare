package ir.hhadanooo.persianshare.ContentSend.Slider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.hhadanooo.persianshare.ContentSend.AppPicker.MyRecyclerViewAdapter;
import ir.hhadanooo.persianshare.ContentSend.sendActivity;
import ir.hhadanooo.persianshare.R;

public class SlideAppPicker extends Fragment{

    private List<String> pathapp;
    private static List<String> listPathapp;
    private String appName;
    RecyclerView recyclerView;
    Drawable[] icons;
    List<String> name;
    List<String> data;
    List<String> sizeapp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_app, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);






        final Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = Objects.requireNonNull(getActivity()).getPackageManager().queryIntentActivities(intent, 0);


        icons = new Drawable[apps.size()];
        data = new ArrayList<>();
        name = new ArrayList<>();
        pathapp = new ArrayList<>();
        sizeapp = new ArrayList<>();
        listPathapp = new ArrayList<>();
        int index = 0;
        SharedPreferences pref_setting = PreferenceManager.getDefaultSharedPreferences(getContext());

        final boolean HidenApp = pref_setting.getBoolean("Show_default_apps" , true);
        for (int i = 0 ; i < apps.size() ; i++) {
            boolean repeat = false;
            ResolveInfo packageInfo = apps.get(i);


            String packageName = packageInfo.toString();

            packageName = packageName.substring(packageName.indexOf(" ")+1);
            packageName = packageName.substring(0 , packageName.indexOf("/"));
            if (!HidenApp){
                if (packageName.contains("com.android.") ||
                        packageName.contains("com.huawei.")){

                    repeat = true;
                    index++;
                }
            }


            for (int i1 = 0; i1 < data.size() ; i1++){
                if (data.get(i1).equals(packageName)){
                    repeat = true;
                    index++;
                }
            }

            if (!repeat) {

                PackageManager packageManager= getActivity().getApplicationContext().getPackageManager();
                try {
                    appName = (String) packageManager.getApplicationLabel(packageManager
                            .getApplicationInfo(packageName, PackageManager.GET_META_DATA));

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                data.add(packageName);
                name.add(appName);
                pathapp.add(packageInfo.activityInfo.applicationInfo.publicSourceDir);
                File f = new File(packageInfo.activityInfo.applicationInfo.publicSourceDir);

                float sizeByte = (float) f.length();
                sizeByte = sizeByte/1024/1024/1024;
                if (sizeByte < 1.0){
                    sizeByte = sizeByte*1024;
                    if (sizeByte < 1.0){
                        sizeByte = sizeByte*1024;
                        @SuppressLint("DefaultLocale") String size = String.format("%.2f" , sizeByte);
                        sizeapp.add(size+" KB");
                    }else {
                        @SuppressLint("DefaultLocale") String size = String.format("%.2f" , sizeByte);

                        sizeapp.add(size+" MB");
                    }
                }else {
                    @SuppressLint("DefaultLocale") String size = String.format("%.2f" , sizeByte);

                    sizeapp.add(size+" GB");
                }


                final int finalIndex = index;
                final int finalI = i;
                final String finalPackageName = packageName;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            icons[finalI - finalIndex] = getActivity().getPackageManager().getApplicationIcon(finalPackageName);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


                //Drawable icon = getResources().getDrawable(R.drawable.add_friend);

            }


        }

        //Toast.makeText(this, ""+icons[19], Toast.LENGTH_SHORT).show();

        recyclerView = view.findViewById(R.id.rvNumbers);
        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),numberOfColumns ));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(getContext(), name, icons, sizeapp);
        adapter.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(View view, int position) {
                boolean isExists = false;
                if (listPathapp.size() == 0 ){
                    listPathapp.add(pathapp.get(position));
                    sendActivity.listPublic(pathapp.get(position));
                    sendActivity.ex_counter.setText(""+sendActivity.list.size());
                }else {
                    for (int i1 = 0 ; i1 < listPathapp.size() ; i1++){
                        if (listPathapp.get(i1).equals(pathapp.get(position))){
                            listPathapp.remove(i1);
                            sendActivity.listPublicRemove(pathapp.get(position));
                            sendActivity.ex_counter.setText(""+sendActivity.list.size());
                            isExists = true;
                        }
                    }
                    if(!isExists){
                        listPathapp.add(pathapp.get(position));
                        sendActivity.listPublic(pathapp.get(position));
                        sendActivity.ex_counter.setText(""+sendActivity.list.size());
                    }
                }
            }
        });
        recyclerView.setAdapter(adapter);



    }

    public void resetList(){
        listPathapp.clear();
    }




}
