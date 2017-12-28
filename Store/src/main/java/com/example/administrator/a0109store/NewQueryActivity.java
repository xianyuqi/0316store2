package com.example.administrator.a0109store;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.annotation.Suppress;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

public class NewQueryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_query);

        ListView listView= (ListView) findViewById(R.id.NewQuery_listview );

        Intent intent =getIntent();
        Bundle data=intent.getExtras();
        @SuppressWarnings("unchecked")
        List<Map<String,String>> list= (List<Map<String, String>>) data.getSerializable("data");
        SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.lv_item_newquery,new String[]{"name","num"},
                new int[]{R.id.lv_newquery_name,R.id.lv_newquery_num});
        listView.setAdapter(adapter);
    }
}
