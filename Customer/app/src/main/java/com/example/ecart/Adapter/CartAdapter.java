package com.example.ecart.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.net.wifi.aware.IdentityChangedListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.ecart.ModelClass.CartModel;
import com.example.ecart.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewholder> {

    Context context;
    ArrayList<CartModel> list;

    public CartAdapter(Context context, ArrayList<CartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CartViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_sinlge_card, parent, false);
        return new CartViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewholder holder, @SuppressLint("RecyclerView") int position) {
        CartModel cartModel = list.get(position);
        holder.pname.setText(cartModel.getProductName());
        holder.pqunty.setText(cartModel.getProductQuantity());
        holder.plprice.setText(cartModel.getProductSprice());
        holder.pprice.setText(cartModel.getProductPrice());
        holder.pdiscoutprice.setText(cartModel.getProductDiscountPrice());
        Glide.with(holder.pimge).load(cartModel.getProductImage()).into(holder.pimge);


        holder.deteleitemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("CART");
                reference.child(cartModel.getCustomerUID()).child(cartModel.getProductUId()).removeValue();
                notifyDataSetChanged();
                list.remove(position);
                list.clear();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CartViewholder extends RecyclerView.ViewHolder {
        TextView pprice, pdiscoutprice, plprice, pqunty, pname;
        ImageView pimge;
        Button deteleitemBtn;

        public CartViewholder(@NonNull View itemView) {
            super(itemView);

            pname = itemView.findViewById(R.id.cart_pname_card_id);
            pprice = itemView.findViewById(R.id.cart_price_card_id);
            pprice.setPaintFlags(pprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            plprice = itemView.findViewById(R.id.cart_lprice_card_id);
            pimge = itemView.findViewById(R.id.cart_pimage_card_id);
            pdiscoutprice = itemView.findViewById(R.id.cart_pdiscout_price_card_id);
            pqunty = itemView.findViewById(R.id.cart_card_quantity_textview);

            deteleitemBtn=itemView.findViewById(R.id.delete_item_BTN);

        }
    }
}
