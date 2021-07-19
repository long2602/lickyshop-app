package com.tlong.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tlong.test.R;
import com.tlong.test.adapter.*;
import com.tlong.test.model.user;
import com.tlong.test.ultil.CheckConnect;
import com.tlong.test.ultil.Server;
import com.tlong.test.ultil.UserLocalStore;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    TextView txthienthi,txttien,txttamtinh,txtvanchuyen,txtgiam,txttongcong;
    ImageButton btnBack;
    boolean check=true;
    RecyclerView list;
    LinearLayout ln;
    double tiengiam=0;
    double vanchuyen=15000;
    paymentAdapter adapter;

    //thong tin khach hàng
    EditText editName,editPhone,editAddress,editCountry,editCity,editDistrict,editSub;
    Button btnXacNhan;
    Dialog dialog;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        userLocalStore  = new UserLocalStore(this);
        user user = userLocalStore.getLoggedInUser();
        AnhXa();
        ThongTinChiTiet();
        TinhTien();
        if(CheckConnect.isNetworkAvailable((getApplicationContext()))){
            if(user==null){
                XacNhanThongTin();
            }
            else{
                editName.setText(user.name);
                editPhone.setText(user.phone);
                XacNhanThongTin2(user.id);
            }
        }
        else {
            CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối!!!");
        }
    }

    private void XacNhanThongTin2(int id) {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sonha=editAddress.getText().toString().trim();
                final String quocgia=editCountry.getText().toString().trim();
                final String tp=editCity.getText().toString().trim();
                final String quan=editDistrict.getText().toString().trim();
                final String phuong=editSub.getText().toString().trim();
                if(sonha.length()>0 && quocgia.length()>0 && tp.length()>0
                        && quan.length()>0 && phuong.length()>0){
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.LinkInformation2, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            MainActivity.IDCUS=id;
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap=new HashMap<String, String>();
                            hashMap.put("idkhachhang",String.valueOf(id));
                            hashMap.put("diachi",sonha);
                            hashMap.put("quocqia",quocgia);
                            hashMap.put("thanhpho",tp);
                            hashMap.put("quan",quan);
                            hashMap.put("phuong",phuong);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                    Toast.makeText(getApplicationContext(),String.valueOf(MainActivity.IDCUS),Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(getApplicationContext(), Payment2Activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                }
                else {
                    CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối!!!");
                }
            }
        });
    }

    private void XacNhanThongTin() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten=editName.getText().toString().trim();
                final String sdt=editPhone.getText().toString().trim();
                final String sonha=editAddress.getText().toString().trim();
                final String quocgia=editCountry.getText().toString().trim();
                final String tp=editCity.getText().toString().trim();
                final String quan=editDistrict.getText().toString().trim();
                final String phuong=editSub.getText().toString().trim();
                if(ten.length()>0 &&sdt.length()>0 && sonha.length()>0 && quocgia.length()>0 && tp.length()>0
                && quan.length()>0 && phuong.length()>0){
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.LinkInformation, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            MainActivity.IDCUS=Integer.parseInt(response);
//                            Log.d("IdCUS",response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap=new HashMap<String, String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("diachi",sonha);
                            hashMap.put("quocqia",quocgia);
                            hashMap.put("thanhpho",tp);
                            hashMap.put("quan",quan);
                            hashMap.put("phuong",phuong);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                    Intent intent =new Intent(getApplicationContext(), Payment2Activity.class);
                    startActivity(intent);
                }
                else {
                    CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối!!!");
                }
            }
        });
    }
    private void openDialog(){
        dialog.setContentView(R.layout.dialog_success);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        Button btn=dialog.findViewById(R.id.dialogsuccess_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
    private void TinhTien() {
        double tongcong=MainActivity.mainTotal+tiengiam+vanchuyen;
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtvanchuyen.setText(decimalFormat.format(vanchuyen)+ "VNĐ");
        txttamtinh.setText(decimalFormat.format(MainActivity.mainTotal)+ "VNĐ");
        txttien.setText(decimalFormat.format(tongcong)+" VNĐ");
        txttongcong.setText(decimalFormat.format(tongcong)+" VNĐ");
    }

    private void ThongTinChiTiet() {
        txthienthi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check==true){
                    ln.setVisibility(View.VISIBLE);
                    list.setVisibility(View.VISIBLE);
                    txthienthi.setText("Ẩn thông tin đơn hàng");
                    check=false;
                    txthienthi.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_hide,0);
                }
                else {
                    ln.setVisibility(View.GONE);
                    list.setVisibility(View.GONE);
                    txthienthi.setText("Hiển thị thông tin đơn hàng");
                    txthienthi.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.icon_show,0);
                    check=true;
                }
            }
        });
    }

    private void AnhXa() {
        btnBack=(ImageButton)findViewById(R.id.img_btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txthienthi=(TextView)findViewById(R.id.payment_txt_thongtindonhang);
        txttien=(TextView)findViewById(R.id.payment_txt_giatien);
        txttamtinh=(TextView)findViewById(R.id.payment_txt_giatientamtinh);
        txtvanchuyen=(TextView)findViewById(R.id.payment_txt_giavanchuyen);
        txtgiam=(TextView)findViewById(R.id.payment_txt_giamgiatien);
        txttongcong=(TextView)findViewById(R.id.payment_txt_giatientongcong);
        ln=(LinearLayout)findViewById(R.id.payment_ln_chitiet);
        list=(RecyclerView)findViewById(R.id.payment_listview);
        adapter=new paymentAdapter(PaymentActivity.this,MainActivity.ArrayCart);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        

        //thong tin khach hang
        dialog=new Dialog(this);
        editName=(EditText)findViewById(R.id.payment_edittxt_hovaten);
        editPhone=(EditText)findViewById(R.id.payment_edittxt_sodienthoai);
        editAddress=(EditText)findViewById(R.id.payment_edittxt_diachi);
        editCountry=(EditText)findViewById(R.id.payment_edittxt_quocgia);
        editCity=(EditText)findViewById(R.id.payment_edittxt_tinhthanh);
        editDistrict=(EditText)findViewById(R.id.payment_edittxt_quan);
        editSub=(EditText)findViewById(R.id.payment_edittxt_phuong);
        btnXacNhan=(Button)findViewById(R.id.payment_btn_hoanthanhdonhang);
    }
}