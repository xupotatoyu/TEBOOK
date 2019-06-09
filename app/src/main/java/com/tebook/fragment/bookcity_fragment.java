package com.tebook.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceActivity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.http.Header;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.markmao.pulltorefresh.widget.XListView;
import com.shizhefei.fragment.LazyFragment;
import com.tebook.R;
import com.tebook.adapter.BookcityGVAdapter;
import com.tebook.adapter.BookshelfGVAdapter;
import com.tebook.dao.BookInfoDao;
import com.tebook.model.tsbook;
import com.tebook.utils.ACache;
import com.tebook.utils.BaseClient;
import com.tebook.utils.PublicUtils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.markmao.pulltorefresh.widget.XListView.IXListViewListener;


public class bookcity_fragment extends LazyFragment implements IXListViewListener {
    private XListView book_list;
    private ACache aCache;
    private List<tsbook> _list_date = new ArrayList<tsbook>();
    private Handler mHandler;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.single_listview);

        try {
            getData();
        } catch (JSONException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        init();
    }

    public void init() {
        mHandler = new Handler();
        book_list = (XListView) findViewById(R.id.single_list);
        book_list.setPullRefreshEnable(true); //允许下拉刷新
        book_list.setPullLoadEnable(true);    //允许上拉加载更多
        book_list.setAutoLoadEnable(true);    //允许下拉到底部后自动加载
        book_list.setXListViewListener(this);

        aCache = ACache.get(getActivity());
        if (!PublicUtils.isNetworkAvailable(getActivity())) {
            JSONObject response = aCache.getAsJSONObject("book_list");
            // If the response is JSONObject instead of expected JSONArray
            try {

                JSONArray jsonArray = response.getJSONArray("data");
                Log.e("data", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject = (JSONObject) jsonArray.get(i);
                    tsbook tsbook = new tsbook();
                    tsbook.setId(jsonObject.getInt("id"));
                    tsbook.setbookname(jsonObject.getString("bookname"));
                    tsbook.setEnglishName(jsonObject.getString("EnglishName"));
                    tsbook.setauthor(jsonObject.getString("author"));
                    tsbook.setcontent(jsonObject.getString("content"));


                    _list_date.add(tsbook);
                }
                BookcityGVAdapter adapter = new BookcityGVAdapter(
                        getApplicationContext(), _list_date);
                book_list.setAdapter(adapter);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public void getData() throws JSONException {
        Log.e("getData", "getData");
        BaseClient.get("", null,
                new JsonHttpResponseHandler() {

                    public void onSuccess( int statusCode, Header[] headers,
                                           JSONObject response) {
                        // If the response is JSONObject instead of expected
                        // JSONArray
                        Log.e("onSuccess", response.toString());
                        aCache.put("book_list", response);
                        try {
                                JSONArray jsonArray = response.getJSONArray("data");
                                Log.e("onSuccess", response.toString());

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject = (JSONObject) jsonArray.get(i);
                                    tsbook tsbook = new tsbook();
                                    tsbook.setId(jsonObject.getInt("id"));
                                    tsbook.setbookname(jsonObject.getString("bookname"));
                                    tsbook.setEnglishName(jsonObject.getString("EnglishName"));
                                    tsbook.setauthor(jsonObject.getString("author"));
                                    tsbook.setcontent(jsonObject.getString("content"));
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");

                                    _list_date.add(tsbook);
                                    BookcityGVAdapter adapter = new BookcityGVAdapter(getApplicationContext(), _list_date);
                                    book_list.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        onLoaded();
                    }
                });
    }


    //下拉刷新@Override
    public void onRefresh() {
        // TODO Auto-generatedmethod stub
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                _list_date = new ArrayList<tsbook>();
                try {
                    getData();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, 1);
    }

    //上拉加载更多@Override
    public void onLoadMore() {
        // TODO Auto-generated method stub
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    getData();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, 1);
    }

    //完成数据加载
    private void onLoaded() {
        book_list.stopRefresh();
        //停止刷新
        book_list.stopLoadMore();     //停止加载更多
        book_list.setRefreshTime(getTime());
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

}

