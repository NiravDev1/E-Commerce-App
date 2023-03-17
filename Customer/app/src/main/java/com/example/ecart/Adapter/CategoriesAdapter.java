package com.example.ecart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecart.Activity.CategoriesToProductsActivity;
import com.example.ecart.ModelClass.CategoriesModel;
import com.example.ecart.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.Categoreisviewholder> {

        Context context;
        ArrayList<CategoriesModel> list;

    public CategoriesAdapter(Context context) {
        this.context = context;
        list=new ArrayList();
    }

    public void add(CategoriesModel categoriesModel)
    {
        list.add(categoriesModel);
        Collections.shuffle(list);
        notifyDataSetChanged();
    }

    public void  clear()
    {
        list.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Categoreisviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_single_card,parent,false);

        return new Categoreisviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Categoreisviewholder holder, int position) {
            CategoriesModel categoriesModel= list.get(position);
        holder.cataname.setText(categoriesModel.getCategoryName());
        Glide.with(holder.cateimge).load(categoriesModel.getCategoryImage()).into(holder.cateimge);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CategoriesToProductsActivity.class);
                intent.putExtra("CatName",holder.cataname.getText().toString());
                // STOPSHIP: 16-03-2023
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  Categoreisviewholder extends RecyclerView.ViewHolder {

        ImageView cateimge;
        TextView cataname;

        public Categoreisviewholder(@NonNull View itemView) {
            super(itemView);
        cataname=itemView.findViewById(R.id.categories_name_card_id);
        cateimge=itemView.findViewById(R.id.categories_img_card_id);
        }
    }

}
