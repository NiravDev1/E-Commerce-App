package com.example.ecartadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecartadmin.Model.CategoriesModel;
import com.example.ecartadmin.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewholder> {

    Context context;
    ArrayList<CategoriesModel> arrayList;

    public CategoryAdapter(Context context, ArrayList<CategoriesModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

//    public void add(CategoriesModel categoriesModel) {
//        arrayList.add(categoriesModel);
//        notifyDataSetChanged();
//    }
//
//    public void clear() {
//        arrayList.clear();
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public CategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_single_card, parent, false);
        return new CategoryViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewholder holder, int position) {
        CategoriesModel categoriesModel = arrayList.get(position);
        holder.catname.setText(categoriesModel.getCategoryName());
        Glide.with(holder.cateimg).load(categoriesModel.getCategoryImage()).into(holder.cateimg);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CategoryViewholder extends RecyclerView.ViewHolder {
        ImageView cateimg;
        TextView catname;

        public CategoryViewholder(@NonNull View itemView) {
            super(itemView);
            cateimg = itemView.findViewById(R.id.categoies_image_card_id);
            catname = itemView.findViewById(R.id.categoies_name_card_id);
        }
    }
}
