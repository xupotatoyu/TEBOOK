package com.tebook.utils;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class BaseClient {

        private static final String BASE_URL= "http://www.weyipisces.cn/api/blog/book";

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

