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
import com.tlong.test.model.*;
import com.tlong.test.ultil.CheckConnect;
import com.tlong.test.ultil.Server;
import com.tlong.test.adapter.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;


public class ProductFragment extends Fragment {
    ListView listView;
    adProductAdapter adapter;
    ArrayList<product> products;
    ArrayList<image> images;
    ArrayList<size> sizes;
    ArrayList<type> types;
    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product, container, false);
        products=new ArrayList<>();
        sizes=new ArrayList<>();
        types=new ArrayList<>();
        images=new ArrayList<>();
        getDuLieuLoaiSanPham();
        listView=view.findViewById(R.id.fragPro_listview);
        adapter=new adProductAdapter(getContext(),products,images,types,sizes);
        listView.setAdapter(adapter);
        return view;
    }

    private void getDuLieuLoaiSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.LinkGetAdProduct, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                if(response != null){
                    int idpro=0,idimg=0,idtype=0,idsize=0,sl=0;
                    String name="",des="",imei="",title="",stitle="",img="";
                    double gia=0;
                    for(int i = 0;i < response.length();i++){
                        try {
                            JSONObject jsonObject =response.getJSONObject(i);
                            idpro=jsonObject.getInt("IdProduct");
                            idimg=jsonObject.getInt("IdImage");
                            idsize=jsonObject.getInt("IdSize");
                            idtype=jsonObject.getInt("IdType");
                            gia=jsonObject.getDouble("price");
                            sl=jsonObject.getInt("Amount");
                            name=jsonObject.getString("Name");
                            des=jsonObject.getString("Description");
                            imei=jsonObject.getString("Imei");
                            title=jsonObject.getString("Title");
                            stitle=jsonObject.getString("STitle");
                            img=jsonObject.getString("Image");
                            products.add(new product(idpro,name,imei,des,idtype,gia));
                            images.add(new image(idimg,img,idpro));
                            sizes.add(new size(idsize,stitle,sl,idpro));
                            types.add(new type(idtype,title));
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
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