package com.example.ecart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecart.Activity.ProductShowActivity;
import com.example.ecart.ModelClass.ProductsModel;
import com.example.ecart.R;

import java.util.ArrayList;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Productviewholder> {

    Context context;
    ArrayList<ProductsModel> list;


    public ProductAdapter(Context context, ArrayList<ProductsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Productviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_single_card, parent, false);

        return new Productviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Productviewholder holder, int position) {
        ProductsModel model = list.get(position);
        holder.namep.setText(model.getProductName());
        holder.quntyp.setText(model.getProductQuantity());
        holder.pricep.setText("\u20B9" + model.getProductPrice());
        holder.discoutpricep.setText("\u20B9" + model.getProductDiscountPrice());
        holder.discoutp.setText(model.getProductDiscount() + "%");
        holder.descriptionp.setText(model.getProductDescription());
        Glide.with(holder.pimage).load(model.getProductImage()).into(holder.pimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,ProductShowActivity.class);
                i.putExtra("PID",model.getProductUId().toString());
                i.putExtra("PCate",model.getProductCategories().toString());
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Productviewholder extends RecyclerView.ViewHolder {
        TextView namep, discoutp, discoutpricep, pricep, quntyp, descriptionp;
        ImageView pimage;

        public Productviewholder(@NonNull View itemView) {
            super(itemView);
            namep = itemView.findViewById(R.id.product_name_card_id);
            discoutp = itemView.findViewById(R.id.product_discount_card_id);
            discoutpricep = itemView.findViewById(R.id.product_discout_price_card_id);
            pricep = itemView.findViewById(R.id.product_price_card_id);
            pricep.setPaintFlags(pricep.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            quntyp = itemView.findViewById(R.id.product_quantity_card_id);
            pimage = itemView.findViewById(R.id.product_imge_card_id);
            descriptionp = itemView.findViewById(R.id.product_description_card_id);


        }

    }
}
