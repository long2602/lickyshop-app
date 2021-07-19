package com.tlong.test.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tlong.test.R;
import com.tlong.test.adapter.adaccountAdapter;
import com.tlong.test.model.account;
import com.tlong.test.ultil.CheckConnect;
import com.tlong.test.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class AccountFragment extends Fragment {
    ListView listView;
    adaccountAdapter adapter;
    public ArrayList<account> arrayList;
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_account, container, false);
        arrayList=new ArrayList<>();
        getDuLieuLoaiSanPham();
        listView=rootview.findViewById(R.id.fragAcc_listview);
        adapter=new adaccountAdapter(getContext(),arrayList);
        listView.setAdapter(adapter);
        return rootview;
    }
    private void getDuLieuLoaiSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.LinkGetAccount, new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                if(response != null){
                    int id=0;
                    String name="";
                    String pass="";
                    String role="";
                    Date ngay =null;
                    for(int i = 0;i < response.length();i++){
                        try {
                            JSONObject jsonObject =response.getJSONObject(i);
                            id=jsonObject.getInt("IdAccount");
                            name=jsonObject.getString("Username");
                            pass=jsonObject.getString("APassword");
                            role=jsonObject.getString("Role");
//                            String date1=jsonObject.getString("CreateDate");
//                            ngay=new SimpleDateFormat("dd/MM/yyyy").parse(date1);
                            arrayList.add(new account(name,pass,id,role,null,null));
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnect.ShowToast_Short(getContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}