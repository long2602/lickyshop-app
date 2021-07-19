package com.tlong.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.tlong.test.R;
import com.tlong.test.model.*;
import com.tlong.test.adapter.*;
import com.tlong.test.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    Toolbar toolbar;
    ImageButton btnBack;
    ListView listKey, listSearch;
    hotkeyAdapter hotkeyAdapter;
    ArrayList<search> cartArrayList;
    //sản phẩm mới;
    ArrayList<image> imageArrayList;
    ArrayList<product> newproductArrayList;
    newAdapter newAdapter;
    RecyclerView NewrecyclerView;
    newAdapter.RecyclerViewClickListener listener;
    Button btnXemtatca;
    searchAdapter searchAdapter;
    ArrayList<search> listOld;
    public AppBarLayout appBarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        AnhXa();
        getDuLieuSPMoiNhat();
        getDuLieuSP();
        hotkeyEvent();
        resutlEvent();
    }

    private void resutlEvent() {
        listSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                product pd;
                image img;
                search item=(search) adapterView.getItemAtPosition(i);
                pd=new product(item.getIdProduct(),item.getName(),item.getImei(),item.getDescription(),item.getIdType(),item.getPrice());
                img=new image(item.getIdImage(), item.getImage(),item.getIdProduct());
                Intent intent=new Intent(getApplicationContext(), ProductActivity.class);
                intent.putExtra("productDetail",pd);
                intent.putExtra("productImage",img);
                startActivity(intent);
            }
        });
    }

    private void hotkeyEvent() {
        listKey.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        appBarLayout.setVisibility(View.VISIBLE);
                        searchAdapter.getFilter().filter("tshirt");
                        break;
                    case 1:
                        appBarLayout.setVisibility(View.VISIBLE);
                        searchAdapter.getFilter().filter("pant");
                        break;
                    case 2:
                        appBarLayout.setVisibility(View.VISIBLE);
                        searchAdapter.getFilter().filter("hoodie");
                        break;
                    case 3:
                        appBarLayout.setVisibility(View.VISIBLE);
                        searchAdapter.getFilter().filter("bag");
                        break;
                }
            }
        });
    }

    private void AnhXa() {
        toolbar=(Toolbar)findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        btnBack=(ImageButton)findViewById(R.id.img_btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnXemtatca=(Button)findViewById(R.id.search_btn_xemtatca);
        btnXemtatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listSearch.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 250*searchAdapter.getCount()));
            }
        });
        listKey=(ListView)findViewById(R.id.search_list_key);
        listSearch=(ListView)findViewById(R.id.search_listview_search);
        ArrayList<String> strings=new ArrayList<>();
        strings.add("tshirt");
        strings.add("pant");
        strings.add("hoodie");
        strings.add("bag");
        hotkeyAdapter=new hotkeyAdapter(strings,getApplicationContext());
        listKey.setAdapter(hotkeyAdapter);
        cartArrayList=new ArrayList<>();
        listOld=cartArrayList;
        searchAdapter=new searchAdapter(getApplicationContext(),cartArrayList,listOld);
        listSearch.setAdapter(searchAdapter);
        appBarLayout=(AppBarLayout)findViewById(R.id.search_appbar);
        //sản phẩm mới
        listener= new newAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent(getApplicationContext(), ProductActivity.class);
                intent.putExtra("productDetail",newproductArrayList.get(position));
                intent.putExtra("productImage",imageArrayList.get(position));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        };
        imageArrayList=new ArrayList<>();
        newproductArrayList=new ArrayList<>();
        newAdapter=new newAdapter(getApplicationContext(),newproductArrayList,imageArrayList,listener);
        NewrecyclerView=(RecyclerView)findViewById(R.id.search_rcvproduct);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        NewrecyclerView.setLayoutManager(gridLayoutManager);
        NewrecyclerView.setAdapter(newAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView=(SearchView) menu.findItem(R.id.menusearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                appBarLayout.setVisibility(View.VISIBLE);
                searchAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                appBarLayout.setVisibility(View.VISIBLE);
                searchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    private void getDuLieuSPMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.LinkNewProduct, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int IdProduct = 0;
                    String Name = "";
                    String Imei="";
                    String Description = "";
                    int IdType = 0;
                    double price =0;
                    String Image="";
                    int IdImage=0;
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject  =response.getJSONObject(i);
                            IdProduct=jsonObject.getInt("IdProduct");
                            Name=jsonObject.getString("Name");
                            Imei=jsonObject.getString("Imei");
                            Description=jsonObject.getString("Description");
                            IdType=jsonObject.getInt("IdType");
                            price=jsonObject.getDouble("price");
                            Image=jsonObject.getString("Image");
                            IdImage=jsonObject.getInt("IdImage");
                            newproductArrayList.add(new product(IdProduct,Name,Imei,Description,IdType,price));
                            imageArrayList.add(new image(IdImage,Image,IdProduct));
                            newAdapter.notifyDataSetChanged();
                        }  catch (JSONException e) {
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

    private void getDuLieuSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan=Server.LinkAllproduct;
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(duongdan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int IdProduct = 0;
                    String Name = "";
                    String Imei="";
                    String Description = "";
                    int IdType = 0;
                    double price =0;
                    String Image="";
                    int IdImage=0;
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject  =response.getJSONObject(i);
                            IdProduct=jsonObject.getInt("IdProduct");
                            Name=jsonObject.getString("Name");
                            Imei=jsonObject.getString("Imei");
                            Description=jsonObject.getString("Description");
                            IdType=jsonObject.getInt("IdType");
                            price=jsonObject.getDouble("price");
                            Image=jsonObject.getString("Image");
                            IdImage=jsonObject.getInt("IdImage");
                            cartArrayList.add(new search(IdProduct,Name,Imei,Description,IdType,price,IdImage,Image));
                            searchAdapter.notifyDataSetChanged();
                        }  catch (JSONException e) {
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
}