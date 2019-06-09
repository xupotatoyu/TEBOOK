package com.tebook.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.tebook.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookInfoDao {
    public static DBOpenHelper helper; //定义DBOpenHelper数据库帮助类对象
    public static SQLiteDatabase db;  //定义SQLiteDatabase数据库对象

    public BookInfoDao(Context context) {
        if(db == null) {
            helper = new DBOpenHelper(context);
            db = helper.getWritableDatabase();
        }
    }

/**添加一条书籍记录
 * 参数：Book（name, address, cover字段必填，cover可使用Book的getRandomCover()方法获取随机封面）
 *         （id:数据库自增不必填，classifyId默认为0（为0则往数据库写进null）选填，mylove默认为1选填，其他字段默认为0）
 * 返回值：boolean 是否执行成功*/

public boolean addBook(Book bookInfo) {
    Object[] values = new Object[3];
    values[0] = bookInfo.getName();
    values[1] = bookInfo.getAddress();
    values[2] = bookInfo.getCover();

    boolean success = true;
    try {
        db.execSQL("insert into tb_bookInfo(name,address,cover) "
                + "values(?,?,?)",values);
    } catch (SQLException e) {
        success = false;
    }
    return success;
}

    /**删除一条书籍记录
     * 参数：bookId （int） ：书籍编号
     * 返回值：无*/
    public void deleteBook(int bookId) {
        db.execSQL("delete from tb_bookInfo where _id=?",new Object[]{bookId});
    }

    /**通过地址，获取书籍编号
     * 参数：address （String） ：书籍地址
     * 返回值：int ：书籍编号，不存在则返回-1*/
    public int checkBookExistByAddress(String address) {
        Cursor cur = null;
        int id = -1;
        try {
            cur = db.rawQuery("select _id from tb_bookInfo where address=?", new String[]{address});
            if (cur.moveToNext()) {
                id = cur.getInt(cur.getColumnIndex("_id"));
            }
        }
        catch (SQLException e) {
        }
        finally {
            if(cur != null) {
                cur.close();
            }
        }
        return id;
    }


    /**修改书籍名。（注：此时同时也修改了书籍地址）
     * 参数：bookId （int） ：书籍编号
     *     newBookName (String) ：新书籍名
     *     newAddress(String):新地址
     * 返回值：无*/
    public void alterBookName(int bookId, String newBookName, String newAddress) {
        String sql = "update tb_bookInfo set name=?,address=? where _id=?";
        db.execSQL(sql, new Object[]{newBookName, newAddress, bookId});
    }


    public List<Map<String, Object>> getAllBooks() {
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        Cursor cur = null;
        try {
            cur = db.rawQuery("select * from tb_bookInfo", null);
            while (cur.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", cur.getString(cur.getColumnIndex("_id")));
                map.put("name", cur.getString(cur.getColumnIndex("name")));
                map.put("address", cur.getString(cur.getColumnIndex("address")));
                map.put("cover", cur.getInt(cur.getColumnIndex("cover")));
                list.add(map);
            }
        }
        catch (SQLException e) {
        }
        finally {
            if(cur != null) {
                cur.close();
            }
        }
        return list;
    }

    /**获取书籍表最新插入记录的_id号
     * 参数：无
     * 返回值：int ：最新插入的_id号 */
    public int getLastTBBookInfoId() {
        Cursor cur = null;
        int id = -1;
        try {
            cur = db.rawQuery("select last_insert_rowid() from tb_bookInfo", null);
            if (cur.moveToNext()) {
                id = cur.getInt(0);
            }
        }
        catch (SQLException e) {
        }
        finally {
            if(cur != null) {
                cur.close();
            }
        }
        return id;
    }

    /**通过地址，查询一本书的所有信息
     * 参数：address （String） ：书籍地址
     * 返回值：Book ：一个书籍对象*/
    public Book getBookByAddress(String address) {
        Cursor cur = null;
        Book book = null;
        try {
            cur = db.rawQuery("select * from tb_bookInfo where address=?", new String[]{address});
            if (cur.moveToNext()) {
                book = new Book();
                book.set_id(cur.getInt(cur.getColumnIndex("_id")));
                book.setName(cur.getString(cur.getColumnIndex("name")));
                book.setAddress(cur.getString(cur.getColumnIndex("address")));
                book.setCover(cur.getInt(cur.getColumnIndex("cover")));
            }
        }
        catch (SQLException e) {
        }
        finally {
            if(cur != null) {
                cur.close();
            }
        }
        return book;
    }
}
