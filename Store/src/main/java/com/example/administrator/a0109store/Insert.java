package com.example.administrator.a0109store;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.a0109store.zxing.activity.CaptureActivity;

public class Insert extends Activity {

    /**
     * 2017/3/15  条形码更新
     */
  /*  EditText et_name, et_num, et_inprice, et_outprice, et_quantity, et_imageSrc, et_remark, et_codebar;*/
    Button btn_insert, btn_cancel, btn_scan;
    Button btn_chooseImage;

    MySqlHelper helper;
    private ClearEditText cet_name, cet_num, cet_inprice, cet_outprice, cet_quantity, cet_imageSrc, cet_remark, cet_codebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_insert_new);
////申明一个权限
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_JAVA_REQUEST_CODE);}
        //init the edit text and button
        init();
        //cancel
        cancelEdittext();
        //init the database
        helper = new MySqlHelper(Insert.this, "store.db", 2);

        //insert

        startInsert();


        //choose Image
        chooseImage();
        /**
         * btn_scan
         * 2017/3/15
         * 扫描条形码输入条形码
         */
        scanCodeBar();
    }


    private void init() {

       /* et_name = (EditText) findViewById(R.id.et_name);
        et_num = (EditText) findViewById(R.id.et_num);
        et_inprice = (EditText) findViewById(R.id.et_inprice);
        et_outprice = (EditText) findViewById(R.id.et_outprice);
        et_quantity = (EditText) findViewById(R.id.et_quantity);
        et_imageSrc = (EditText) findViewById(R.id.et_imagesrc);
        et_remark = (EditText) findViewById(R.id.et_remark);
        et_codebar = (EditText) findViewById(R.id.et_codebar);*/
        cet_name = (ClearEditText) findViewById(R.id.cet_name);
        cet_num = (ClearEditText) findViewById(R.id.cet_num);
        cet_inprice = (ClearEditText) findViewById(R.id.cet_inprice);
        cet_outprice = (ClearEditText) findViewById(R.id.cet_outprice);
        cet_quantity = (ClearEditText) findViewById(R.id.cet_quantity);
        cet_imageSrc = (ClearEditText) findViewById(R.id.cet_imagesrc);
        cet_remark = (ClearEditText) findViewById(R.id.cet_remark);
        cet_codebar = (ClearEditText) findViewById(R.id.cet_codebar);

        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_chooseImage = (Button) findViewById(R.id.btn_insert_chooseImage);
        /**
         * 2017/3/15  条形码更新
         */

        btn_scan = (Button) findViewById(R.id.btn_scan);

    }

    private void cancelEdittext() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* et_name.setText("");
                et_num.setText("");
                et_inprice.setText("");
                et_outprice.setText("");
                et_quantity.setText("");
                et_imageSrc.setText("");
                et_remark.setText("");
                et_codebar.setText("");*/
                cet_name.setText("");
                cet_num.setText("");
                cet_inprice.setText("");
                cet_outprice.setText("");
                cet_quantity.setText("");
                cet_imageSrc.setText("");
                cet_remark.setText("");
                cet_codebar.setText("");
            }
        });
    }

    //insert in  database
    private void insert() {
       /* String name = et_name.getText().toString();
        String num = et_num.getText().toString();
        String inprice = et_inprice.getText().toString();
        String outprice = et_outprice.getText().toString();
        String quantity = et_quantity.getText().toString();
        String imageSrc = et_imageSrc.getText().toString();
        String remark = et_remark.getText().toString();
        String codebar = et_codebar.getText().toString();*/


        String name = cet_name.getText().toString();
        String num = cet_num.getText().toString();
        String inprice = cet_inprice.getText().toString();
        String outprice = cet_outprice.getText().toString();
        String quantity = cet_quantity.getText().toString();
        String imageSrc = cet_imageSrc.getText().toString();
        String remark = cet_remark.getText().toString();
        String codebar = cet_codebar.getText().toString();
        //// TODO: 2017/1/8

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into goods values(null,?,?,?,?,?,?,?,?)", new String[]{name, num, inprice, outprice, quantity, imageSrc, remark, codebar});


        Log.d("Insert", "Oninsert ");

    }

    //insert from button
    private void startInsert() {
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Insert.this)
                        .setTitle("向数据库添加商品")
                        .setMessage("确定添加商品吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                insert();
                                Toast.makeText(Insert.this, "商品添加成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Insert.this, Query.class);
                                startActivity(intent);
                                Insert.this.finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();


            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (helper != null) {
            helper.close();
        }
        Log.e("Insert", "is onDestroy");
    }

    private void chooseImage() {
        btn_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Insert.this, ChooseImageAcitivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == 1) {
            if (resultCode == 1) {
                String url = data.getExtras().getString("url");
                et_imageSrc.setText(url);
                Toast.makeText(Insert.this, url, Toast.LENGTH_SHORT).show();
            }
        }*/


        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String url = data.getExtras().getString("url");
                   /* et_imageSrc.setText(url);*/
                    cet_imageSrc.setText(url);
                    Toast.makeText(Insert.this, url, Toast.LENGTH_SHORT).show();
                }
                break;

            case 2:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString("result");
                   /* et_codebar.setText(scanResult);*/
                    cet_codebar.setText(scanResult);

                }
                break;
            default:
                break;
        }

    }

    private void scanCodeBar() {
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(Insert.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 2);
            }
        });

    }
}
