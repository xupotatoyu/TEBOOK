package com.tebook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tebook.R;
import com.tebook.model.tsbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookcityGVAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    public List<tsbook> _listData = null;
    private Context mContext;

    public  BookcityGVAdapter(Context context, List<tsbook> list){

        _listData = new ArrayList<tsbook>();
        _listData = list;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return _listData == null ? 0 : _listData.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return _listData == null ? null : _listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return _listData == null ? 0 : position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_book, null);
            holder.bookname = (TextView)convertView.findViewById(R.id.txt_bookname);
            holder.txt_author = (TextView)convertView.findViewById(R.id.txt_author);
            holder.txt_content  = (TextView)convertView.findViewById(R.id.txt_content);
            holder.img_book = (ImageView)convertView.findViewById(R.id.img_book);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.bookname.setText(_listData.get(position).getbookname());
        holder.txt_author.setText(_listData.get(position).getauthor());
        holder.txt_content.setText(_listData.get(position).getcontent());


        String getEnglishName=_listData.get(position).getEnglishName().replaceAll(" ","_");
        holder.img_book.setImageResource(mContext.getResources().getIdentifier(
                getEnglishName.toLowerCase(Locale.ENGLISH), "drawable", mContext.getPackageName()));


        return convertView;
    }

    public class ViewHolder{
        private ImageView img_book;
        private TextView bookname;
        private TextView EnglishName;
        private TextView txt_author;
        private TextView txt_content;
    }


}