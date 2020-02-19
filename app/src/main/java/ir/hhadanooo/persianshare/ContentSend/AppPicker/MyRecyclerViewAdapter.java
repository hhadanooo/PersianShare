package ir.hhadanooo.persianshare.ContentSend.AppPicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

import ir.hhadanooo.persianshare.ContentSend.sendActivity;
import ir.hhadanooo.persianshare.R;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private List<String> sizeapp;
    private List<String> listTick = new ArrayList<>();
    private Drawable[] da;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    public MyRecyclerViewAdapter(Context context,
                                 List<String> data, Drawable[] da, List<String> sizeapp) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.da = da;
        this.sizeapp = sizeapp;
    }


    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.myTextView.setText(mData.get(position));
        //holder.image_app.setImageDrawable(da[position]);
        Glide.with(holder.image_app).load(da[position]).into(holder.image_app);
        holder.image_app.getLayoutParams().width = (int)(sendActivity.dm.widthPixels*0.16);
        holder.image_app.getLayoutParams().height = (int)(sendActivity.dm.widthPixels*0.16);


        holder.lay_appPicker_item.getLayoutParams().width = (int)(sendActivity.dm.widthPixels*0.2);
        holder.lay_appPicker_item.getLayoutParams().height = (int)(sendActivity.dm.widthPixels*0.2);

        holder.image_tick.getLayoutParams().width = (int)(sendActivity.dm.widthPixels*0.05);
        holder.image_tick.getLayoutParams().height = (int)(sendActivity.dm.widthPixels*0.05);


        holder.info_size.setText(sizeapp.get(position));
        for (int i = 0 ; i < listTick.size() ; i++){
            if (!listTick.get(i).equals(holder.myTextView.getText().toString())){
                //Toast.makeText(context, "11 "+holder.myTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                holder.image_tick.setVisibility(View.GONE);
            }
        }
        for (int i = 0 ; i < listTick.size() ; i++){
            if (listTick.get(i).equals(holder.myTextView.getText().toString())){
               // Toast.makeText(context, "11 "+holder.myTextView.getText().toString(), Toast.LENGTH_SHORT).show();
                holder.image_tick.setVisibility(View.VISIBLE);
            }
        }



    }



    @Override
    public int getItemCount() {
        return mData.size();
    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView info_size;
        ImageView image_app;
        ImageView image_tick;
        RelativeLayout lay_appPicker_item;


        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.info_text);
            image_app = itemView.findViewById(R.id.image_app);
            image_tick = itemView.findViewById(R.id.image_tick);
            info_size = itemView.findViewById(R.id.info_size);
            lay_appPicker_item = itemView.findViewById(R.id.lay_appPicker_item);


            itemView.setOnClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View view) {
            if (mClickListener != null){

                mClickListener.onItemClick(view, getAdapterPosition());

                boolean isExists = false;
                if (listTick.size() == 0 ){
                    listTick.add(myTextView.getText().toString());
                    sendActivity.ex_counter.setText(""+sendActivity.list.size());
                    image_tick.setVisibility(View.VISIBLE);
                }else {
                    for (int i1 = 0 ; i1 < listTick.size() ; i1++){
                        if (listTick.get(i1).equals(myTextView.getText().toString())){
                            listTick.remove(i1);
                            image_tick.setVisibility(View.GONE);
                            sendActivity.ex_counter.setText(""+sendActivity.list.size());
                            isExists = true;
                        }
                    }
                    if(!isExists){
                        listTick.add(myTextView.getText().toString());
                        sendActivity.ex_counter.setText(""+sendActivity.list.size());
                        image_tick.setVisibility(View.VISIBLE);
                    }
                }

                //Toast.makeText(context, "12"+listTick, Toast.LENGTH_SHORT).show();
            }
        }
    }

 /*   // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

*/

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
