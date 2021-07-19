package com.tlong.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tlong.test.R;
import com.tlong.test.ultil.Server;

import java.util.HashMap;
import java.util.Map;

public class Forgot2Activity extends AppCompatActivity {
    ImageButton btnBack;
    EditText edituser,editphone;
    Button btntiep;
    String NAMEUSER;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot2);
        AnhXa();
        EventClick();
    }
    private void EventClick() {
        //trở về
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //đổi mật khẩu
        btntiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoiMatKhau();
            }
        });
    }

    private void DoiMatKhau() {
        if(checkEditText(editphone)&&checkEditText(edituser)&&checkPass(editphone,edituser)){
            StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.LinkUpdatePass, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("1")){
                        Toast.makeText(getApplicationContext(),"Thay đổi mật khẩu thành công",Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Thay đổi mật khẩu thất bại",Toast.LENGTH_LONG).show();
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
                    params.put("pass1",editphone.getText().toString().trim());
                    params.put("name1",NAMEUSER);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private boolean checkEditText(EditText editText) {
        if (editText.getText().toString().trim().length() > 0)
            return true;
        else {
            editText.setError("Vui lòng nhập dữ liệu!");
        }
        return false;
    }
    private boolean checkPass(EditText edi1,EditText edi2){
        if(edi1.getText().toString().equals(edi2.getText().toString())){
            return true;
        }else {
            edi2.setError("Mật khẩu không trùng khớp");
        }
        return false;
    }
    private void AnhXa() {
        NAMEUSER=getIntent().getStringExtra("NAMEUSER");
        btnBack=(ImageButton)findViewById(R.id.img_btn_back);
        btntiep=(Button)findViewById(R.id.forgot2_btn_xacnhan);
        editphone=(EditText) findViewById(R.id.forgot2_edit_pass);
        edituser=(EditText)findViewById(R.id.forgot2_edit_pass2);
    }
}