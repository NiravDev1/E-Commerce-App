package com.example.ecart.Adapter;

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
    public void onBindViewHolder(@NonNull CartViewholder holder, int position) {
        CartModel cartModel = list.get(position);
        holder.pname.setText(cartModel.getProductName());
        holder.pqunty.setText(cartModel.getProductQuantity());
        holder.plprice.setText(cartModel.getProductSprice());
        holder.pprice.setText(cartModel.getProductPrice());
        holder.pdiscoutprice.setText(cartModel.getProductDiscountPrice());
        Glide.with(holder.pimge).load(cartModel.getProductImage()).into(holder.pimge);

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cout = Integer.parseInt(holder.pqunty.getText().toString());
                int price= Integer.parseInt(holder.pdiscoutprice.getText().toString());
                if (cout != 1) {
                    cout--;
                    holder.pqunty.setText(String.valueOf(cout));
                    int c= Integer.parseInt(holder.pqunty.getText().toString());
                    int tp=price*c;
                    holder.plprice.setText(String.valueOf(tp));

                }
                else
                {
                    holder.plprice.setText(cartModel.getProductDiscountPrice());
                }


            }
        });
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cout= Integer.parseInt(holder.pqunty.getText().toString());
                int price= Integer.parseInt(holder.pdiscoutprice.getText().toString());
                cout++;
                holder.pqunty.setText(String.valueOf(cout));
                int c= Integer.parseInt(holder.pqunty.getText().toString());
                int tp=price*c;
                System.out.println(price);
                System.out.println(c);
                System.out.println(tp);
                holder.plprice.setText(String.valueOf(tp));
            }
        });
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
        Button increment, decrement,deteleitemBtn;

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
            increment = itemView.findViewById(R.id.cart_incrementButton_card_id);
            decrement = itemView.findViewById(R.id.cart_decrementButton_card_btn);
        }
    }
}
