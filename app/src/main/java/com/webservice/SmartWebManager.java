package com.webservice;

import android.content.Context;
import android.util.Log;

import com.adapter.JSONResponse;
import com.interf.RequestInterface;

import java.lang.reflect.Method;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SmartWebManager  {
    private static final int TIMEOUT = 100000;
    private Object JSONResponse;
    public enum REQUEST_METHOD_PARAMS {CONTEXT,METHOD, URL, RESPONSE_LISTENER}
    private static SmartWebManager mInstance;
    private Context mCtx;
     private Retrofit retrofit;
    RequestInterface request;

    private SmartWebManager(Context context) {
        mCtx = context;
        request = getRequestQueue();
    }

    public static synchronized SmartWebManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SmartWebManager(context);
        }
        return mInstance;
    }
    public RequestInterface getRequestQueue() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://reqres.in/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            request = retrofit.create(RequestInterface.class);
        }
        return request;
    }
    public <T> void addToRequestQueue(final HashMap<REQUEST_METHOD_PARAMS, Object> requestParams) {
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
            JSONResponse jsonResponse = response.body();
    ((OnResponseReceivedListener) requestParams.get(REQUEST_METHOD_PARAMS.RESPONSE_LISTENER)).onResponseReceived(jsonResponse, true, 200);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });

    }

    public interface OnResponseReceivedListener {
        void onResponseReceived(JSONResponse tableRows, boolean isValidResponse, int responseCode);

        void onResponseError();
    }
}