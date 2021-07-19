package com.tlong.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tlong.test.R;
import com.tlong.test.model.*;
import com.tlong.test.adapter.*;
import com.tlong.test.ultil.Server;
import com.tlong.test.ultil.UserLocalStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoActivity extends AppCompatActivity {
    ArrayList<info> list;
    infoAdapter adapter;
    ListView listView;
    Button btnLogout,btnHome,btnCart,btnAcc;
    UserLocalStore userLocalStore;
    CircleImageView avatar;
    TextView txtname,txtphone,txtmail,txtdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        userLocalStore  = new UserLocalStore(this);
        AnhXa();
        EventClick();
        DangXuat();
        MenuEvent();
        LoadDulieu();
    }

    private void EventClick() {
        //mở trang home
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegisterPage=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(RegisterPage);
            }
        });
        //mở trang giỏ hàng
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegisterPage=new Intent(getApplicationContext(), Cart2Activity.class);
                startActivity(RegisterPage);
            }
        });
    }

    private void LoadDulieu() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.LinkGetCustomer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name = "",sex="",mail="",phone="",date="";
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    txtname.setText(jsonObject.getString("name").toString().trim());
                    txtphone.setText("SĐT: "+jsonObject.getString("phone").toString().trim());
                    date=jsonObject.getString("date");
                    if(date==null||date.equals("")){
                        txtdate.setText("Ngày sinh: ");
                    }
                    else{
                        txtdate.setText("Ngày sinh: "+date);
                    }
                    mail=jsonObject.getString("mail").toString().trim();
                    if(mail==null||mail.equals("")){
                        txtmail.setText("Email: ");
                    }
                    else {
                        txtmail.setText("Email: "+mail);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params=new HashMap<>();
                params.put("id",String.valueOf(MainActivity.IDCUS));
                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
    private void MenuEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent intent;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        intent=new Intent(getApplicationContext(),BillActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent=new Intent(getApplicationContext(),EditInfoActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent=new Intent(getApplicationContext(),PolicyActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    private void DangXuat() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        btnHome=(Button)findViewById(R.id.home_btn_home);
        btnCart=(Button)findViewById(R.id.home_btn_giohang);
        btnAcc=(Button)findViewById(R.id.home_btn_account);
        avatar=(CircleImageView)findViewById(R.id.info_avatar);
        txtname=(TextView)findViewById(R.id.info_txt_name);
        txtphone=(TextView)findViewById(R.id.info_txt_phone);
        txtmail=(TextView)findViewById(R.id.info_txt_mail);
        txtdate=(TextView)findViewById(R.id.info_txt_date);
        btnLogout=(Button)findViewById(R.id.info_btn_logout);
        list=new ArrayList<>();
        list.add(new info("Đơn hàng của tôi",R.drawable.icon_bill));
        list.add(new info("Chỉnh sửa thông tin cá nhân",R.drawable.icon_setting));
        list.add(new info("Chính sách đổi trả",R.drawable.icon_info));
        listView=(ListView)findViewById(R.id.info_listview);
        adapter=new infoAdapter(list,getApplicationContext());
        listView.setAdapter(adapter);
    }
}