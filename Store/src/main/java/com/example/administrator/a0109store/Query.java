package com.example.administrator.a0109store;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.a0109store.zxing.activity.CaptureActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Query extends Activity {

    MySqlHelper helper;
    ListView lv_goods;
    SQLiteDatabase db;
    // Cursor cursor;
    EditText et_query;
    Button btn_query;
    /**
     * 2017/3/15
     * btn_scan
     * 实现扫描输入查询
     */

    private Button btn_scan;
    private Handler handler;
    private static final int msgwhat = 0x112;

   // private ProgressBar pb_query;
    private String content = "";

    private List<Goods> resultList = null;
    private int count = 0;
    private int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        //open database

        //init
        init();

        //listview
        //setAdapter();
        //点击查询按钮
        clickQuery();
        /**
         * scanQuery()
         * 2017/3/15
         */
        scanQuery();

        //刷新listview
        // refreshView();

        //查询


//        btn_query.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String key=et_query.getText().toString();
//                //Cursor cursor=helper.getReadableDatabase().rawQuery("select * from goods where name like ? or num like ?",new String[]{"%"+key+"%","%"+key+"%"});
//                Cursor cursor=helper.getReadableDatabase().rawQuery("select * from goods where name = ? ",new String[]{"%"+key+"%"});
//
//
//                Bundle data=new Bundle();
//                data.putSerializable("data",converCursorToList(cursor));
//                Intent intent=new Intent(Query.this,NewQueryActivity.class);
//                intent.putExtras(data);
//                startActivity(intent);
//            }
//        });

    }

    /**
     * close the dbhelper
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();


        Toast.makeText(Query.this, "消失", Toast.LENGTH_SHORT).show();
        Log.e("Query", "onDestroy");

        if (helper != null) {
            helper.close();
        }
    }

    /**
     * return the data for simpleAdapter
     *
     * @return
     */

    private List<Map<String, String>> getData(Cursor cursor) {
        List<Map<String, String>> data = new ArrayList<>();


        if (cursor != null) {


            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                String name = cursor.getString(1);
                String num = cursor.getString(2);
                String outprice = cursor.getString(4);
                String quantity = cursor.getString(5);
                String remark = cursor.getString(7);
                map.put("name", "名称：" + name);
                map.put("num", "货号:" + num);
                map.put("outprice", "售价:" + outprice);
                map.put("quantity", "数量:" + quantity);
                map.put("remark", "备注:" + remark);

                data.add(map);

            }

        } else {
            Toast.makeText(Query.this, "数据为空", Toast.LENGTH_SHORT).show();
        }

        return data;

    }


    private SimpleAdapter getAdapter(Cursor cursor) {
        SimpleAdapter adapter = new SimpleAdapter(Query.this,
                getData(cursor),
                R.layout.lv_item_query,
                new String[]{"name", "num", "outprice", "quantity", "remark"},
                new int[]{R.id.item_name, R.id.item_num, R.id.item_outprice, R.id.item_quantity, R.id.item_remark});

        return adapter;
    }


    /**
     * init the button and something
     */
    private void init() {
        lv_goods = (ListView) findViewById(R.id.lv_query);
        btn_query = (Button) findViewById(R.id.btn_query);
        et_query = (EditText) findViewById(R.id.et_query);
       // pb_query = (ProgressBar) findViewById(R.id.pb_query);
        btn_scan = (Button) findViewById(R.id.btn_scan);

       /* if (pb_query.getVisibility() != View.GONE) {
            pb_query.setVisibility(View.GONE);
        }*/

        helper = new MySqlHelper(this, "store.db", 2);
        //db = helper.getWritableDatabase();
        db = helper.getReadableDatabase();
        //cursor = db.rawQuery("select * from goods where name=?", new String[]{"电视"});
        // String data = et_query.getText().toString();
        //cursor = db.rawQuery("select * from goods where name like ? or num like ?", new String[]{"%" + data + "%","%" + data + "%"});

    }

    /**
     * set listview adapter
     */
    private void setAdapter() {
        // String data = et_query.getText().toString();
        // cursor = db.rawQuery("select * from goods where name like ? or num like ?", new String[]{"%" + data + "%","%" + data + "%"});

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = et_query.getText().toString();
                Cursor cursor = db.rawQuery("select * from goods where name like ? or num like ?", new String[]{"%" + data + "%", "%" + data + "%"});

                lv_goods.setAdapter(getAdapter(cursor));
            }
        });
    }

    /**
     * for  test
     *
     * @param cursor
     * @return
     */

    protected ArrayList<Map<String, String>> converCursorToList(Cursor cursor) {
        ArrayList<Map<String, String>> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<>();
            map.put("name", cursor.getString(1));
            map.put("num", cursor.getString(2));
            result.add(map);
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       /* if (requestCode == 0) {
            if (resultCode == 1) {
                Toast.makeText(Query.this, "修改成功", Toast.LENGTH_SHORT).show();
            }
            if (resultCode == 2) {
                Toast.makeText(Query.this, "删除成功", Toast.LENGTH_SHORT).show();
            }
        }*/
        switch (requestCode)
        {
            case 0:
                if (resultCode == 1) {
                    Toast.makeText(Query.this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                if (resultCode == 2) {
                    Toast.makeText(Query.this, "删除成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString("result");
                    et_query.setText(scanResult);
                }
                break;
            default:
                break;

        }


//// TODO: 2017/3/15  
        //onCreate(null);
    }

    //判断进度条是否隐藏并设置显示
/*    public void isGone() {
        if (pb_query.getVisibility() == View.GONE) {
            pb_query.setVisibility(View.VISIBLE);
        }

    }*/


    public void clickQuery() {
        btn_query.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             //设置进度条显示
                                             //isGone();
                                             content = et_query.getText().toString();
                                             // String content = et_query.getText().toString();
                                             Toast.makeText(Query.this, "content:" + content, Toast.LENGTH_SHORT).show();
                                             //更新数据
                                             // refreshView();

                                             resultList = helper.getSomeGoods(content);
                                             GoodsAdapter adapter = new GoodsAdapter(getApplicationContext(), resultList);
                                             lv_goods.setAdapter(adapter);
                                             lv_goods.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
                                             lv_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                 @Override
                                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                     Intent intent = new Intent(Query.this, Update.class);
                                                     intent.putExtra("_id", resultList.get(position).get_id());
                                                     intent.putExtra("name", resultList.get(position).getName());
                                                     intent.putExtra("num", resultList.get(position).getNum());
                                                     intent.putExtra("inprice", resultList.get(position).getInprice());
                                                     intent.putExtra("outprice", resultList.get(position).getOutprice());
                                                     intent.putExtra("quantity", resultList.get(position).getQuantity());
                                                     intent.putExtra("imagesrc", resultList.get(position).getImagesrc());

                                                     intent.putExtra("remark", resultList.get(position).getRemark());
                                                     intent.putExtra("codebar", resultList.get(position).getCodebar());
                                                     if (helper != null) {
                                                         helper.close();
                                                     }
                                                     startActivityForResult(intent, 0);
                                                 }
                                             });
                                             count = adapter.getCount();
                                             size = resultList.size();

                                         }

                                     }

        );
    }

    public void refreshView() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == msgwhat) {

                    GoodsAdapter adapter = new GoodsAdapter(getApplicationContext(), resultList);
                    lv_goods.setAdapter(adapter);
                    lv_goods.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));
                    lv_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(Query.this, Update.class);
                            intent.putExtra("_id", resultList.get(position).get_id());
                            intent.putExtra("name", resultList.get(position).getName());
                            intent.putExtra("num", resultList.get(position).getNum());
                            intent.putExtra("inprice", resultList.get(position).getInprice());
                            intent.putExtra("outprice", resultList.get(position).getOutprice());
                            intent.putExtra("quantity", resultList.get(position).getQuantity());
                            intent.putExtra("imagesrc", resultList.get(position).getImagesrc());

                            intent.putExtra("remark", resultList.get(position).getRemark());
                            if (helper != null) {
                                helper.close();
                            }
                            startActivityForResult(intent, 0);
                        }
                    });

                    count = lv_goods.getCount();
                    /*if (count == size) {
                        pb_query.setVisibility(View.GONE);
                    }*/
                }

            }
        };


        new Thread() {
            @Override
            public void run() {
                super.run();


                if (count <= size) {


                    resultList = helper.getSomeGoods(content);
                    size = resultList.size();
                    Log.d("Query", "run" + "count" + count);
                    Log.d("Query", "run" + "size" + size);

                }


                Message m = new Message();
                m.what = msgwhat;
                handler.sendMessage(m);
            }
        }.start();

    }

    private void scanQuery()
    {
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(Query.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 2);
            }
        });
    }


}
