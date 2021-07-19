package com.tlong.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tlong.test.R;
import com.tlong.test.adapter.*;
import com.tlong.test.model.*;
import com.tlong.test.ultil.Server;
import com.tlong.test.ultil.UserLocalStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryOuterwearActivity extends AppCompatActivity {
    private RecyclerView rcvProduct;
    ArrayList<product> productArrayList;
    ArrayList<image> imageArrayList;
    topAdapter topAdapter;
    ImageButton btnBack;
    TextView search;
    Button btnHome,btnCart,btnAcc;
    UserLocalStore userLocalStore;
    String page="3";
    topAdapter.RecyclerViewClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outerwear);
        userLocalStore  = new UserLocalStore(this);
        AnhXa();
        EventClick();
        DangNhap();
        getDuLieuSP();

    }

    private void EventClick() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        //mở trang home
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegisterPage=new Intent(CategoryOuterwearActivity.this, MainActivity.class);
                startActivity(RegisterPage);
            }
        });
        //mở trang giỏ hàng
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegisterPage=new Intent(CategoryOuterwearActivity.this, Cart2Activity.class);
                startActivity(RegisterPage);
            }
        });
    }
    private void DangNhap() {
        btnAcc.setOnClickListener(new View.OnClickListener() {
            user user = userLocalStore.getLoggedInUser();
            @Override
            public void onClick(View view) {

                if(user!=null){
                    MainActivity.IDCUS=user.id;
                    Intent intent=new Intent(getApplicationContext(), InfoActivity.class);
                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                }
                else{
                    Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                }
            }
        });
    }
    private void getDuLieuSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan=Server.LinkProduct+"?page=2";
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
                            productArrayList.add(new product(IdProduct,Name,Imei,Description,IdType,price));
                            imageArrayList.add(new image(IdImage,Image,IdProduct));
                            topAdapter.notifyDataSetChanged();
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
    private void AnhXa(){
        btnHome=(Button)findViewById(R.id.home_btn_home);
        btnCart=(Button)findViewById(R.id.home_btn_giohang);
        btnAcc=(Button)findViewById(R.id.home_btn_account);
        rcvProduct=(RecyclerView)findViewById(R.id.category_rcView);
        btnBack=(ImageButton)findViewById(R.id.outer_imgBtn_back);
        search=(TextView) findViewById(R.id.catrgory_search);
        listener= new topAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent=new Intent(getApplicationContext(), ProductActivity.class);
                intent.putExtra("productDetail",productArrayList.get(position));
                intent.putExtra("productImage",imageArrayList.get(position));
                startActivity(intent);
            }
        };
        productArrayList=new ArrayList<>();
        imageArrayList=new ArrayList<>();
        topAdapter=new topAdapter(getApplicationContext(),productArrayList,imageArrayList,listener);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        rcvProduct.setLayoutManager(gridLayoutManager);
        rcvProduct.setAdapter(topAdapter);
    }

}