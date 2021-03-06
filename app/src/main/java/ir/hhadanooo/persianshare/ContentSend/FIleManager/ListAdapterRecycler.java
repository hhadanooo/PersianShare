package ir.hhadanooo.persianshare.ContentSend.FIleManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.hhadanooo.persianshare.ContentSend.sendActivity;
import ir.hhadanooo.persianshare.R;

public class ListAdapterRecycler extends RecyclerView.Adapter<ListAdapterRecycler.ViewHolder> {

    private List<ModelItem> listFile;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    private List<String> selectList;
    private List<String> dataList;
    private List<String> listAddressdata;
    private boolean isExists = false;
    private int width;
    private String endName;





    // data is passed into the constructor
    public ListAdapterRecycler(Context context, List<ModelItem> listFile,
                               List<String> dataList, List<String> listAddressdata, int width) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.listFile = listFile;
        this.dataList = dataList;
        this.listAddressdata = listAddressdata;
        this.width = width;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.myfile_list_item, parent, false);
        return new ViewHolder(view);
    }



    // binds the data to the TextView in each cell
    @SuppressLint({"ObsoleteSdkInt", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        ModelItem item = listFile.get(position);
        String setName = item.getFilename();

        if (setName.length() > 25){
            endName = setName.substring(setName.length()-10);

            String startName = setName.substring(0 ,10 );

            holder.name_file_show.setText(startName+" ... "+endName);
        }else {
            holder.name_file_show.setText(setName);
        }



        holder.name_file.setText(item.getFilename());
        holder.size_file.setText(item.getFilesize());
        Glide.with(holder.icon_select).load(item.getIcon_select()).into(holder.icon_select);
        holder.icon_file.getLayoutParams().width = (int) (width*.12);
        holder.icon_file.getLayoutParams().height = (int) (width*.12);
        holder.name_file.setMaxWidth((int) (width*.5));

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                (int) (width*.06), (int) (width*.06));
        lp.setMargins((int) (width*.21) , (int) (width*.035) , 0 ,0);
        holder.icon_select.setLayoutParams(lp);
        holder.lay_select.getLayoutParams().width = (int) (width*.3);
        holder.lay_select.getLayoutParams().height = (int) (width*.13 );

        Glide.with(holder.icon_file).load(item.getIcon_file()).into(holder.icon_file);
        selectList = new ArrayList<>();

        SharedPreferences pref_setting = PreferenceManager.getDefaultSharedPreferences(context);

        final boolean vibration = pref_setting.getBoolean("vibration" , true);

        holder.lay_select.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                selectList = dataList;
                isExists =false;
                if (selectList == null){
                    selectList = new ArrayList<>();
                }

                if (vibration){
                    Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                        Objects.requireNonNull(vib).vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        //deprecated in API 26
                        Objects.requireNonNull(vib).vibrate(100);
                    }
                }



                String path = "";

                for (int i = 0; i < listAddressdata.size(); i++ ){
                    if (listAddressdata.get(i).endsWith(holder.name_file.getText().toString())){
                        path = listAddressdata.get(i);
                    }
                }
                //Toast.makeText(activity, "5"+path, Toast.LENGTH_SHORT).show();

                File fileOrFol = new File(path);

                if (fileOrFol.isFile()) {
                    if (!path.equals("")) {

                        if (selectList.size() == 0) {
                            selectList.add(path);
                            sendActivity.listPublic(path);
                            sendActivity.ex_counter.setText(""+sendActivity.list.size());
                            //Toast.makeText(context, "add \n" + path, Toast.LENGTH_SHORT).show();
                            Glide.with(holder.icon_select).load(R.drawable.select).into(holder.icon_select);
                        } else {

                            //Toast.makeText(activity, "for", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < selectList.size(); i++) {
                                if (selectList.get(i).equals(path)) {
                                    //Toast.makeText(context, "removed " + path, Toast.LENGTH_SHORT).show();
                                    selectList.remove(i);
                                    sendActivity.listPublicRemove(path);
                                    sendActivity.ex_counter.setText(""+sendActivity.list.size());
                                    Glide.with(holder.icon_select).load(R.drawable.unselect).into(holder.icon_select);
                                    isExists = true;
                                }
                            }
                            if (!isExists) {
                                selectList.add(path);
                                sendActivity.listPublic(path);
                                sendActivity.ex_counter.setText(""+sendActivity.list.size());
                                //Toast.makeText(context, "add \n" + path, Toast.LENGTH_SHORT).show();
                                Glide.with(holder.icon_select).load(R.drawable.select).into(holder.icon_select);
                            }

                        }
                    }


                }else {
                    Toast.makeText(context, "folder not be select", Toast.LENGTH_SHORT).show();
                }


            }
        });


        //Toast.makeText(activity, ""+selectList, Toast.LENGTH_SHORT).show();









        String path = "";
        final String nameFile = holder.name_file.getText().toString();

        for (int i = 0; i < listAddressdata.size(); i++ ){
            if (listAddressdata.get(i).endsWith(nameFile)){

                path = listAddressdata.get(i);

            }
        }
        //Toast.makeText(activity, "12" +convertView. , Toast.LENGTH_LONG).show();

        final String finalPath = path;


        if (nameFile.contains(".") && (nameFile.endsWith(".jpg") ||
                nameFile.endsWith(".JPG") ||
                nameFile.endsWith(".png") ||
                nameFile.endsWith(".PNG") ||
                nameFile.endsWith(".webp") ||
                nameFile.endsWith(".jpeg") ||
                nameFile.endsWith(".JPEG")
        )) {

            Glide.with(holder.icon_file).load(finalPath).into(holder.icon_file);


        }


        if (nameFile.contains(".") && (nameFile.endsWith(".mp4") ||
                nameFile.substring(nameFile.lastIndexOf(".")).equals(".mkv")
        )) {

            Glide
                    .with(holder.icon_file)
                    .asBitmap()
                    .load(Uri.fromFile(new File(finalPath)))
                    .into(holder.icon_file);


        }



        if (nameFile.contains(".") && (nameFile.endsWith(".apk")

        )) {

            Bitmap bmpIcon = null;
            Drawable icon ;

            File file = new File(finalPath);
            String sourcePath = file.getPath();

            if (sourcePath.endsWith(".apk")) {
                PackageInfo packageInfo = context.getPackageManager()
                        .getPackageArchiveInfo(sourcePath, PackageManager.GET_ACTIVITIES);
                if(packageInfo != null) {
                    ApplicationInfo appInfo = packageInfo.applicationInfo;
                    if (Build.VERSION.SDK_INT >= 8) {
                        appInfo.sourceDir = sourcePath;
                        appInfo.publicSourceDir = sourcePath;
                    }
                    icon = appInfo.loadIcon(context.getPackageManager());
                    bmpIcon = ((BitmapDrawable) icon).getBitmap();
                }
            }
            
            Glide
                    .with(holder.icon_file)
                    .asBitmap()
                    .load(bmpIcon)
                    .into(holder.icon_file);


        }


        //Toast.makeText(context, ""+dataList, Toast.LENGTH_SHORT).show();

        selectList = dataList;
        if (selectList != null) {
            if (selectList.size() != 0) {
                for (int i = 0; i < selectList.size(); i++) {
                    if (selectList.get(i).equals(finalPath)) {


                        Glide.with(holder.icon_select).load(R.drawable.select).into(holder.icon_select);

                    }
                }
            }
        }


    }


    // total number of cells
    @Override
    public int getItemCount() {
        return listFile.size();
    }




    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icon_file , icon_select;
        TextView name_file , size_file , name_file_show;
        RelativeLayout lay , lay_select;


        ViewHolder(View itemView) {
            super(itemView);
            lay_select = itemView.findViewById(R.id.lay_select);
            icon_file = itemView.findViewById(R.id.icon_file);
            icon_select = itemView.findViewById(R.id.select);
            name_file = itemView.findViewById(R.id.name_file);
            size_file = itemView.findViewById(R.id.size_file);
            name_file_show = itemView.findViewById(R.id.name_file_show);
            lay = itemView.findViewById(R.id.lay);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null){

                mClickListener.onItemClick(view, getAdapterPosition());

                //Toast.makeText(context, "12"+listTick, Toast.LENGTH_SHORT).show();
            }
        }
    }

    // convenience method for getting data at click position
    /*String getItem(int id) {
        return mData.get(id);
    }*/

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
