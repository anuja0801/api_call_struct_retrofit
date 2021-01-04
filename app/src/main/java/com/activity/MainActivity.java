package com.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adapter.DataAdapter;
import com.adapter.JSONResponse;
import com.example.api_call_struct_retrofit.R;
import com.framework.SmartUtils;
import com.model.Details;
import com.webservice.SmartWebManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Details> data;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        getData();
        //ssss
    }


  private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        HashMap<SmartWebManager.REQUEST_METHOD_PARAMS, Object> requestParams = new HashMap<>();
        // requestParams.put(SmartWebManager.REQUEST_METHOD_PARAMS.URL, getString(R.string.domain_name) + "users?page=2");
        requestParams.put(SmartWebManager.REQUEST_METHOD_PARAMS.CONTEXT, MainActivity.this);
        requestParams.put(SmartWebManager.REQUEST_METHOD_PARAMS.RESPONSE_LISTENER, new SmartWebManager.OnResponseReceivedListener() {
            @Override
            public void onResponseReceived(JSONResponse response, boolean isValidResponse, int responseCode) {
                SmartUtils.hideLoadingDialog();
                if (responseCode == 200) {
                    Log.e("Response", "AboutUs Successful!!");
                    try {
                        data = new ArrayList<>(Arrays.asList(response.getData()));
                        adapter = new DataAdapter(MainActivity.this,data);
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        //ex
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onResponseError() {
                Log.e("Response", "AboutUs Error!!");
                SmartUtils.hideLoadingDialog();
            }
        });
        SmartWebManager.getInstance(getApplicationContext()).addToRequestQueue(requestParams);
    }

}