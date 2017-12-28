package com.example.administrator.a0109store;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Administrator on 2017/1/8.
 */

public class MySqlHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "store.db";
    private static final String TABLE_NAME = "goods";
    private static final int VERSION = 1;
    private static final int DATABASE_VERSION = 2;
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NUM = "num";
    private static final String KEY_INPRICE = "inprice";
    private static final String KEY_OUTPRICE = "outprice";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_IMAGESRC = "imagesrc";
    private static final String KEY_REMARK = "remark";
    private static final String KEY_CODEBAR = "codebar";
    private Context context;

    final String CRE_TABLE = "create table goods(_id integer primary key autoincrement,name,num,inprice,outprice,quantity,imagesrc,remark)";


    public MySqlHelper(Context context, String name) {

        super(context, name, null, VERSION);
        this.context = context;
    }

    public MySqlHelper(Context context) {

        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    public MySqlHelper(Context context, String name, int version)

    {

        super(context, name, null, version);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CRE_TABLE);
        final int FIRST_DATABASE_VERSION = VERSION;
        onUpgrade(db, FIRST_DATABASE_VERSION, DATABASE_VERSION);
        Log.d("MySqlHelper", "onCreate  sqlitedatabase");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 1:
                    // String sql1 = "ALTER TABLE "+SQL.T_FAVORITE+" ADD COLUMN message VARCHAR";
                    String sql1 = "alter table goods add column codebar text";
                    db.execSQL(sql1);
                    Log.d("MySqlHelper", "onUpgrade" + "case 1 is running");
                    // upgradeToVersion1001(db);
                    break;
                case 2:
                    //upgradeToVersion1002(db);
                    // DROP TABLE IF EXISTS  Teachers
                    String sql2 = "drop table if exists goods";
                    db.execSQL(sql2);
                    String sql3 = "create table goods(_id integer primary key autoincrement,name,num,inprice,outprice,quantity,imagesrc,remark,codebar)";
                    db.execSQL(sql3);
                    Log.d("MySqlHelper", "onUpgrade" + "case 2 is running");
                    break;

                default:
                    break;
            }
        }


    }

    @Override
    public synchronized void close() {
        super.close();
        Log.e("MyHelper", "is closing");
    }

    /**
     * add the good to table goods
     * 插入数据
     *
     * @param goods
     */
    public void addGoods(Goods goods) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, goods.getName());
        cv.put(KEY_NUM, goods.getNum());
        cv.put(KEY_INPRICE, goods.getInprice());
        cv.put(KEY_OUTPRICE, goods.getOutprice());
        cv.put(KEY_QUANTITY, goods.getQuantity());
        cv.put(KEY_IMAGESRC, goods.getImagesrc());
        cv.put(KEY_REMARK, goods.getRemark());
        cv.put(KEY_CODEBAR, goods.getCodebar());

        db.insert(TABLE_NAME, null, cv);
        db.close();


    }

    public Goods getGoods(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_NUM, KEY_INPRICE, KEY_OUTPRICE, KEY_QUANTITY, KEY_IMAGESRC, KEY_REMARK},
//                KEY_NAME + " like ?", new String[]{"%"+ name+"%"}, null, null, null);
        Cursor cursor = db.rawQuery("select * from goods where name like ? or num like ?", new String[]{"%" + name + "%", "%" + name + "%"});


        Goods goods = null;
        if (cursor.moveToNext()) {
            goods = new Goods(cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8));

        }
        return goods;
    }

    public List<Goods> getSomeGoods(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Goods> goodslist = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from goods where name like ? or codebar like ?", new String[]{"%" + name + "%", "%" + name + "%"});
        if (cursor.moveToFirst()) {
            do {
                Goods goods = new Goods();
                goods.set_id(cursor.getInt(0));
                goods.setName(cursor.getString(1));
                goods.setNum(cursor.getString(2));
                goods.setInprice(cursor.getString(3));
                goods.setOutprice(cursor.getString(4));
                goods.setQuantity(cursor.getString(5));
                goods.setImagesrc(cursor.getString(6));
                goods.setRemark(cursor.getString(7));
                goods.setCodebar(cursor.getString(8));
                goodslist.add(goods);
            } while (cursor.moveToNext());
        }

        Log.e("MyHelper", "is on getsomeGoods");
        return goodslist;
    }


    public List<Goods> getAllGoods() {
        List<Goods> goodslist = new ArrayList<>();
        String selectQuery = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Goods goods = new Goods();
                goods.set_id(cursor.getInt(0));
                goods.setName(cursor.getString(1));
                goods.setNum(cursor.getString(2));
                goods.setInprice(cursor.getString(3));
                goods.setOutprice(cursor.getString(4));
                goods.setQuantity(cursor.getString(5));
                goods.setImagesrc(cursor.getString(6));
                goods.setRemark(cursor.getString(7));
                goods.setCodebar(cursor.getString(8));
                goodslist.add(goods);
            } while (cursor.moveToNext());

        }
        return goodslist;


    }


    /**
     *
     */
    public int getGoodsCounts() {
        String selectQuery = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.close();

        return cursor.getCount();


    }

    /**
     * @param goods
     * @return
     */
    public int updateGoods(Goods goods) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, goods.getName());
        cv.put(KEY_NUM, goods.getNum());
        cv.put(KEY_INPRICE, goods.getInprice());
        cv.put(KEY_OUTPRICE, goods.getOutprice());
        cv.put(KEY_QUANTITY, goods.getQuantity());
        cv.put(KEY_IMAGESRC, goods.getImagesrc());
        cv.put(KEY_REMARK, goods.getRemark());
        cv.put(KEY_CODEBAR, goods.getCodebar());
        Log.e("MyHelper", "undate is on");
        return db.update(TABLE_NAME, cv, KEY_ID + "=?", new String[]{String.valueOf(goods.get_id())});

    }

    public void deleteGoods(Goods goods) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(goods.get_id())});
        // db.delete(TABLE_NAME, "name =?", new String[]{goods.getName()});
        db.close();
        Log.e("MyHelper", "on delete");
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public int getVersionCode(Context context) {

        PackageManager manager = context.getPackageManager();//获取包管理器

        try {
            //通过当前的包名获取包的信息
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);//获取包对象信息
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
