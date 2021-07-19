package com.tlong.test.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tlong.test.R;
import com.tlong.test.adapter.paymentAdapter;
import com.tlong.test.model.user;
import com.tlong.test.ultil.CheckConnect;
import com.tlong.test.ultil.Server;
import com.tlong.test.ultil.UserLocalStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Payment2Activity extends AppCompatActivity {
    TextView txthienthi,txttien,txttamtinh,txtvanchuyen,txtgiam,txttongcong;
    ImageButton btnBack;
    boolean check=true;
    RecyclerView list;
    LinearLayout ln;
    double tiengiam=0;
    double vanchuyen=15000;
    paymentAdapter adapter;

    //thong tin khach hàng
    Button btnXacNhan;
    Dialog dialog;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment2);
        userLocalStore  = new UserLocalStore(this);
        AnhXa();
        ThongTinChiTiet();
        TinhTien();
        if(CheckConnect.isNetworkAvailable((getApplicationContext()))){
            XacNhanThongTin();
        }
        else {
            CheckConnect.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối!!!");
        }
    }

    private void XacNhanThongTin() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user user = userLocalStore.getLoggedInUser();
                RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.LinkBill, new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String madonhang) {
                        Log.d("IDBILL",madonhang);
                        if(Integer.parseInt(madonhang)>0){
                            RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                            StringRequest request=new StringRequest(Request.Method.POST, Server.LinkBillDetail, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("1")){
                                        MainActivity.ArrayCart.clear();
                                        MainActivity.mainAmount=0;
                                        MainActivity.mainTotal=0;
                                        MainActivity.CheckCart=0;
                                        if(user==null){
                                            MainActivity.IDCUS=0;
                                        }
                                        openDialog();
                                    }
                                    else {
                                        CheckConnect.ShowToast_Short(getApplicationContext(),"Dữ liệu giỏ hàng của bạn bị lỗi!!!");
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    JSONArray jsonArray=new JSONArray();
                                    for (int i=0;i<MainActivity.ArrayCart.size();i++){
                                        JSONObject jsonObject=new JSONObject();
                                        try {
                                            jsonObject.put("IdBILL",madonhang);
                                            jsonObject.put("amount",MainActivity.ArrayCart.get(i).getAmount());
                                            jsonObject.put("price",MainActivity.ArrayCart.get(i).getPrice());
                                            jsonObject.put("total",MainActivity.ArrayCart.get(i).getTotal());
                                            jsonObject.put("IdPro",MainActivity.ArrayCart.get(i).getIdPro());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jsonArray.put(jsonObject);
                                    }
                                    HashMap<String,String> hashMap=new HashMap<String, String>();
                                    hashMap.put("json",jsonArray.toString());
                                    return hashMap;
                                }
                            };
                            queue.add(request);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap=new HashMap<String, String>();
                        hashMap.put("idcustomer",String.valueOf(MainActivity.IDCUS));
                        hashMap.put("tong",String.valueOf(MainActivity.mainTotal));
                        return hashMap;
                    }
                };
                requestQueue.add(stringRequest);
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
                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
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
        adapter=new paymentAdapter(Payment2Activity.this,MainActivity.ArrayCart);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        //thong tin khach hang
        dialog=new Dialog(this);
        btnXacNhan=(Button)findViewById(R.id.payment_btn_hoanthanhdonhang);
    }
}