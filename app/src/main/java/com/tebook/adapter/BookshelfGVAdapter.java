package com.tebook.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.tebook.R;
import com.tebook.dao.BookInfoDao;
import com.tebook.fragment.bookshelf_fragment;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookshelfGVAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Map<String, Object>> list;

    public BookshelfGVAdapter(Context context, List<Map<String, Object>> list) {
        //获得LayoutInflater的实例
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return list.size(); //返回列表长度
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);//返回该位置的Map<>
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    public final class ViewHolder {
        public Button btn_book;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //设置/获取holder
        ViewHolder holder = null;
        if (convertView == null) {
            //convertView表示某一行的view布局，是已经inflate了的布局
            convertView = layoutInflater.inflate(R.layout.bookshelf_grid_item, null);

            //实例化holder中的weight
            holder = new ViewHolder();
            holder.btn_book = (Button) convertView.findViewById(R.id.btn_book);

            //给convertView绑定一个额外数据，把ViewHolder缓存起来，方便下次可以getTag()获得该view，直接重用
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        //把holder中的控件与List<>数据源对应起来，即与Map中的key字段对应
        final int p = position;
        holder.btn_book.setBackgroundResource(Integer.parseInt(String.valueOf(list.get(position).get("cover"))));
        holder.btn_book.setText((String) list.get(position).get("name"));


        //单击书籍
        holder.btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到阅读界面
                TxtConfig.saveIsOnVerticalPageMode(context,false);
                Map<String, Object> currItemMap = list.get(p);
                HwTxtPlayActivity.loadTxtFile(context,String.valueOf(list.get(p).get("address")));
            }
        });

        //长按书籍
        holder.btn_book.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(context, R.style.HorizonDialog);
                dialog.setContentView(R.layout.dialog_book_longclick);
                dialog.setCanceledOnTouchOutside(true);//当点击对话框以外区域时，关闭对话框

                Button btn_delete = (Button)dialog.findViewById(R.id.btn_delete);
                Button btn_rename = (Button)dialog.findViewById(R.id.btn_rename);


                //删除书籍
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        final Dialog deleteDialog = new Dialog(context, R.style.HorizonDialog);
                        deleteDialog.setContentView(R.layout.dialog_delete);

                        Button btn_ok = (Button)deleteDialog.findViewById(R.id.btn_ok);
                        Button btn_cancel = (Button)deleteDialog.findViewById(R.id.btn_cancel);
                        final CheckBox checkBox_deleteSource = (CheckBox)deleteDialog.findViewById(R.id.checkBox_deleteSourceFile);

                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                if(checkBox_deleteSource.isChecked()) {
                                    //删除源文件
                                    File file = new File(String.valueOf(list.get(p).get("address")));
                                    if(file.exists()) {
                                        file.delete();
                                    }
                                }

                                //删除书籍的数据库操作
                                BookInfoDao bookInfoDao = new BookInfoDao(context);
                                bookInfoDao.deleteBook(Integer.parseInt(String.valueOf(list.get(p).get("id"))));

                                //更新书架
                                updateAllBook(bookshelf_fragment.currentCategory);

                                deleteDialog.cancel();
                            }
                        });

                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                deleteDialog.cancel();
                            }
                        });

                        deleteDialog.show();
                        dialog.cancel();
                    }
                });

                //重命名书籍
                btn_rename.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        final Dialog renameDialog = new Dialog(context, R.style.HorizonDialog);
                        renameDialog.setContentView(R.layout.dialog_rename);

                        Button btn_ok = (Button)renameDialog.findViewById(R.id.btn_ok);
                        Button btn_cancel = (Button)renameDialog.findViewById(R.id.btn_cancel);
                        final EditText et_newName = (EditText)renameDialog.findViewById(R.id.et_newName);
                        et_newName.setText(String.valueOf(list.get(p).get("name")));

                        btn_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                int id = Integer.parseInt(String.valueOf(list.get(p).get("id")));
                                String newNameStr = String.valueOf(et_newName.getText());
                                String newAddressStr = "";

                                if(newNameStr != "" && newNameStr != null) {
                                    newNameStr += ".txt";

                                    //修改文件地址和文件名,然后给newAddressStr赋值
                                    File file = new File((String)list.get(p).get("address"));
                                    if(file.exists()) {
                                        newAddressStr = file.getParent() + "/" + newNameStr;
                                        File renameFile = new File(newAddressStr);
                                        if(renameFile.exists()) {
                                            Toast.makeText(context, "该图书文件名已经存在,请重新输入", Toast.LENGTH_LONG).show();
                                        } else {
                                            file.renameTo(renameFile);//重命名文件

                                            //重命名数据库中图书的名和地址
                                            BookInfoDao bookInfoDao = new BookInfoDao(context);
                                            bookInfoDao.alterBookName(id, newNameStr, newAddressStr);

                                            //更新书架
                                            list.get(p).put("name", newNameStr);
                                            list.get(p).put("address", newAddressStr);
                                            notifyDataSetChanged();

                                            renameDialog.cancel();
                                        }
                                    }
                                } else {
                                    Toast.makeText(context, "请输入分类名", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                renameDialog.cancel();
                            }
                        });

                        renameDialog.show();
                        dialog.cancel();
                    }
                });


                dialog.show();
                return true;
            }
        });

        return convertView;
    }


    /**获取此adapter的list数据集合*/
    public List<Map<String, Object>> getList() {
        return list;
    }

    /**设置此adapter的list数据集合*/
    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    /**
     * 更新书架上当前显示类别的图书
     * 参数： int currentCategory：类别编号，它可以是分类编号，或者表示最爱、最近阅读、全部或未分类的标记 String
     */

    public void updateAllBook(int currentCategory) {
        BookInfoDao bookInfoDao = new BookInfoDao(context);
        switch (currentCategory) {
            case bookshelf_fragment.ALLBOOK:
                setList(bookInfoDao.getAllBooks());
                break;

        }
        notifyDataSetInvalidated();
    }
}