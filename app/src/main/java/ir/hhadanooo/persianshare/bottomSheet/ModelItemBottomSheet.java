package ir.hhadanooo.persianshare.bottomSheet;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class ModelItemBottomSheet {

    private String filename , filesize;
    private int icon_select , icon_file;
    private Bitmap bit;




    public ModelItemBottomSheet(String filename , String filesize ,
                                Bitmap icon_file , int icon_select) {
        this.filename = filename;
        this.filesize = filesize;
        this.bit = icon_file;
        this.icon_select = icon_select;
    }

    public ModelItemBottomSheet(String filename , String filesize ,
                                int icon_file , int icon_select  ) {
        this.filename = filename;
        this.filesize = filesize;
        this.icon_file = icon_file;
        this.icon_select = icon_select;


    }

    public int getIcon_file() {
        return icon_file;
    }

    public void setIcon_file(int icon_file) {
        this.icon_file = icon_file;
    }

    public Bitmap getBit() {
        return bit;
    }

    public void setBit(Bitmap bit) {
        this.bit = bit;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }


    public int getIcon_select() {
        return icon_select;
    }

    public void setIcon_select(int icon_select) {
        this.icon_select = icon_select;
    }


    @NonNull
    public String toString(){
        return filename;
    }
}
