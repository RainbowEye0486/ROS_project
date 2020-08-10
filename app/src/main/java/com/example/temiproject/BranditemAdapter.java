package com.example.temiproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BranditemAdapter extends RecyclerView.Adapter<BranditemAdapter.MyViewHolder> {


    private Context myContext;
    private List<Branditem> mData;

    public BranditemAdapter(Context myContext, List<Branditem> mData) {
        this.myContext = myContext;
        this.mData = mData;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.brandText.setText(mData.get(position).getTitle());
        holder.brandThumbnail.setImageResource(mData.get(position).getThumbnail());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView brandText;
        ImageView brandThumbnail;
        ImageButton brandButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            brandText = (TextView) itemView.findViewById(R.id.brand_title);
            brandButton = (ImageButton) itemView.findViewById(R.id.brand_add_btn);
            brandThumbnail = (ImageView) itemView.findViewById(R.id.brand_img);

        }
    }
}
