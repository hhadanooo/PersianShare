package ir.hhadanooo.persianshare.ContentSend.bottomSheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import ir.hhadanooo.persianshare.R;


public class DialogBottomSheetAdapter extends RecyclerView.Adapter<DialogBottomSheetAdapter.ViewHolderBottomSheet> {


    private List<ModelItemBottomSheet> listFile;
    private Context context;
    private LayoutInflater mInflater;
    private int width;
    private String endName;
    private List<String> list;


    public DialogBottomSheetAdapter(Context context , List<ModelItemBottomSheet> listFile ,
                                    int width , List<String> list){
        this.context = context;
        this.width = width;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.listFile = listFile;
    }


    @NonNull
    @Override
    public ViewHolderBottomSheet onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_bottom_sheet, parent, false);
        return new DialogBottomSheetAdapter.ViewHolderBottomSheet(view);
    }

    @SuppressLint({"SetTextI18n", "ObsoleteSdkInt"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolderBottomSheet holder, int position) {

        ModelItemBottomSheet item = listFile.get(position);
        String setName = item.getFilename().substring( item.getFilename().lastIndexOf("/")+1);

        if (setName.length() > 25){
            endName = setName.substring(setName.length()-10);

            String startName = setName.substring(0 ,10 );

            holder.name_file_show.setText(startName+" ... "+endName);
        }else {
            holder.name_file_show.setText(setName);
        }


        holder.name_file.setText(item.getFilename());
        holder.size_file.setText(item.getFilesize());
        holder.icon_file.getLayoutParams().width = (int) (width*.12);
        holder.icon_file.getLayoutParams().height = (int) (width*.12);
        holder.name_file.setMaxWidth((int) (width*.5));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                (int) (width*.06), (int) (width*.06));
        lp.setMargins((int) (width*.2) , (int) (width*.03) , 0 ,0);

        Glide.with(holder.icon_file).load(item.getIcon_file()).into(holder.icon_file);


        String path = "";
        final String nameFile = holder.name_file.getText().toString();

        for (int i = 0; i < list.size(); i++ ){
            if (list.get(i).endsWith(nameFile)){

                path = list.get(i);

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
        if (nameFile.contains(".") && (nameFile.endsWith(".mp3")
        )) {

            Glide
                    .with(holder.icon_file)
                    .asBitmap()
                    .load(R.drawable.icon_m)
                    .into(holder.icon_file);

        }

        if (nameFile.contains(".") && (nameFile.endsWith(".apk")

        )) {

            Bitmap bmpIcon = null;
            Drawable icon ;

            File file = new File(finalPath);
            String sourcePath = file.getPath();
            String appName = "";

            if (sourcePath.endsWith(".apk")) {
                PackageInfo packageInfo = context.getPackageManager()
                        .getPackageArchiveInfo(sourcePath, PackageManager.GET_ACTIVITIES);
                if(packageInfo != null) {
                    ApplicationInfo appInfo = packageInfo.applicationInfo;
                    if (Build.VERSION.SDK_INT >= 8) {
                        appInfo.sourceDir = sourcePath;
                        appInfo.publicSourceDir = sourcePath;
                    }
                    appName = String.valueOf(appInfo.loadLabel(context.getPackageManager()));
                    icon = appInfo.loadIcon(context.getPackageManager());
                    bmpIcon = ((BitmapDrawable) icon).getBitmap();
                }
            }

            Glide
                    .with(holder.icon_file)
                    .asBitmap()
                    .load(bmpIcon)
                    .into(holder.icon_file);

            holder.name_file_show.setText(appName);


        }



    }

    @Override
    public int getItemCount() {
         return listFile.size();
    }

    public class ViewHolderBottomSheet extends RecyclerView.ViewHolder {

        ImageView icon_file ;
        TextView name_file , size_file , name_file_show;
        RelativeLayout lay;


        ViewHolderBottomSheet(@NonNull View itemView) {
            super(itemView);

            icon_file = itemView.findViewById(R.id.icon_file_bottom_sheet);
            name_file = itemView.findViewById(R.id.name_file_bottom_sheet);
            size_file = itemView.findViewById(R.id.size_file_bottom_sheet);
            name_file_show = itemView.findViewById(R.id.name_file_show_bottom_sheet);
            lay = itemView.findViewById(R.id.lay_bottom_sheet);


        }
    }

}





