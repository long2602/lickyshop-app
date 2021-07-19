package com.tlong.test.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tlong.test.R;
import com.tlong.test.model.*;
import com.tlong.test.adapter.*;
import com.tlong.test.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BillActivity extends AppCompatActivity {
    RecyclerView listview;
    ArrayList<bill> billArrayList;
    billAdapter adapter;
    billAdapter.RecyclerViewClickListener listener;
    ImageButton btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        AnhXa();
        EventClick();
        getDulieuBill();
    }

    private void EventClick() {
        //trở về
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getDulieuBill() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.LinkgetBill+"?page="+String.valueOf(MainActivity.IDCUS);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(duongdan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int idbill=0;
                    int idcus=0;
                    String date="",pay="",mes="";
                    double tong=0;
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject  =response.getJSONObject(i);
                            idbill=jsonObject.getInt("IdBill");
                            idcus=jsonObject.getInt("IdCust");
                            date= jsonObject.getString("BDateTime");
                            pay=jsonObject.getString("Payment");
                            mes=jsonObject.getString("Messenger");
                            tong=jsonObject.getDouble("Total");
                            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
                            Date date6=formatter.parse(date);
                            billArrayList.add(new bill(idbill,idcus,date6,tong,pay,mes));
                            adapter.notifyDataSetChanged();
                        }  catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void AnhXa() {
        btnBack=(ImageButton)findViewById(R.id.img_btn_back);
        listview=(RecyclerView)findViewById(R.id.bill_listview);
        billArrayList=new ArrayList<>();
        listener=new billAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        };
        adapter=new billAdapter(getApplicationContext(),billArrayList,listener);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,1);
        listview.setLayoutManager(gridLayoutManager);
        listview.setAdapter(adapter);
    }
}