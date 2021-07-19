package com.tlong.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tlong.test.R;
import com.tlong.test.ultil.Server;
import com.tlong.test.model.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditInfoActivity extends AppCompatActivity {
    CircleImageView avatar;
    Button btnhuy,btnxacnhan;
    EditText txtname,txtdate,txtphone,txtmail,txtgender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        AnhXa();
        LoadDulieu();
        CapNhatEvent();
    }

    private void CapNhatEvent() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1="",mail1="",sex1="",day1="";
                name1=txtname.getText().toString().trim();
                mail1=txtmail.getText().toString().trim();
                sex1=txtgender.getText().toString().trim();
                day1=txtdate.getText().toString().trim();
                ChinhSuaThongTin(name1,mail1, day1, sex1);
            }
        });
    }

    private void ChinhSuaThongTin(String name,String mail,String day,String gender) {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.LinkUpdateCustomer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("1")){
                    Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(EditInfoActivity.this,InfoActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Cập nhật thông tin thất bại",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("id1",String.valueOf(MainActivity.IDCUS));
                params.put("name1",name);
                params.put("date1",day);
                params.put("mail1",mail);
                params.put("sex1",gender);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void LoadDulieu() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.LinkGetCustomer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name = "",sex="",mail="",phone="";
                Date day;
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    txtname.setText(jsonObject.getString("name").toString().trim());
                    if(jsonObject.getString("sex")==null){
                        txtgender.setText("");
                    }
                    else {
                        txtgender.setText(jsonObject.getString("sex").toString().trim());
                    }
                    txtdate.setText(jsonObject.getString("date").toString().trim());
                    txtphone.setText(jsonObject.getString("phone").toString().trim());
                    txtmail.setText(jsonObject.getString("mail").toString().trim());
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

    private void AnhXa() {
        avatar=(CircleImageView)findViewById(R.id.edit_avatar);
        btnhuy=(Button)findViewById(R.id.edit_btn_huy);
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnxacnhan=(Button)findViewById(R.id.edit_btn_xacnhan);

        txtname=(EditText)findViewById(R.id.edit_edit_name);
        txtdate=(EditText)findViewById(R.id.edit_edit_DOB);
        txtgender=(EditText)findViewById(R.id.edit_edit_sex);
        txtmail=(EditText)findViewById(R.id.edit_edit_mail);
        txtphone=(EditText)findViewById(R.id.edit_edit_sdt);
    }

}