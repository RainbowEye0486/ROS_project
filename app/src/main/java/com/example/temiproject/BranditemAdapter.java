package com.example.temiproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BranditemAdapter extends RecyclerView.Adapter<BranditemAdapter.MyViewHolder> {

    private String TAG = "BranditemAdapter";
    private Context myContext;
    private List<Branditem> mData;
    private List<Beacon> mbeacon;
    private BrandSearchActivity brandSearchActivity;

    public BranditemAdapter(Context myContext, BrandSearchActivity brandSearchActivity,List<Branditem> mData, List<Beacon> mbeacon) {
        this.myContext = myContext;
        this.mData = mData;
        this.mbeacon = mbeacon;
        this.brandSearchActivity = brandSearchActivity;
        Log.d(TAG, "BranditemAdapter: "+mbeacon);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(myContext);
        view = mInflater.inflate(R.layout.brand_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.brandText.setText(mData.get(position).getTitle());
        holder.brandThumbnail.setImageResource(mData.get(position).getThumbnail());
        holder.brandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempTitle = mData.get(position).getTitle();
                int textSize = 40;
                if (tempTitle.length() > 8){
                    textSize = 25;
                }
                boolean repeat = false;
                for (int o=0;o<mbeacon.size();o++) {
                    if (tempTitle.equals(mbeacon.get(o).title)){
                        repeat = true;
                    }
                }
                if (!repeat && mbeacon.size()<=5){
                    Log.d(TAG, "onClick: add new beacon " + tempTitle);
                    mbeacon.add(new Beacon(tempTitle, textSize));
                    brandSearchActivity.flushBeacon();
                }
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: No." + mData.get(position).getTitle() + " button");

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView brandText;
        ImageView brandThumbnail;
        ImageButton brandButton;

        CardView cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            brandText = (TextView) itemView.findViewById(R.id.brand_title);
            brandButton = (ImageButton) itemView.findViewById(R.id.brand_add_btn);
            brandThumbnail = (ImageView) itemView.findViewById(R.id.brand_img);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }



}
