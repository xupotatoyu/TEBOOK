package com.tebook.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.shizhefei.fragment.LazyFragment;
import com.tebook.R;
import com.tebook.activity.home_filebrowsing;
import com.tebook.adapter.BookshelfGVAdapter;
import com.tebook.dao.BookInfoDao;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class bookshelf_fragment extends Fragment {

    private GridView gv_book;// 书架的gridView
    private TextView tv_classifyName;// 书架top bar上分类名称
    private ImageButton imgBtn_openMenuAc; //打开菜单

    private BookshelfGVAdapter bookshelfGVAdapter;// 书架的自定义adapter
    private List<Map<String, Object>> classifyLVList;// 分类列表的list数据集合（注意初始化后，请不要把它重新指向新的list）

    public static final int ALLBOOK = -4;
    public static int currentCategory;

    private BookInfoDao bookInfoDao;
    private Context context;
    private long exitTime = 0;// 计算短时间内点击返回按钮的次数
//    private Context context = getContext().getApplicationContext();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_bookshelf, container, false);

        gv_book = (GridView) view.findViewById(R.id.gridView_book);
        tv_classifyName = (TextView) view.findViewById(R.id.tv_classifyName);
        imgBtn_openMenuAc = (ImageButton) view.findViewById(R.id.imgBtn_openFileSAct);

        bookInfoDao = new BookInfoDao(getContext());

        // 设置监听器
        imgBtn_openMenuAc.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view1) {
                Intent intent = new Intent(getActivity(), home_filebrowsing.class);
                startActivity(intent);
            }
        });


        // ————设置书架
        bookshelfGVAdapter = new BookshelfGVAdapter(getContext(), bookInfoDao.getAllBooks());
        gv_book.setAdapter(bookshelfGVAdapter);
        tv_classifyName.setText("全部图书");
        currentCategory = ALLBOOK;

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void onClick(View v) {

    }


    /**
     * 从其它页面返回到当前页面的处理
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == 1) { //从文件浏览页面返回
            updateAllBook(currentCategory, (String) tv_classifyName.getText());
        }
    }

    /**
     * 更新书架上*所有*图书，显示某类别的图书 参数： int
     * currentCategory：类别编号，它可以是分类编号，或者表示最爱、最近阅读、全部或未分类的标记 String
     * currentCategoryName：类别名称
     */
    public void updateAllBook(int currentCategory, String currentCategoryName) {
        switch (currentCategory) {
            case ALLBOOK:
                bookshelfGVAdapter.setList(bookInfoDao.getAllBooks());
                break;

        }

        tv_classifyName.setText(currentCategoryName);
        this.currentCategory = currentCategory;
        bookshelfGVAdapter.notifyDataSetInvalidated();
    }

}