package com.example.ecart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.ecart.ModelClass.SliderImageModel;
import com.example.ecart.R;
import java.util.ArrayList;

public class SliderImageHomeAdapter extends RecyclerView.Adapter<SliderImageHomeAdapter.sliderImageviwholder> {
    ArrayList<SliderImageModel> list;
    ViewPager2 viewPager;



    public SliderImageHomeAdapter(ArrayList<SliderImageModel> list, ViewPager2 viewPager) {
        this.list = list;
        this.viewPager = viewPager;
    }

    @NonNull
    @Override
    public sliderImageviwholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View  view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sliderimage_home,parent,false);
        return new sliderImageviwholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sliderImageviwholder holder, int position) {

        SliderImageModel sliderImageModel=list.get(position);

        Glide.with(holder.sliderimage).load(sliderImageModel.getSliderImage()).into(holder.sliderimage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class sliderImageviwholder extends RecyclerView.ViewHolder{
        ImageView sliderimage;
        public sliderImageviwholder(@NonNull View itemView) {
            super(itemView);
            sliderimage = itemView.findViewById(R.id.slider_single_card_image_view_id);
        }
    }
}
