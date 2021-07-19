package com.tlong.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText edituser,editpass,editpass2,editname,editphone;
    Button btndangky;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        EventDangKy();
    }

    private void EventDangKy() {
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=edituser.getText().toString().trim();
                String pass=editpass.getText().toString().trim();
                String pass2=editpass2.getText().toString().trim();
                String name=editname.getText().toString().trim();
                String phone=editphone.getText().toString().trim();
                DangKy(username,pass,name,phone);
            }
        });
    }

    private void DangKy(String user,String pass,String name,String phone) {
        if(checkEditText(edituser) && checkEditText(editpass) && checkEditText(editpass2) && checkEditText(editname) && checkEditText(editphone) && checkPass(editpass,editpass2)){
            pDialog.show();
            StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.LinkSignUp, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String message = "";
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        if (jsonObject.getInt("success") == 1) {
                            message = jsonObject.getString("message");
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            message = jsonObject.getString("message");
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    pDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params=new HashMap<>();
                    params.put("username",user);
                    params.put("password",pass);
                    params.put("phone",phone);
                    params.put("name",name);
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
        edituser=(EditText)findViewById(R.id.register_edit_UserName);
        editname=(EditText)findViewById(R.id.register_edit_name);
        editpass=(EditText)findViewById(R.id.register_edit_pass);
        editpass2=(EditText)findViewById(R.id.register_edit_pass2);
        editphone=(EditText)findViewById(R.id.register_edit_phone);
        btndangky=(Button)findViewById(R.id.register_btn_dangky);
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("Đang đăng ký ...");
        pDialog.setCanceledOnTouchOutside(false);
    }

}