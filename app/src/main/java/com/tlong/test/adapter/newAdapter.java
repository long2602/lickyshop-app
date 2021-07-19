package com.tlong.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tlong.test.R;
import com.tlong.test.model.image;
import com.tlong.test.model.product;

import java.text.DecimalFormat;
import java.util.List;

public class newAdapter extends RecyclerView.Adapter<newAdapter.shirtViewHolder>{
    Context context;
    private List<product> productList;
    private List<image> imageList;
    private RecyclerViewClickListener clickListener;

    public newAdapter(Context context, List<product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public newAdapter(Context context, List<product> productList, List<image> imageList, RecyclerViewClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.imageList = imageList;
        this.clickListener=listener;
    }

    @NonNull
    @Override
    public shirtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_newproduct,parent,false);
        return new shirtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull shirtViewHolder holder, int position) {
        product product=productList.get(position);
        image image=imageList.get(position);
        String txt=String.valueOf(product.getPrice());
        holder.name.setText(product.getName());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.gia.setText(decimalFormat.format(product.getPrice())+" VNĐ");
        Picasso.get().load(image.getImage()).placeholder(R.drawable.longng).error(R.drawable.vu).into(holder.img);

    }

    @Override
    public int getItemCount() {
        if(productList!=null)
            return productList.size();
        return 0;
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    public class shirtViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView img;
        private TextView name;
        private TextView gia;
        public shirtViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.items_new_imgView_item);
            name=itemView.findViewById(R.id.items_new_txtView_name);
            gia=itemView.findViewById(R.id.items_new_txtView_gia);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view,getAdapterPosition());
        }
    }


}
