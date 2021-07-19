package com.tlong.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tlong.test.R;
import com.tlong.test.model.*;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class billAdapter extends RecyclerView.Adapter<billAdapter.shirtViewHolder>{
    Context context;
    private List<bill> productList;
    private RecyclerViewClickListener clickListener;
    boolean check=true;
    public billAdapter(Context context, List<bill> productList) {
        this.context = context;
        this.productList = productList;
    }

    public billAdapter(Context context, List<bill> productList, RecyclerViewClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.clickListener=listener;
    }

    @NonNull
    @Override
    public shirtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_bill,parent,false);
        return new shirtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull shirtViewHolder holder, int position) {
        bill product=productList.get(position);
        holder.id.setText("Mã đơn hàng: #"+String.valueOf(product.getIdBill()));
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.gia.setText(decimalFormat.format(product.getTotal())+" VNĐ");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(product.getBDateTime());
        holder.ngay.setText(strDate);
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
        private TextView id,ngay,gia;
        paymentAdapter adapter;
        public shirtViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.itembill_txt_id);
            ngay=itemView.findViewById(R.id.itembill_txt_date);
            gia=itemView.findViewById(R.id.itembill_txt_gia);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view,getAdapterPosition());
        }
    }


}
