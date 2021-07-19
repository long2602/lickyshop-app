package com.tlong.test.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tlong.test.R;
import com.tlong.test.adapter.adaccountAdapter;
import com.tlong.test.adapter.adbillAdapter;
import com.tlong.test.model.*;
import com.tlong.test.ultil.CheckConnect;
import com.tlong.test.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class BillFragment extends Fragment {
    ListView listView;
    ArrayList<customer> customers;
    ArrayList<bill> bills;
    adbillAdapter adapter;
    public BillFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bill, container, false);
        listView=view.findViewById(R.id.fragBill_listview);
        customers=new ArrayList<>();
        bills=new ArrayList<>();
        getDuLieuLoaiSanPham();
        adapter=new adbillAdapter(getContext(),bills,customers);
        listView.setAdapter(adapter);
        return view;
    }
    private void getDuLieuLoaiSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.LinkGetAdBill, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                if(response != null){
                    int idcus=0,idbill=0;
                    String name="",sex="",mail="",phone="",payment="",mess="";
                    double total=0;
                    String dob,date;
                    for(int i = 0;i < response.length();i++){
                        try {
                            JSONObject jsonObject =response.getJSONObject(i);
                            idcus=jsonObject.getInt("IdCus");
                            idbill=jsonObject.getInt("IdBill");
                            name=jsonObject.getString("FullName");
                            sex=jsonObject.getString("Gender");
                            mail=jsonObject.getString("Email");
                            phone=jsonObject.getString("Phone");
                            payment=jsonObject.getString("Payment");
                            mess=jsonObject.getString("Messenger");
                            total=jsonObject.getDouble("Total");
                            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
                            dob =jsonObject.getString("DOB");
                            date=jsonObject.getString("BDateTime");
                            Date date1=formatter.parse(dob);
                            Date date2=formatter.parse(date);
                            customers.add(new customer(idcus,name,date1,sex,mail,phone));
                            bills.add(new bill(idbill,idcus,date2,total,payment,mess));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnect.ShowToast_Short(getContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}