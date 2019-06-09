package com.tebook.utils;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class BaseClientb {

    private static final String BASE_URL= "http://121.199.40.253/nba/game";

    private static final String TAG = "HttpClient";
    //contentType

    //创建实例
    private static final AsyncHttpClient client= new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {


        client.get(getAbsoluteUrl(), params, responseHandler);
    }
    private static String getAbsoluteUrl() {
        return BASE_URL ;
    }

}


