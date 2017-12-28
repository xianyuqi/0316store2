package com.example.administrator.a0109store;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;

import static java.security.AccessController.getContext;

public class MainActivity extends Activity {


    Button btn_insert,btn_query,btn_more;

    /**
     * 2017/3/17
     * info
     * @param savedInstanceState
     */
    private Button btn_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        btn_insert= (Button) findViewById(R.id.btn_insert);
        btn_query= (Button) findViewById(R.id.btn_query);
        btn_more= (Button) findViewById(R.id.btn_more);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,Insert.class);
                startActivity(intent);
            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MainActivity.this,Query.class);
                startActivity(intent);            }
        });
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,More.class);
                startActivity(intent);
            }
        });
       // Toast.makeText(MainActivity.this,"状态栏高："+getStatusBarHeight(MainActivity.this),Toast.LENGTH_SHORT).show();
        Toast.makeText(MainActivity.this,""+getVersion(),Toast.LENGTH_SHORT).show();


        btn_info= (Button) findViewById(R.id.btn_info );
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Info.class);
                startActivity(intent);
            }
        });

    }

    /**
     * 动态获取状态栏高度
     * @return
     */
    private int getStatusBarHeight(Context context) {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            //sbar = getContext().getResources().getDimensionPixelSize(x);
            sbar=context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return sbar;
    }

    public String getVersion() {
            try {
                     PackageManager manager = this.getPackageManager();
                    PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
                    String version = info.versionName;
                    return this.getString(R.string.version_name) + version;
                 } catch (Exception e) {
                     e.printStackTrace();
                     return this.getString(R.string.can_not_find_version_name);
                 }
         }

}
