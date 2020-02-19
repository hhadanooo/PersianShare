package ir.hhadanooo.persianshare.ContentSend.Slider;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;

import ir.hhadanooo.persianshare.ContentSend.FIleManager.ListAdapterRecycler;
import ir.hhadanooo.persianshare.ContentSend.FIleManager.ModelItem;
import ir.hhadanooo.persianshare.ContentSend.sendActivity;
import ir.hhadanooo.persianshare.R;

public class SlideFileManager extends Fragment implements ListAdapterRecycler.ItemClickListener, View.OnClickListener{

    private List<ModelItem> listFile;
    private RecyclerView rv;
    private List<String> dataList;
    @SuppressLint("StaticFieldLeak")
    private DisplayMetrics dm;
    private List<String>   listAllSelect , listpath   ,dir , docList , docListAdress , appList , appListAddress ,videoList , videoListAddress , musicList , musicListAddress , imgList , imgListAddress , listAddress;
    private String path;
    private boolean show_img = true;
    private boolean show_mp4 = true;
    private boolean show_mp3 = true;
    private boolean show_doc = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_file_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dm = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(dm);

            setHasOptionsMenu(true);


            path = Environment.getExternalStorageDirectory().toString();

            if (ActivityCompat.checkSelfPermission(getActivity() , Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity() , new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE} , 123);
            }
            rv = view.findViewById(R.id.rv);

        RelativeLayout lay_allSelect = view.findViewById(R.id.lay_allSelect);

        ImageView icon_select_all = view.findViewById(R.id.icon_select_all);
        ImageView icon_unselect_all = view.findViewById(R.id.icon_unselect_all);
        RelativeLayout lay_select_all = view.findViewById(R.id.lay_select_all);
        RelativeLayout lay_unselect_all = view.findViewById(R.id.lay_unselect_all);
        Spinner spinner_filter = view.findViewById(R.id.spinner_filter);


        lay_allSelect.getLayoutParams().height = (int)(dm.widthPixels*0.1);

        icon_select_all.getLayoutParams().width = (int)(dm.widthPixels*0.08);
        icon_select_all.getLayoutParams().height = (int)(dm.widthPixels*0.08);

        lay_select_all.getLayoutParams().width = (int)(dm.widthPixels*0.08);
        lay_select_all.getLayoutParams().height = (int)(dm.widthPixels*0.1);

        icon_unselect_all.getLayoutParams().width = (int)(dm.widthPixels*0.08);
        icon_unselect_all.getLayoutParams().height = (int)(dm.widthPixels*0.08);

        lay_unselect_all.getLayoutParams().width = (int)(dm.widthPixels*0.08);
        lay_unselect_all.getLayoutParams().height = (int)(dm.widthPixels*0.1);



       String[] s = {"All File" , "Music" , "Video" , "Image"};


       ArrayAdapter<String> ada = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_dropdown_item ,s );



        spinner_filter.setAdapter(ada);


        spinner_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    imgList.clear();
                    imgListAddress.clear();
                    videoList.clear();
                    videoListAddress.clear();
                    musicList.clear();
                    musicListAddress.clear();
                    appList.clear();
                    appListAddress.clear();
                    docList.clear();
                    docListAdress.clear();
                    rv.removeAllViews();
                    listFile.clear();
                    listpath.clear();
                    listAllSelect.clear();
                    show_img = true;
                    show_mp3 = true;
                    show_mp4 = true;
                    show_doc = true;
                    prepareData();
                    refreshDisplay(dataList);
                }else if(position==1){
                    imgList.clear();
                    imgListAddress.clear();
                    videoList.clear();
                    videoListAddress.clear();
                    musicList.clear();
                    musicListAddress.clear();
                    appList.clear();
                    appListAddress.clear();
                    docList.clear();
                    docListAdress.clear();
                    rv.removeAllViews();
                    listFile.clear();
                    listpath.clear();
                    listAllSelect.clear();
                    show_img = false;
                    show_mp3 = true;
                    show_mp4 = false;
                    show_doc = false;
                    prepareData();
                    refreshDisplay(dataList);
                }else if(position==2){
                    imgList.clear();
                    imgListAddress.clear();
                    videoList.clear();
                    videoListAddress.clear();
                    musicList.clear();
                    musicListAddress.clear();
                    appList.clear();
                    appListAddress.clear();
                    docList.clear();
                    docListAdress.clear();
                    rv.removeAllViews();
                    listFile.clear();
                    listpath.clear();
                    listAllSelect.clear();
                    show_img = false;
                    show_mp3 = false;
                    show_mp4 = true;
                    show_doc = false;
                    prepareData();
                    refreshDisplay(dataList);
                }else if(position==3){
                    imgList.clear();
                    imgListAddress.clear();
                    videoList.clear();
                    videoListAddress.clear();
                    musicList.clear();
                    musicListAddress.clear();
                    appList.clear();
                    appListAddress.clear();
                    docList.clear();
                    docListAdress.clear();
                    rv.removeAllViews();
                    listFile.clear();
                    listpath.clear();
                    listAllSelect.clear();
                    show_img = true;
                    show_mp3 = false;
                    show_mp4 = false;
                    show_doc = false;
                    prepareData();
                    refreshDisplay(dataList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


            videoList = new ArrayList<>();
            videoListAddress = new ArrayList<>();
            musicList = new ArrayList<>();
            musicListAddress = new ArrayList<>();
            imgList = new ArrayList<>();
            imgListAddress = new ArrayList<>();
            listAddress = new ArrayList<>();
            appList = new ArrayList<>();
            appListAddress = new ArrayList<>();
            docList = new ArrayList<>();
            docListAdress = new ArrayList<>();
            dataList = new ArrayList<>();
            listpath = new ArrayList<>();
            listAllSelect = new ArrayList<>();


            dir = new ArrayList<>();
            dir.add(Environment.getExternalStorageDirectory().toString());


            listFile = new ArrayList<>();
            prepareData();
            refreshDisplay( dataList);





            lay_select_all.setOnClickListener(this);
            lay_unselect_all.setOnClickListener(this);



    }
    //class
    private void prepareData() {
        imgList.clear();
        imgListAddress.clear();
        videoList.clear();
        videoListAddress.clear();
        musicList.clear();
        musicListAddress.clear();
        appList.clear();
        appListAddress.clear();
        docList.clear();
        docListAdress.clear();
        Log.i("matiooo", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();

        assert files != null;
        if (files.length != 0){
            for (File file : files) {

                if (file.getName().indexOf(".") == 0 || file.getName().equals("Android") ||
                        file.getName().equals("Databases") || file.getName().equals("Backups") ||
                        file.getName().equals("cache") || file.getName().equals("temp") || file.getName().equals("tmp"))continue;
                Log.i("matiooo", "FileName:" + file.getName());
                String format = file.getName();
                //Toast.makeText(this, ""+file.getName(), Toast.LENGTH_SHORT).show();

                if (file.isFile()){
                    if (format.contains(".")){

                        if(format.endsWith(".mp4") || format.endsWith(".MP4") || format.endsWith(".mkv") ||
                                format.endsWith(".MKV") || format.endsWith(".3gp")|| format.endsWith(".acc")||
                                format.endsWith(".ACC")){

                            if (show_mp4) {
                                checkForNoRepeat(videoList, file.getName());
                                checkForNoRepeat(videoListAddress, file.getAbsolutePath());
                            }
                        }else if (format.endsWith(".jpg") || format.endsWith(".png") || format.endsWith(".PNG") ||
                                format.endsWith(".jpeg") || format.endsWith(".JPG") || format.endsWith(".JPEG") ||
                                format.endsWith(".bmp") || format.endsWith(".webp")|| format.endsWith(".TIFF")||
                                format.endsWith(".tif")){


                            if (show_img) {
                                checkForNoRepeat(imgList, file.getName());
                                checkForNoRepeat(imgListAddress, file.getAbsolutePath());
                            }


                        }else if (format.endsWith(".mp3") || format.endsWith(".ogg") || format.endsWith(".m4a")||
                                format.endsWith(".egg")){


                            if (show_mp3) {
                                checkForNoRepeat(musicList, file.getName());
                                checkForNoRepeat(musicListAddress, file.getAbsolutePath());
                            }


                        }else if (format.endsWith(".apk")){


                            if (show_doc) {
                                checkForNoRepeat(appList, file.getName());
                                checkForNoRepeat(appListAddress, file.getAbsolutePath());
                            }


                        }else {

                            if (show_doc) {
                                checkForNoRepeat(docList, file.getName());
                                checkForNoRepeat(docListAdress, file.getAbsolutePath());
                            }
                        }

                    }else {

                        if (show_doc) {
                            checkForNoRepeat(docList, file.getName());
                            checkForNoRepeat(docListAdress, file.getAbsolutePath());
                        }
                    }
                }else {
                    listFile.add(new ModelItem(file.getName(), "" ,  R.drawable.explorer_icon, R.drawable.unselect));
                    checkForNoRepeat(listAddress , file.getAbsolutePath());
                    checkForNoRepeat(listpath , file.getAbsolutePath());
                    checkForNoRepeat(listAllSelect , file.getAbsolutePath());
                }





            }
            //Toast.makeText(this, ""+listAddress.size(), Toast.LENGTH_SHORT).show();
            if (videoList.size() > 0 || show_mp4){
                for (int i = 0 ; i < videoList.size() ; i++){

                    File file = new File(videoListAddress.get(i));

                    String sizeapp;
                    float sizeByte = (float) file.length();
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

                    listFile.add(new ModelItem(videoList.get(i) , sizeapp , R.drawable.film_icon , R.drawable.unselect));
                    checkForNoRepeat(listAddress , videoListAddress.get(i));
                    checkForNoRepeat(listpath , videoListAddress.get(i));
                    checkForNoRepeat(listAllSelect , videoListAddress.get(i));
                }
            }



            if (imgList.size() > 0 || show_img){

                for (int i = 0 ; i < imgList.size() ; i++){
                    File file = new File(imgListAddress.get(i));
                    String sizeapp;
                    float sizeByte = (float) file.length();
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
                    listFile.add(new ModelItem(imgList.get(i) , sizeapp , R.drawable.image_icon , R.drawable.unselect));
                    checkForNoRepeat(listAddress , imgListAddress.get(i));
                    checkForNoRepeat(listpath , imgListAddress.get(i));
                    checkForNoRepeat(listAllSelect , imgListAddress.get(i));
                }
            }
            if (musicList.size() > 0 || show_mp3){
                for (int i = 0 ; i < musicList.size() ; i++){

                    File file = new File(musicListAddress.get(i));
                    String sizeapp;
                    float sizeByte = (float) file.length();
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

                    listFile.add(new ModelItem(musicList.get(i) , sizeapp , R.drawable.icon_m , R.drawable.unselect));
                    checkForNoRepeat(listAddress , musicListAddress.get(i));
                    checkForNoRepeat(listpath , musicListAddress.get(i));
                    checkForNoRepeat(listAllSelect , musicListAddress.get(i));
                }
            }

            if (docList.size() > 0 || show_doc){
                for (int i = 0 ; i < docList.size() ; i++){

                    File file = new File(docListAdress.get(i));
                    String sizeapp;
                    float sizeByte = (float) file.length();
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

                    listFile.add(new ModelItem(docList.get(i) , sizeapp , R.drawable.doc_icon2 , R.drawable.unselect));
                    checkForNoRepeat(listAddress , docListAdress.get(i));
                    checkForNoRepeat(listpath , docListAdress.get(i));
                    checkForNoRepeat(listAllSelect , docListAdress.get(i));
                }
            }
            if (appList.size() > 0 || show_doc){
                for (int i = 0 ; i < appList.size() ; i++){

                    File file = new File(appListAddress.get(i));
                    String sizeapp;
                    float sizeByte = (float) file.length();
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

                    listFile.add(new ModelItem(appList.get(i) , sizeapp , R.drawable.apk_icon , R.drawable.unselect));
                    checkForNoRepeat(listAddress , appListAddress.get(i));
                    checkForNoRepeat(listpath , appListAddress.get(i));
                    checkForNoRepeat(listAllSelect , appListAddress.get(i));
                }
            }
        }else {
            Toast.makeText(getContext(), "folder is empty", Toast.LENGTH_SHORT).show();
        }




    }

    private void refreshDisplay( List<String> dl) {

        int numberOfColumns = 1;
        rv.setLayoutManager(new GridLayoutManager(getContext(),numberOfColumns ));
        ListAdapterRecycler adapter = new ListAdapterRecycler(getContext(), listFile, dl, listAllSelect, dm.widthPixels);
        adapter.setClickListener(this);
        rv.setAdapter(adapter);

    }










    private void checkForNoRepeat(List<String> ls, String name){
        boolean repeat = false;

        for (int i = 0 ; i < ls.size() ; i++){
            if (ls.get(i).equals(name)){
                repeat = true;
            }
        }
        if (!repeat){
            ls.add(name);
        }
    }



    public void back(){
        path = dir.get(dir.size() - 2);
        dir.remove(dir.get(dir.size() - 1));
        imgList.clear();
        imgListAddress.clear();
        videoList.clear();
        videoListAddress.clear();
        musicList.clear();
        musicListAddress.clear();
        appList.clear();
        appListAddress.clear();
        docList.clear();
        docListAdress.clear();
        rv.removeAllViews();
        listFile.clear();
        listpath.clear();
        listAllSelect.clear();
        prepareData();
        refreshDisplay(dataList);
    }
    public List<String> sharePath(){
        return dir;
    }

    @Override
    public void onItemClick(View view, int position){


            //Toast.makeText(FileActivity.this, ""+listpath.get(position), Toast.LENGTH_SHORT).show();
            String pas = listpath.get(position);
            File fileOrFolder  = new File(pas);

            if (fileOrFolder.isFile()) {

                String format = listpath.get(position);
                //Toast.makeText(FileActivity.this, ""+listpath.get(position), Toast.LENGTH_SHORT).show();
                if (format.contains(".")) {
                        //Toast.makeText(this, ""+format, Toast.LENGTH_SHORT).show();


                    if(format.endsWith("mp4") || format.endsWith("MP4") || format.endsWith("mkv") ||
                            format.endsWith("MKV") || format.endsWith("3gp")){
                        File file1 = new File(listpath.get(position));
                        final Intent intent1 = new Intent(Intent.ACTION_VIEW)//
                                .setDataAndType(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ?
                                                FileProvider.getUriForFile(Objects.requireNonNull(getContext()), Objects.requireNonNull(getActivity()).getPackageName() + ".provider", file1) : Uri.fromFile(file1),
                                        "video/*").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent1);
                    }else if (format.endsWith("jpg") || format.endsWith("png") || format.endsWith("PNG") ||
                            format.endsWith("jpeg") || format.endsWith("JPG") || format.endsWith("JPEG") ||
                            format.endsWith("bmp") || format.endsWith("webp")|| format.endsWith("tiff")||
                            format.endsWith("TIFF")){

                        File file = new File(listpath.get(position));
                        final Intent intent = new Intent(Intent.ACTION_VIEW)//
                                .setDataAndType(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ?
                                                FileProvider.getUriForFile(Objects.requireNonNull(getContext()), Objects.requireNonNull(getActivity()).getPackageName() + ".provider", file) : Uri.fromFile(file),
                                        "image/*").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);


                    }else if (format.endsWith("mp3")){

                        File file2 = new File(listpath.get(position));
                        final Intent intent2 = new Intent(Intent.ACTION_VIEW)//
                                .setDataAndType(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ?
                                                FileProvider.getUriForFile(Objects.requireNonNull(getContext()), Objects.requireNonNull(getActivity()).getPackageName() + ".provider", file2) : Uri.fromFile(file2),
                                        "audio/*").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent2);



                    }else{

                        Toast.makeText(getContext(), "not supported", Toast.LENGTH_SHORT).show();


                    }


                }else {
                    Toast.makeText(getContext(), "not supported", Toast.LENGTH_SHORT).show();
                }


            } else {

                //Toast.makeText(FileActivity.this, "a : "+convertNameToPath(adapter.saveData()), Toast.LENGTH_SHORT).show();
                path = listpath.get(position);
                File directory = new File(path);
                File[] files = directory.listFiles();
                assert files != null;
                if (files.length != 0) {
                    if (!path.equals(dir.get(0))) {

                        dir.add(path);

                        //Toast.makeText(FileActivity.this, ""+dir.get(1), Toast.LENGTH_SHORT).show();
                    }
                    imgList.clear();
                    imgListAddress.clear();
                    videoList.clear();
                    videoListAddress.clear();
                    musicList.clear();
                    musicListAddress.clear();
                    appList.clear();
                    appListAddress.clear();
                    docList.clear();
                    docListAdress.clear();
                    rv.removeAllViews();
                    listFile.clear();
                    listpath.clear();
                    listAllSelect.clear();
                    prepareData();
                    refreshDisplay(dataList);

                    //Toast.makeText(FileActivity.this, ""+listAllSelect.size(), Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getContext(), "folder is empty", Toast.LENGTH_SHORT).show();
                }


            }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lay_select_all){



            imgList.clear();
            imgListAddress.clear();
            videoList.clear();
            videoListAddress.clear();
            musicList.clear();
            musicListAddress.clear();
            appList.clear();
            appListAddress.clear();
            docList.clear();
            docListAdress.clear();
            rv.removeAllViews();
            listFile.clear();
            listpath.clear();



            for (int i = 0 ; i < listAllSelect.size() ; i++) {


                File fileOrFol = new File(listAllSelect.get(i));

                if (fileOrFol.isFile()) {

                    if (dataList.size() == 0) {
                        dataList.add(listAllSelect.get(i));
                        sendActivity.listPublic(listAllSelect.get(i));
                        sendActivity.ex_counter.setText(""+sendActivity.list.size());

                    } else {

                        for (int i1 = 0; i1 < dataList.size(); i1++) {
                            if (dataList.get(i1).equals(listAllSelect.get(i))) {
                                dataList.remove(i1);
                                sendActivity.listPublicRemove(listAllSelect.get(i));
                                sendActivity.ex_counter.setText(""+sendActivity.list.size());



                            }
                        }

                        dataList.add(listAllSelect.get(i));
                        sendActivity.listPublic(listAllSelect.get(i));
                        sendActivity.ex_counter.setText(""+sendActivity.list.size());

                    }
                }


            }

            refreshDisplay(dataList);
            prepareData();

        }else if (v.getId() == R.id.lay_unselect_all){


            imgList.clear();
            imgListAddress.clear();
            videoList.clear();
            videoListAddress.clear();
            musicList.clear();
            musicListAddress.clear();
            appList.clear();
            appListAddress.clear();
            docList.clear();
            docListAdress.clear();
            rv.removeAllViews();
            listFile.clear();
            listpath.clear();
            for (int i = 0 ; i < listAllSelect.size() ; i++) {

                for (int i1 = 0; i1 < dataList.size(); i1++) {
                    if (dataList.get(i1).equals(listAllSelect.get(i))) {
                        dataList.remove(i1);
                        sendActivity.listPublicRemove(listAllSelect.get(i));
                        sendActivity.ex_counter.setText(""+sendActivity.list.size());

                    }
                }
            }


            prepareData();
            refreshDisplay( dataList);

        }
    }


}
