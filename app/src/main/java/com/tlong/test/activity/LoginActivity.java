package com.tlong.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tlong.test.R;
import com.tlong.test.ultil.Server;
import com.tlong.test.ultil.UserLocalStore;
import com.tlong.test.model.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView txtRegister,txtForgot;
    EditText edituser,editpass;
    Button btnlogin,btnHome,btnCart,btnAcc;
    ProgressDialog pDialog;
    UserLocalStore userLocalStore;
    public static String LOGIN_USERNAME;
    public static String LOGIN_PASSWORD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userLocalStore=new UserLocalStore(this);
        AnhXa();
        EventClick();
        EventDangNhap();

    }

    private void EventClick() {
        //mở trang đăng ký
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterPage=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(RegisterPage);
            }
        });
        //mở trang quên mật khẩu
        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegisterPage=new Intent(LoginActivity.this, Forgot1Activity.class);
                startActivity(RegisterPage);
            }
        });
        //mở trang home
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent RegisterPage=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(RegisterPage);
            }
        });
        //mở trang giỏ hàng
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegisterPage=new Intent(LoginActivity.this, Cart2Activity.class);
                startActivity(RegisterPage);
            }
        });
        //mở trang tài khoản
        btnAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegisterPage=new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(RegisterPage);

            }
        });
    }


    private void EventDangNhap() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=edituser.getText().toString().trim();
                String pass=editpass.getText().toString().trim();
                DangNhap(username,pass);
            }
        });
    }

    private void DangNhap(String username,String pass) {
        if(checkEditText(edituser) && checkEditText(editpass)){
            pDialog.show();
            StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.LinkSignIn, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String message="";
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        if (jsonObject.getInt("success") == 1){
                            message = jsonObject.getString("message");
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            user item=new user(jsonObject.getString("phone"),
                                    jsonObject.getString("user_name"),
                                    jsonObject.getString("pass_word"),jsonObject.getInt("id_cus"));
                                userLocalStore.storeUserData(item);
                                userLocalStore.setUserLoggedIn(true);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            message = jsonObject.getString("message");
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    pDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> params=new HashMap<>();
                    params.put("username",username);
                    params.put("password",pass);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(stringRequest);
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
        txtRegister=(TextView)findViewById(R.id.login_txt_Dangkingay);
        txtForgot=(TextView)findViewById(R.id.login_txt_Quenmatkhau);
        btnHome=(Button)findViewById(R.id.home_btn_home);
        btnCart=(Button)findViewById(R.id.home_btn_giohang);
        btnAcc=(Button)findViewById(R.id.home_btn_account);
        edituser=(EditText)findViewById(R.id.login_edit_user);
        editpass=(EditText)findViewById(R.id.login_edit_pass);
        btnlogin=(Button)findViewById(R.id.login_btn_Login);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Đang đăng nhập...");
        pDialog.setCanceledOnTouchOutside(false);
    }
}