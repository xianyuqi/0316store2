package com.example.administrator.a0109store;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.a0109store.zxing.activity.CaptureActivity;

public class Update extends Activity {

    Button btn_update;
    Button btn_delete;
    Button btn_chooseImage;
    Button btn_scan;
    /**
     * et_codebar
     * 2017/3/15
     * update
     */

    //EditText et_name, et_num, et_inprice, et_outprice, et_quantity, et_imagesrc, et_remark, et_codebar;
    ClearEditText cet_name, cet_num, cet_inprice, cet_outprice, cet_quantity, cet_imagesrc, cet_remark, cet_codebar;
    ImageView iv_show;
    private int _id;
    Intent intent;
    //private MySqlHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_updatee);
        setContentView(R.layout.activity_updater);

        //初始化控件
        init();
        //初始化数据
        initData();
        //初始化数据库
        //helper=new MySqlHelper(this);

        //修改数据
        update();
        //删除数据
        delete();
        //选择照片
        chooseImage();
        //初始化照片
        setImage();
/**
 * setCodeBar()
 * 2017/3/15
 * 条形码扫描输入
 */
        setCodeBar();

    }


    private void init() {
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_chooseImage = (Button) findViewById(R.id.btn_update_chooseImage);
        btn_scan = (Button) findViewById(R.id.btn_scan);
      /*  et_name = (EditText) findViewById(R.id.et_name);
        et_num = (EditText) findViewById(R.id.et_num);
        et_inprice = (EditText) findViewById(R.id.et_inprice);
        et_outprice = (EditText) findViewById(R.id.et_outprice);
        et_quantity = (EditText) findViewById(R.id.et_quantity);
        et_imagesrc = (EditText) findViewById(R.id.et_imagesrc);
        et_remark = (EditText) findViewById(R.id.et_remark);
        et_codebar = (EditText) findViewById(R.id.et_codebar);*/

        cet_name = (ClearEditText) findViewById(R.id.cet_name);
        cet_num = (ClearEditText) findViewById(R.id.cet_num);
        cet_inprice = (ClearEditText) findViewById(R.id.cet_inprice);
        cet_outprice = (ClearEditText) findViewById(R.id.cet_outprice);
        cet_quantity = (ClearEditText) findViewById(R.id.cet_quantity);
        cet_imagesrc = (ClearEditText) findViewById(R.id.cet_imagesrc);
        cet_remark = (ClearEditText) findViewById(R.id.cet_remark);
        cet_codebar = (ClearEditText) findViewById(R.id.cet_codebar);

        iv_show = (ImageView) findViewById(R.id.iv_update_show);


    }

    private void initData() {

        intent = getIntent();
        _id = intent.getExtras().getInt("_id");
       /* et_name.setText(intent.getStringExtra("name"));
        et_num.setText(intent.getStringExtra("num"));
        et_inprice.setText(intent.getStringExtra("inprice"));
        et_outprice.setText(intent.getStringExtra("outprice"));
        et_quantity.setText(intent.getStringExtra("quantity"));
        et_imagesrc.setText(intent.getStringExtra("imagesrc"));
        et_remark.setText(intent.getStringExtra("remark"));
        et_codebar.setText(intent.getStringExtra("codebar"));*/


        cet_name.setText(intent.getStringExtra("name"));
        cet_num.setText(intent.getStringExtra("num"));
        cet_inprice.setText(intent.getStringExtra("inprice"));
        cet_outprice.setText(intent.getStringExtra("outprice"));
        cet_quantity.setText(intent.getStringExtra("quantity"));
        cet_imagesrc.setText(intent.getStringExtra("imagesrc"));
        cet_remark.setText(intent.getStringExtra("remark"));
        cet_codebar.setText(intent.getStringExtra("codebar"));
    }

    public void update() {

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Update.this)
                        .setTitle("修改数据")
                        .setMessage("确定要修改数据吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //// TODO: 2017/1/14
                                Goods goods = new Goods(_id,
                                       /* et_name.getText().toString(),
                                        et_num.getText().toString(),
                                        et_inprice.getText().toString(),
                                        et_outprice.getText().toString(),
                                        et_quantity.getText().toString(),
                                        et_imagesrc.getText().toString(),
                                        et_remark.getText().toString(),
                                        et_codebar.getText().toString()*/



                                        cet_name.getText().toString(),
                                        cet_num.getText().toString(),
                                        cet_inprice.getText().toString(),
                                        cet_outprice.getText().toString(),
                                        cet_quantity.getText().toString(),
                                        cet_imagesrc.getText().toString(),
                                        cet_remark.getText().toString(),
                                        cet_codebar.getText().toString()
                                );
                                MySqlHelper helper = new MySqlHelper(Update.this, "store.db", 2);
                                int number = helper.updateGoods(goods);


                                setResult(1, intent);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });


    }

    public void delete() {
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Update.this)
                        .setTitle("删除数据")
                        .setMessage("确定要删除数据吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Goods goods = new Goods(_id,
                                        /*et_name.getText().toString(),
                                        et_num.getText().toString(),
                                        et_inprice.getText().toString(),
                                        et_outprice.getText().toString(),
                                        et_quantity.getText().toString(),
                                        et_imagesrc.getText().toString(),
                                        et_remark.getText().toString(),
                                        et_codebar.getText().toString()*/


                                        cet_name.getText().toString(),
                                        cet_num.getText().toString(),
                                        cet_inprice.getText().toString(),
                                        cet_outprice.getText().toString(),
                                        cet_quantity.getText().toString(),
                                        cet_imagesrc.getText().toString(),
                                        cet_remark.getText().toString(),
                                        cet_codebar.getText().toString()


                                );
                                MySqlHelper helper = new MySqlHelper(Update.this, "store.db", 2);
                                helper.deleteGoods(goods);

                                if (helper != null) {
                                    helper.close();
                                }

                                //// TODO: 2017/1/14

                                setResult(2, intent);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

    }

    private void chooseImage() {
        btn_chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Update.this, ChooseImageAcitivity.class);
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
                et_imagesrc.setText(url);
                Toast.makeText(Update.this, url, Toast.LENGTH_SHORT).show();
            }
        }*/

        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String url = data.getExtras().getString("url");
                   /* et_imagesrc.setText(url);*/

                    cet_imagesrc.setText(url);
                    Toast.makeText(Update.this, url, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(Update.this, "update is onDestroy", Toast.LENGTH_SHORT).show();
        Log.d("Update", "onDestroy" + "is on");
    }

    private void setImage() {
       /* if (et_imagesrc.getText()!=null&&et_imagesrc.getText().toString()!=""){
            Bitmap bitmap= BitmapFactory.decodeFile(et_imagesrc.getText().toString());
            iv_show.setImageBitmap(bitmap);}
        else
        {
            iv_show.setImageResource(R.drawable.nopicture);

        }*/

       /* Bitmap bitmap = BitmapFactory.decodeFile(et_imagesrc.getText().toString());
        iv_show.setImageBitmap(bitmap);
        Toast.makeText(Update.this, "setImage is on" + et_imagesrc.getText().toString(), Toast.LENGTH_SHORT).show();*/



        Bitmap bitmap = BitmapFactory.decodeFile(cet_imagesrc.getText().toString());
        iv_show.setImageBitmap(bitmap);


        Toast.makeText(Update.this, "setImage is on" + cet_imagesrc.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private void setCodeBar() {
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_scan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent openCameraIntent = new Intent(Update.this, CaptureActivity.class);
                        startActivityForResult(openCameraIntent, 2);
                    }
                });
            }
        });
    }
}
