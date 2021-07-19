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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Forgot1Activity extends AppCompatActivity {
    ImageButton btnBack;
    EditText edituser,editphone;
    Button btntiep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot1);
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
                CheckThongTin();
            }
        });
    }

    private void CheckThongTin() {
        if(checkEditText(editphone)&&checkEditText(edituser)){
            StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.LinkCheckForgot, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("1")){
                        Intent intent=new Intent(getApplicationContext(),Forgot2Activity.class);
                        intent.putExtra("NAMEUSER",edituser.getText().toString().trim());
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Tài khoản không tồn tại",Toast.LENGTH_LONG).show();
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
                    params.put("phone",editphone.getText().toString().trim());
                    params.put("username",edituser.getText().toString().trim());
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
    private void AnhXa() {
        btnBack=(ImageButton)findViewById(R.id.img_btn_back);
        btntiep=(Button)findViewById(R.id.forgot1_btn_tieptuc);
        editphone=(EditText) findViewById(R.id.forgot1_edit_phone);
        edituser=(EditText)findViewById(R.id.forgot1_edit_UserName);
    }
}